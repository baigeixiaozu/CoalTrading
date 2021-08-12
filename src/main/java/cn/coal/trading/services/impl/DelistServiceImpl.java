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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param delisting 摘牌信息
     * @return ResponseData
     * 摘牌操作
     * @author Sorakado
     */

    @Override
    public ResponseData delist(Delisting delisting) {
        ResponseData response = new ResponseData();

        try {
            int insert = delistingMapper.insert(delisting);
            if (insert == 1) {
                response.setCode(200);
                response.setMsg("提交摘牌申请成功！");
                response.setError("无");
                response.setData(delisting);
            } else {
                response.setCode(409);
                response.setMsg("提交摘牌申请失败！");
                response.setError("资源冲突，或者资源被锁定");
                response.setData(null);
            }
        }catch (DuplicateKeyException e){
            response.setCode(405);
            response.setMsg("fail");
            response.setError("已经摘过牌了");
            response.setData(null);
        }
        return response;
    }


    /**
     * 获取所有摘牌信息
     *
     * @param page  页码
     * @param limit 每页数量
     * @return
     */
    @Override
    public Map<String, Object> listDelist(int page, int limit) {
        Page<Map<String, Object>> delistPage = delistingMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Delisting>() {{

            eq("status", 1);
            select("id", "req_id", "user_id", "created_time", "status","type");
        }});

        return new HashMap<String, Object>() {{
            put("rows", delistPage.getRecords());
            put("current", delistPage.getCurrent());
            put("pages", delistPage.getPages());
        }};
    }

    /**
     * 获取摘牌和挂牌信息
     *
     * @param delistId 挂牌id
     * @return
     */
    @Override
    public ResponseData getDetailInfoByZPId(long delistId) {

        Delisting delist = delistingMapper.selectById(delistId);
        Request request = reqMapper.selectById(delist.getReqId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("reqInfo", request);
        map.put("delistInfo", delist);
        ResponseData response = new ResponseData();
        if (delist != null && request != null) {
            response.setData(map);
            response.setCode(200);
            response.setMsg("查询成功");
            response.setError("无");
        } else {
            response.setData(null);
            response.setCode(404);
            response.setMsg("出现未知错误，未查询成功");
            response.setError("资源，服务未找到");
        }

        return response;
    }

    @Override
    public ResponseData getDetailInfo2(long gpId, long userId) {

        Request request = reqMapper.selectById(gpId);
        Delisting delist = delistingMapper.selectOne(new QueryWrapper<Delisting>(){{
            eq("user_id", userId);
            eq("req_id", gpId);
        }});
        HashMap<String, Object> map = new HashMap<>();
        map.put("reqInfo", request);
        map.put("delistInfo", delist);
        ResponseData response = new ResponseData();
        if (delist != null && request != null) {
            response.setData(map);
            response.setCode(200);
            response.setMsg("查询成功");
            response.setError("无");
        } else {
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
        wrapper.set("opinion", opinion.getOpinion());
        int update = delistingMapper.update(delisting, wrapper);

        if (update != 0) {
            response.setCode(204);
            response.setMsg("操作成功！");
            response.setError("无");
            response.setData(null);

        } else {
            response.setData(null);
            response.setCode(409);
            response.setMsg("未知错误！");
            response.setError("资源冲突，或者资源已经被锁定");
        }
        return response;
    }

    /**
     * @param profile token 信息
     * @param page    页码
     * @param limit   每页数量
     * @return 需求数据
     * @author Sorakado
     * 获取摘牌列表
     */
    @Override
    public Map<String, Object> listDelistFinance(TokenProfile profile, int page, int limit) {

        long queryId = 0;
        Set<String> roles = profile.getRoles();
        for (String role : roles) {
            if ("USER_MONEY".equals(role)) {
                FinanceProperty finance = financeMapper.selectOne(new QueryWrapper<FinanceProperty>() {
                    {
                        eq("finance_userid", profile.getId());
                    }
                });
                queryId = finance.getMainUserid();
            } else {
                queryId = Long.parseLong(profile.getId());
            }
        }

        long finalQueryId = queryId;
        Page<Map<String, Object>> delistPage = delistingMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Delisting>() {{
            eq("user_id", finalQueryId);
            eq("status", 1);
            select("id", "req_id", "user_id", "created_time", "status","type");
        }});

        return new HashMap<String, Object>() {{
            put("rows", delistPage.getRecords());
            put("current", delistPage.getCurrent());
            put("pages", delistPage.getPages());
        }};
    }

    /**
     * 公司账户获取指定的摘牌详细信息及对应的挂牌信息
     *
     * @param profile
     * @param delistId
     * @author Sorakado
     * @time 2021/8/11/ 20:24
     * @version 1.0
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public ResponseData getDetailInfoForUser(TokenProfile profile, long delistId) {
        ResponseData response = new ResponseData();
        long queryId = 0;
        Set<String> roles = profile.getRoles();
        for (String role : roles) {
            if ("USER_MONEY".equals(role)) {
                FinanceProperty finance = financeMapper.selectOne(new QueryWrapper<FinanceProperty>() {
                    {
                        eq("finance_userid", profile.getId());
                    }
                });
                queryId = finance.getMainUserid();
            } else {
                queryId = Long.parseLong(profile.getId());
            }
        }

        Delisting delist = delistingMapper.selectById(delistId);
        long userId=delist.getUserId();
        if(userId==queryId){
            Request request = reqMapper.selectById(delist.getReqId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("reqInfo", request);
            map.put("delistInfo", delist);
            response.setData(map);
            response.setCode(200);
            response.setMsg("查询成功");
            response.setError("无");
        }else{
            response.setData(null);
            response.setCode(404);
            response.setMsg("出现未知错误，未查询成功");
            response.setError("资源，服务未找到");
        }
        return response;

    }
    /**
     * 公司账户获取指定的摘牌详细信息及对应的挂牌信息
     *
     * @param profile
     * @param reqId
     * @author Sorakado
     * @time 2021/8/11/ 20:24
     * @version 1.0
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public ResponseData getDetailInfoForUser2(TokenProfile profile, long reqId) {
        ResponseData response = new ResponseData();
        long queryId = 0;
        Set<String> roles = profile.getRoles();
        for (String role : roles) {
            if ("USER_MONEY".equals(role)) {
                FinanceProperty finance = financeMapper.selectOne(new QueryWrapper<FinanceProperty>() {
                    {
                        eq("finance_userid", profile.getId());
                    }
                });
                queryId = finance.getMainUserid();
            } else {
                queryId = Long.parseLong(profile.getId());
            }
        }

        long finalQueryId = queryId;
        Delisting delist = delistingMapper.selectOne(new QueryWrapper<Delisting>(){{
            eq("user_id", finalQueryId);
            eq("req_id", reqId);
        }});
        Request request = reqMapper.selectOne(new QueryWrapper<Request>(){{
            eq("id", reqId);
            eq("status", "3");
        }});
        HashMap<String, Object> map = new HashMap<>();
        map.put("reqInfo", request);
        map.put("delistInfo", delist);
        response.setData(map);
        response.setCode(200);
        response.setMsg("查询成功");
        response.setError("无");
        return response;

    }
}
