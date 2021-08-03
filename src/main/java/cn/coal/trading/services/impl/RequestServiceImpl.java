package cn.coal.trading.services.impl;

import cn.coal.trading.bean.Request;
import cn.coal.trading.mapper.ReqMapper;
import cn.coal.trading.services.RequestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.stereotype.Service;

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

    @Override
    public Map<String,Object> list(Integer userId, int page, int limit) {
        Page<Request> requestPage = reqMapper.selectPage(new Page<>(page, limit), new QueryWrapper<Request>() {{
            if(userId!=null){
                eq("user_id",userId);
            }
        }});

        return new HashMap<String,Object>(){{
            put("rows", requestPage.getRecords());
            put("current", requestPage.getCurrent());
            put("pages", requestPage.getPages());
        }};
    }

    @Override
    public int newReq(Request request) {
        TokenProfile profile = ProfileHolder.getProfile();
        request.setUserId(Long.parseLong(profile.getId()));
        return reqMapper.insert(request);
    }

    @Override
    public int edit(Request request) {
        TokenProfile profile = ProfileHolder.getProfile();
        request.setUserId(null);
        return reqMapper.update(request, new QueryWrapper<Request>(){{
            eq("user_id", Long.parseLong(profile.getId()));
            eq("id", request.getId());
        }});
    }
}
