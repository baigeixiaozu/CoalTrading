package cn.coal.trading.controller;

import cn.coal.trading.bean.*;
import cn.coal.trading.services.*;
import cn.coal.trading.utils.TimeUtil;
import com.baomidou.shaun.core.annotation.HasRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sorakado & jiyeme
 * @veuserServiceion v2.0
 * @time 7.29 11:02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${shaun.expire-time}")
    String expireTime;
    @Resource
    UserService userService;
    @Resource
    LoginService loginService;
    @Resource
    RegisterService registerService;

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

    /**
     * @Author Sorakado
     * @Date 2021/7/29 14:34
     * @Version 2.0
     **/
    @PostMapping("/register")
    public ResponseData register(@RequestBody TradeUser user) {

        ResponseData result = registerService.register(user);

        return result;
    }
    /**
     * @Author Sorakado & jiyeme
     * @Date 2021/7/29 16:34
     * @Version 2.0
     **/
    @PostMapping("/login")
    public ResponseData login(@RequestBody BaseUser user) {
        ResponseData response = new ResponseData();
        BaseUser userInfo = loginService.getUserInfo(user);
        if(userInfo == null){
            //用户不存在
            response.setCode(101404);
            response.setMsg("fail");
            response.setError("用户不存在");
            return response;
        }else if(userInfo.getStatus() == 1){
            //用户审核未通过
            response.setCode(101403);
            response.setMsg("fail");
            response.setError("用户正在审核中");
            return response;
        }

        // 加密  密码检查
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        boolean isPassRight = encoder.matches(user.getPass(), userInfo.getPass());

        if(!isPassRight){
            //密码不正确
            response.setCode(101401);
            response.setMsg("fail");
            response.setError("账户密码不正确");
            return response;
        }

        String token = loginService.login(userInfo);
        if(token == null){
            response.setCode(101401);
            response.setMsg("fail");
            response.setError("登录失败，出现未知异常");
        }else{
            response.setCode(200);
            response.setMsg("success");
            response.setData(new HashMap<String, Object>(){{
                put("access_token", token);
                put("expire_time", TimeUtil.parse(expireTime));
            }});
        }

        return response;
    }

    //        @GetMapping("/loginout")
//        public ResponseData loginOut(@RequestBody BaseUser user){
//       ResponseData result = userService.loginOut(user);
//
//            return result;
    /**
     * @Author Sorakado
     * @Date 2021/7/29 18:54
     * @Version 1.0
     **/
    @PostMapping("/complete")
    public ResponseData complete(@RequestBody CompanyInformation info) {

        ResponseData result = userService.complete(info);

        return result;
    }

}
