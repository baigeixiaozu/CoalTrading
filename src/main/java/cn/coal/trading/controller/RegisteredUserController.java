package cn.coal.trading.controller;

import cn.coal.trading.bean.RegisteredUser;
import cn.coal.trading.services.impl.RegisteredUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

/**
 * @author Sorakado
 * @time 7.28 21:17
 * @version v1.0
 */
@RestController
public class RegisteredUserController {

    @Autowired
    RegisteredUserServiceImpl rs;

    @PostMapping("/registeredUser")
    public Map<String,Object> Regisered(@RequestBody RegisteredUser user){

        Map<String, Object> register = rs.register(user);

    return register;
    }

}
