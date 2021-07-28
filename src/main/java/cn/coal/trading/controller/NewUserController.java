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

        // TODO: 权限验证
        Map<String, Object> result = new HashMap<>();
        if(user.getLogin() == null) {
            result.put("code", 105201);
            result.put("msg", "用户名不能为空");
            return result;
        }
        if(user.getPass() == null) {
            result.put("code", 105201);
            result.put("msg", "密码不能为空");
            return result;
        }
        if(user.getNick() == null) {
            result.put("code", 105201);
            result.put("msg", "昵称不能为空");
            return result;
        }
        if(user.getEmail() == null) {
            result.put("code", 105201);
            result.put("msg", "邮箱不能为空");
            return result;
        }
        if(user.getRole() == null) {
            result.put("code", 105201);
            result.put("msg", "用户角色不能为空");
            return result;
        }
        String ret = newUserService.newUser(user);

        if(ret == null){
            result.put("code", 200);
            result.put("msg", "success");
        }else{
            result.put("code", 105202);
            result.put("msg", "fail");
            result.put("error", ret);
        }

        return result;
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
