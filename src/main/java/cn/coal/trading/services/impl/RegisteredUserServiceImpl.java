package cn.coal.trading.services.impl;


import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.RegisteredUser;
import cn.coal.trading.mapper.RegisteredUserMapper;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.services.RegisteredUserService;
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
    public RegisteredUserMapper registeredUserMapper;
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public BaseUser baseUser;

    @Override
    public Map<String,Object> register(RegisteredUser registeredUser) {
        HashMap<String, Object> map = new HashMap<>();
        baseUser.setLogin(registeredUser.getLogin());
        baseUser.setPass(registeredUser.getPass());
        baseUser.setNick(registeredUser.getNick());
        baseUser.setEmail(registeredUser.getEmail());

        int result  =userMapper.insert(baseUser);

       if(result==1){
            map.put("code","201");
            map.put("msg","创建成功");




       }else{
           map.put("code","101");
           map.put("msg","用户名重复，创建失败");
       }
        return map;
    }


}
