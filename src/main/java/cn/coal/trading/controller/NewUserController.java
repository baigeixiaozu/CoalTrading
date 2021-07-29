package cn.coal.trading.controller;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.Role;
import cn.coal.trading.services.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseData newUser(@RequestBody BaseUser user){

        ResponseData responseData = new ResponseData();

        if(user.getLogin() == null) {
            responseData.setCode(105201);
            responseData.setMsg("用户名不能为空");
            return responseData;
        }
        if(user.getPass() == null) {
            responseData.setCode(105201);
            responseData.setMsg("密码不能为空");
            return responseData;
        }
        if(user.getNick() == null) {
            responseData.setCode(105201);
            responseData.setMsg("昵称不能为空");
            return responseData;
        }
        if(user.getEmail() == null) {
            responseData.setCode(105201);
            responseData.setMsg("邮箱不能为空");
            return responseData;
        }
        if(user.getRole() == null) {
            return responseData;
        }
        String ret = newUserService.newUser(user);

        if(ret == null){
            responseData.setCode(200);
            responseData.setMsg("success");
        }else{
            responseData.setCode(105202);
            responseData.setMsg("fail");
            responseData.setError(ret);
        }

        return responseData;
    }

    // 获取角色列表
    @GetMapping({"/getRoleList/{type}"})
    public ResponseData getRoleList(@PathVariable String type){
        ResponseData responseData = new ResponseData();

        List<Role> roles = newUserService.getRoleList(type);

        if(roles != null){
            responseData.setCode(200);
            responseData.setMsg("success");
            responseData.setData(roles);
        }else{
            responseData.setCode(201);
            responseData.setMsg("fail");
            responseData.setError("角色数据获取失败，未知异常");
        }
        return responseData;
    }
}
