package cn.coal.trading.services.impl;

import cn.coal.trading.bean.*;
import cn.coal.trading.mapper.DelistingMapper;
import cn.coal.trading.mapper.FinanceMapper;
import cn.coal.trading.mapper.ReqMapper;
import cn.coal.trading.services.DelistService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class DelistServiceImpl implements DelistService {
    /**
     * @author Sorakado
     * @param id    需求id
     * @return Request
     * 获取详细需求信息
     */
    @Resource
    ReqMapper reqMapper;
    @Resource
    DelistingMapper delistingMapper;
    @Resource
    FinanceMapper financeMapper;

    /**
     * @author Sorakado
     * @param id requestId    摘牌用户id，需求id
     * @return ResponseData
     * 摘牌操作
     */

    @Override
    public ResponseData delist(long id, int requestId){
        ResponseData response = new ResponseData();
        Delisting delist=new Delisting();
        delist.setReqId((long)requestId);
        delist.setUserId(id);
        delist.setStatus(1);

        int insert = delistingMapper.insert(delist);
        if(insert==1) {

            UpdateWrapper<Request> wrapper = new UpdateWrapper<>();
            wrapper.set("zp_id",delist.getId());
            reqMapper.update(reqMapper.selectById(requestId),wrapper);

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


    /**
     * 获取所有摘牌信息
     * @param page      页码
     * @param limit     每页数量
     * @return
     */
    @Override
    public Map<String,Object> listDelist(int page, int limit) {
        Page<Map<String, Object>> delistPage = delistingMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Delisting>() {{

            eq("status", 1);
            select("id", "req_id","user_id", "created_time",  "status");
        }});

        return new HashMap<String,Object>(){{
            put("rows", delistPage.getRecords());
            put("current", delistPage.getCurrent());
            put("pages", delistPage.getPages());
        }};
    }

    /**
     * 获取摘牌和挂牌信息
     * @param delistId   挂牌id
     * @return
     */
    @Override
    public ResponseData getDetailInfo(long delistId) {

        Delisting delist= delistingMapper.selectById(delistId);
        Request request = reqMapper.selectById(delist.getReqId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("reqInfo",request);
        map.put("delistInfo",delist);
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
    public ResponseData examine(long delistId, AuditOpinion opinion) {
        ResponseData response = new ResponseData();
        Delisting delisting = delistingMapper.selectById(delistId);

        UpdateWrapper<Delisting> wrapper = new UpdateWrapper<Delisting>();
        wrapper.set("opinion",opinion.getOpinion());
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

    /**
     * @param profile    token 信息
     * @param page  页码
     * @param limit 每页数量
     * @return 需求数据
     * @author Sorakado
     * 获取摘牌列表
     */
    @Override
    public Map<String, Object> listDelistFinance(TokenProfile profile, int page, int limit) {

        long queryId=0;
        Set<String> roles = profile.getRoles();
        for (String role : roles) {
            if(role=="USER_MONEY"){
                FinanceProperty finance = financeMapper.selectOne(new QueryWrapper<FinanceProperty>() {
                    {
                        eq("finance_userid", profile.getId());
                    }
                });
                queryId=finance.getMainUserid();
            }else{
                queryId= Long.parseLong(profile.getId());
            }
        }


        long finalQueryId = queryId;
        Page<Map<String, Object>> delistPage = delistingMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Delisting>() {{
            eq("user_id", finalQueryId);
            eq("status", 1);
            select("id", "req_id","user_id", "created_time",  "status");
        }});

        return new HashMap<String,Object>(){{
            put("rows", delistPage.getRecords());
            put("current", delistPage.getCurrent());
            put("pages", delistPage.getPages());
        }};
    }
}
