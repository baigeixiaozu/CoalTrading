package cn.coal.trading.services.impl;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.Request;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.mapper.DelistingMapper;
import cn.coal.trading.mapper.FinanceMapper;
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
    @Resource
    FinanceMapper financeMapper;
    @Resource
    CompanyMapper companyMapper;

    @Override
    public Map<String,Object> getPublicList(Long userId, int page, int limit) {
        Page<Map<String, Object>> requestPage = reqMapper.selectMapsPage(new Page<>(page, limit), new QueryWrapper<Request>() {{
            if(userId!=null){
                eq("user_id",userId);
            }
            eq("status", 3);
            select("id", "user_id", "created_time", "type");
        }});

        return new HashMap<String,Object>(){{
            put("rows", requestPage.getRecords());
            put("current", requestPage.getCurrent());
            put("pages", requestPage.getPages());
        }};
    }

    // TODO:条件处理：审核通过的  - 3
    @Override
    public Request getPublicDetail(int id) {
        return reqMapper.selectOne(new QueryWrapper<Request>(){{
            eq("id", id);
            eq("status", 3);
        }});
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
            select("id", "type", "user_id", "created_time", "status");
        }});
        return new HashMap<String,Object>(){{
            put("rows", requestPage.getRecords());
            put("current", requestPage.getCurrent());
            put("pages", requestPage.getPages());
        }};
    }

    @Override
    public Request myDetail(Long userId, long req_id) {
        return reqMapper.selectOne(new QueryWrapper<Request>(){{
            eq("user_id", userId);
            eq("id", req_id);
        }});
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
    public String getComName(long id) {
        CompanyInformation companyInformation = companyMapper.selectOne(new QueryWrapper<CompanyInformation>() {{
            eq("user_id", id);
            select("com_name");
        }});
        return companyInformation.getComName();
    }


}
