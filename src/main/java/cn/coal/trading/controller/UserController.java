package cn.coal.trading.controller;

import cn.coal.trading.bean.*;
import cn.coal.trading.services.*;
import cn.coal.trading.utils.TimeUtil;
import com.baomidou.shaun.core.annotation.HasPermission;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    @Resource
    FileService fileService;


    /**
     * 新增用户操作
     * TODO: 设定超级管理员权限
     * @Author jiyeme
     * @param user 用户信息
     * @return ResponseData
     */
    @PostMapping("/new")
    @HasRole(value = {"SUPER_ADMIN"})
    public ResponseData newUser(@RequestBody User user) {

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
    public ResponseData getRoleList(@PathVariable String type) {
        ResponseData responseData = new ResponseData();
        if("admin".equals(type)) {
            TokenProfile profile = ProfileHolder.getProfile();
            Set<String> permissions = profile.getPermissions();
            boolean user_add = permissions.contains("USER_ADD");
            if(!user_add){
                responseData.setCode(403);
                return responseData;
            }
        }

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
     *
     * @Author jiyec
     * @Date 2021/7/29 14:34
     * @Version 2.0
     **/
    @PostMapping("/register")
    public ResponseData register(@RequestBody User user) {
        ResponseData response = new ResponseData();
        boolean userExist = registerService.isUserExist(user.getLogin());
        if(userExist){
            response.setCode(102201);
            response.setMsg("fail");
            response.setError("用户名已被使用");
            return response;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPass(encoder.encode(user.getPass()));

        int register = registerService.register(user);

        if(register == 0){
            response.setCode(102201);
            response.setMsg("fail");
            response.setError("注册失败，原因未知");
        }else{
            response.setCode(200);
            response.setMsg("success");
        }
        return response;
    }
    /**
     * @Author Sorakado & jiyeme
     * @Date 2021/7/29 16:34
     * @Version 2.0
     **/
    @PostMapping("/login")
    public ResponseData login(@RequestBody User user) {
        ResponseData response = new ResponseData();
        User userInfo = loginService.getUserInfo(user);
        if(userInfo == null){
            //用户不存在
            response.setCode(101404);
            response.setMsg("fail");
            response.setError("用户不存在");
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


    /**
     * @Author Sorakado
     * @Date 2021/7/29 18:54
     * @Version 1.0
     * 企业信息完善（不包括财务开户)
     **/
    @HasPermission(value={"PUB_SALE","PUB_BUY"},logical = Logical.ANY)
    @PostMapping("/complete")
    public ResponseData complete(@RequestBody CompanyInformation info) {
        TokenProfile profile=ProfileHolder.getProfile();
        info.setUserId((long)Integer.parseInt(profile.getId()));

        ResponseData result = userService.complete(info);

        return result;
    }
    /**
     * @Author Sorakado
     * @Date 2021/7/31 22:26
     * @Version 1.0
     * 文件上传功能
     **/
    @HasRole(value={"PUB_SALE"})
    @PostMapping("/uploadFile")
    public ResponseData uploadFiles(@RequestPart MultipartFile[] multipartFile) throws IOException {

        ResponseData result = fileService.uploadFiles(multipartFile);
        return result;
    }

  //  @HasPermission(value={"PUB_SALE","PUB_BUY"},logical = Logical.ANY)
    @PostMapping("/finance")
    public ResponseData openFinancialAccount(@RequestBody FinanceProperty finance){
     //   TokenProfile profile=ProfileHolder.getProfile();
     //   fund.setUserId(Integer.parseInt(profile.getId()));
        finance.setFinanceUserid(7L);
        finance.setMainUserid(7L);
        ResponseData result = userService.finance(finance);
        return result;
    }

}
