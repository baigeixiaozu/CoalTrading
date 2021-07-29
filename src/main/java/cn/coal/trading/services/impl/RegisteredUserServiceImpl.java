package cn.coal.trading.services.impl;


import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.RegisteredUser;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.UserRoleBinding;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.mapper.UserRoleMapper;
import cn.coal.trading.services.RegisteredUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Sorakado
 * @Date 2021/7/28 20:13
 * @Version 1.0
 **/

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public UserRoleMapper userRoleMapper;
    @Override
    public ResponseData register(RegisteredUser registeredUser) {
        ResponseData response=new ResponseData();
        BaseUser baseUser=new BaseUser();

        baseUser.setLogin(registeredUser.getLogin());
        baseUser.setPass(registeredUser.getPass());
        baseUser.setNick(registeredUser.getNick());
        baseUser.setEmail(registeredUser.getEmail());

        int result  =userMapper.insert(baseUser);

       if(result==1){

        int role=0;
        if(baseUser.getNick().equals("供应商")){
             role=7;
        }else{
            role=8;
        }
        UserRoleBinding urb=new UserRoleBinding(baseUser.getId(),role);
        userRoleMapper.insert(urb);

           response.setCode(201);
           response.setMsg("创建成功");
           response.setError("无");
           response.setData(null);

       }else{
           response.setCode(409);
           response.setMsg("创建失败，用户名已存在");
           response.setError("资源冲突，或者资源被锁定");
           response.setData(null);
       }
        return response;
    }


}
