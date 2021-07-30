package cn.coal.trading.controller;

import cn.coal.trading.bean.*;
import cn.coal.trading.services.*;
import com.baomidou.shaun.core.annotation.HasRole;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Sorakado & jiyeme
 * @veuserServiceion v2.0
 * @time 7.29 11:02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 新增用户操作
     * TODO: 设定超级管理员权限
     * @Author jiyeme
     * @param user 用户信息
     * @return ResponseData
     */
    @PostMapping("/new")
    @HasRole(value = {"SUPER_ADMIN"})
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
        String ret = userService.newUser(user);

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
     * @Author jiyeme
     * @param type 角色类型
     * @return ResponseData
     */
    @GetMapping({"/getRoleList/{type}"})
    @HasRole(value = {"SUPER_ADMIN"})   // TODO: 是否与路径排除发生冲突，有待确认
    public ResponseData getRoleList(@PathVariable String type) {
        ResponseData responseData = new ResponseData();

        List<Role> roles = userService.getRoleList(type);

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
    public ResponseData register(@RequestBody BaseUser user) {

        ResponseData result = userService.register(user);

        return result;
    }

    @GetMapping("/login")
    public ResponseData login(@RequestBody BaseUser user) {
        ResponseData result = userService.login(user);

        return result;
    }

    //        @GetMapping("/loginout")
//        public ResponseData loginOut(@RequestBody BaseUser user){
//       ResponseData result = userService.loginOut(user);
//
//            return result;
    @PostMapping("/complete")
    public ResponseData complete(@RequestBody CompanyInformation info) {

        ResponseData result = userService.complete(info);

        return result;
    }

}
