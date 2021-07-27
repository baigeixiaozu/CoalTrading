package cn.coal.trading.controller;

import cn.coal.trading.bean.NewUser;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:50
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("/user")
public class NewUserController {
    @PostMapping("/new")
    public Map<String, Object> newUser(@RequestBody NewUser user){
        return new HashMap<String, Object>(){{
            put("code", 200);
            put("test", user);
        }};
    }
}
