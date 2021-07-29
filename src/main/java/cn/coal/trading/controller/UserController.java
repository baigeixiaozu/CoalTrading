package cn.coal.trading.controller;

import cn.coal.trading.bean.*;
import cn.coal.trading.services.CompanyService;
import cn.coal.trading.services.NewUserService;
import cn.coal.trading.services.RegisteredUserService;
import cn.coal.trading.services.UserLoginService;
import cn.coal.trading.services.impl.CompanyServiceImpl;
import cn.coal.trading.services.impl.UserLoginServiceImpl;
import cn.coal.trading.services.impl.RegisteredUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Sorakado & jiyeme
 * @version v2.0
 * @time 7.29 11:02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    RegisteredUserService rs;
    @Resource
    UserLoginService ls;
    @Resource
    CompanyService cs;
    @Resource
    NewUserService newUserService;

    /**
     * 新增用户操作
     * TODO: 设定超级管理员权限
     * @Author jiye
     * @param user 用户信息
     * @return ResponseData
     */
    @PostMapping("/new")
    public ResponseData newUser(@RequestBody BaseUser user) {

        ResponseData responseData = new ResponseData();

        if (user.getLogin() == null) {
            responseData.setCode(105201);
            responseData.setMsg("用户名不能为空");
            return responseData;
        }
        if (user.getPass() == null) {
            responseData.setCode(105201);
            responseData.setMsg("密码不能为空");
            return responseData;
        }
        if (user.getNick() == null) {
            responseData.setCode(105201);
            responseData.setMsg("昵称不能为空");
            return responseData;
        }
        if (user.getEmail() == null) {
            responseData.setCode(105201);
            responseData.setMsg("邮箱不能为空");
            return responseData;
        }
        if (user.getRole() == null) {
            return responseData;
        }
        String ret = newUserService.newUser(user);

        if (ret == null) {
            responseData.setCode(200);
            responseData.setMsg("success");
        } else {
            responseData.setCode(105202);
            responseData.setMsg("fail");
            responseData.setError(ret);
        }

        return responseData;
    }

    /**
     * 获取角色列表
     *
     * @Author jiye
     * @param type 角色类型
     * @return ResponseData
     */
    @GetMapping({"/getRoleList/{type}"})
    public ResponseData getRoleList(@PathVariable String type) {
        ResponseData responseData = new ResponseData();

        List<Role> roles = newUserService.getRoleList(type);

        if (roles != null) {
            responseData.setCode(200);
            responseData.setMsg("success");
            responseData.setData(roles);
        } else {
            responseData.setCode(201);
            responseData.setMsg("fail");
            responseData.setError("角色数据获取失败，未知异常");
        }
        return responseData;
    }

    @PostMapping("/register")
    public ResponseData regisered(@RequestBody BaseUser user) {

        ResponseData result = rs.register(user);

        return result;
    }

    @GetMapping("/login")
    public ResponseData login(@RequestBody BaseUser user) {
        ResponseData result = ls.login(user);

        return result;
    }

    //        @GetMapping("/loginout")
//        public ResponseData loginOut(@RequestBody BaseUser user){
//       ResponseData result = rs.loginOut(user);
//
//            return result;
    @PostMapping("/complete")
    public ResponseData complete(@RequestBody CompanyInformation info) {

        ResponseData result = cs.complete(info);

        return result;
    }

}
