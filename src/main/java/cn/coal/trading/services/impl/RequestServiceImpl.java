package cn.coal.trading.services.impl;

import cn.coal.trading.bean.Delisting;
import cn.coal.trading.bean.Request;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.DelistingMapper;
import cn.coal.trading.mapper.ReqMapper;
import cn.coal.trading.services.RequestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/8/3 14:09
 * @Version 1.0
 **/
@Service
public class RequestServiceImpl implements RequestService {

    @Resource
    ReqMapper reqMapper;
    @Resource
    DelistingMapper delistingMapper;

    @Override
    public Map<String,Object> listAvailable(Long userId, int page, int limit) {
        Page<Map<String, Object>> requestPage = reqMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Request>() {{
            if(userId!=null){
                eq("user_id",userId);
            }
            eq("status", 3);
            select("id", "user_id", "created_time", "type", "detail");
        }});

        return new HashMap<String,Object>(){{
            put("rows", requestPage.getRecords());
            put("current", requestPage.getCurrent());
            put("pages", requestPage.getPages());
        }};
    }

    @Override
    public long newReq(Request request) {
        int insert = reqMapper.insert(request);
        if
        (insert == 1){return request.getId();}
        else {return 0;}
    }

    @Override
    public int edit(Request request) {
        TokenProfile profile = ProfileHolder.getProfile();
        request.setUserId(null);
        return reqMapper.update(request, new QueryWrapper<Request>(){{
            eq("user_id", Long.parseLong(profile.getId()));
            eq("id", request.getId());
            eq("status", 1);        // 草稿状态才能编辑
        }});
    }

    @Override
    public Map<String, Object> myList(Long userId, int page, int limit) {
        Page<Map<String, Object>> requestPage = reqMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Request>() {{
            eq("user_id", userId);
            select("id", "user_id", "created_time", "status");
        }});
        return new HashMap<String,Object>(){{
            put("rows", requestPage.getRecords());
            put("current", requestPage.getCurrent());
            put("pages", requestPage.getPages());
        }};
    }

    @Override
    public Map<String, Object> auditPending(int page, int limit) {
        Page<Map<String, Object>> requestPage = reqMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Request>() {{
            eq("status", 2);
            select("id", "created_time");
        }});
        return new HashMap<String,Object>(){{
            put("rows", requestPage.getRecords());
            put("current", requestPage.getCurrent());
            put("pages", requestPage.getPages());
        }};
    }

    @Override
    public Request auditDetail(long req_id) {
        return reqMapper.selectOne(new QueryWrapper<Request>() {{
            eq("id", req_id);
            eq("status", 2);        // 待审核状态
        }});
    }

    @Override
    public boolean doAudit(Request request) {
        int i = reqMapper.update(request, new UpdateWrapper<Request>(){{
            eq("id", request.getId());
            eq("status", 2);        // 2 审核中

        }});
        return i == 1;
    }

    /**
     * @author Sorakado
     * @param id    需求id
     * @return Request
     */

    @Override
    public Request getReqDetails(int id) {
        Request request = reqMapper.selectById(id);
        return request;
    }

    @Override
    public ResponseData delist(long id, int requestId){
        ResponseData response = new ResponseData();
        Delisting delist=new Delisting();
        delist.setReqId((long)requestId);
        delist.setUserId(id);
        delist.setStatus(1);

        int insert = delistingMapper.insert(delist);
        if(insert==1) {
            response.setCode(200);
            response.setMsg("提交摘牌申请成功！");
            response.setError("无");
            response.setData(delist);

        }
        else{
            response.setCode(409);
            response.setMsg("提交摘牌申请失败！");
            response.setError("资源冲突，或者资源被锁定");
            response.setData(null);
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateContract(long reqId, long userId, String contractPath) {
        int update = reqMapper.update(new Request(), new UpdateWrapper<Request>() {{
            eq("id", reqId);
            eq("user_id", userId);
            eq("status", "" + 8).or().eq("status", "" + 10);
            set("contract_file", contractPath);
            set("status", 9);
        }});
        return update == 1;
    }




    @Override
    public Map<String,Object> listDelist(Long userId, int page, int limit) {
        Page<Map<String, Object>> delistPage = delistingMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Delisting>() {{
            if(userId!=null){
                eq("user_id",userId);
            }
            eq("status", 1);
            select("id", "req_id","user_id", "created_time",  "detail");
        }});

        return new HashMap<String,Object>(){{
            put("rows", delistPage.getRecords());
            put("current", delistPage.getCurrent());
            put("pages", delistPage.getPages());
        }};
    }

    @Override
    public ResponseData getDetailInfo(long delistId) {

        Delisting delist= delistingMapper.selectById(delistId);
        Request request = reqMapper.selectById(delist.getReqId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("挂牌信息",request);
        map.put("摘牌信息",delist);
        ResponseData response = new ResponseData();
        if(delist!=null&&request!=null) {
            response.setData(map);
            response.setCode(200);
            response.setMsg("查询成功");
            response.setError("无");
        }
        else{
            response.setData(null);
            response.setCode(404);
            response.setMsg("出现未知错误，未查询成功");
            response.setError("资源，服务未找到");
        }

        return response;
    }

    /**
     * @param delistId 挂牌id
     * @param opinion
     * @return 需求数据
     * @author Sorakado
     * 审核功能
     */
    @Override
    public ResponseData examine(long delistId, String opinion) {
        ResponseData response = new ResponseData();
        Delisting delisting = delistingMapper.selectById(delistId);
        delisting.setOpinion(opinion);
        UpdateWrapper<Delisting> wrapper = new UpdateWrapper<Delisting>();
        wrapper.set("opinion",opinion);
        int update = delistingMapper.update(delisting, wrapper);

        if(update!=0){
            response.setCode(204);
            response.setMsg("操作成功！");
            response.setError("无");
            response.setData(null);

        }else{
            response.setData(null);
            response.setCode(409);
            response.setMsg("未知错误！");
            response.setError("资源冲突，或者资源已经被锁定");
        }
        return response;
    }

}
