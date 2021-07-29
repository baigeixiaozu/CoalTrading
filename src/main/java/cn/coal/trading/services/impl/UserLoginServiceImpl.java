package cn.coal.trading.services.impl;

import cn.coal.trading.bean.BaseUser;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.services.UserLoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserLoginServiceImpl implements UserLoginService {


    @Autowired
    public UserMapper userMapper;
    @Override
    public ResponseData login(BaseUser loginUserUser) {
        ResponseData response=new ResponseData();
        HashMap<String, Object> querymap = new HashMap<>();
        QueryWrapper<BaseUser> wrapper= new QueryWrapper<BaseUser>();

        querymap.put("login",loginUserUser.getLogin());
        querymap.put("pass",loginUserUser.getPass());
        wrapper.allEq(querymap,false);

        BaseUser baseUser = userMapper.selectOne(wrapper);
        if(baseUser!=null) {
            response.setCode(200);
            response.setMsg("登录成功");
            response.setData(baseUser);
            response.setError("无");

        }
        else{
            response.setCode(401);
            response.setMsg("账号密码输入有误！");
            response.setData(null);
            response.setError("未授权");

        }
        return response;

    }
}
