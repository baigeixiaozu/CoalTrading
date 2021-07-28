package cn.coal.trading.controller;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.Role;
import cn.coal.trading.services.NewUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    NewUserService newUserService;

    @PostMapping("/new")
    public Map<String, Object> newUser(@RequestBody BaseUser user){

        Integer ret = newUserService.newUser(user);

        return new HashMap<String, Object>(){{
            put("code", 200);
            put("test", ret);
        }};
    }

    // TODO: 权限验证
    @GetMapping("/getRoleList")
    public Map<String, Object> getRoleList(){

        List<Role> roles = newUserService.getRoleList();
        Map<String, Object> result = new HashMap<>();

        if(roles != null){
            result.put("code", 200);
            result.put("data", roles);
        }else{
            result.put("code", 201);
            result.put("msg", "角色数据获取失败，未知异常");
        }
        return result;
    }
}
