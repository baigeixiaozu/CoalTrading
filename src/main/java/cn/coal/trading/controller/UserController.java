package cn.coal.trading.controller;

import cn.coal.trading.bean.*;
import cn.coal.trading.services.*;
import cn.coal.trading.utils.TimeUtil;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sorakado & jiyeme
 * @veuserServiceion v2.0
 * @time 7.29 11:02
 */
@Api(value = "用户管理",tags = "用户管理")
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
     *
     * @param user 用户信息
     * @return ResponseData
     * @Author jiyeme
     */
    @ApiOperation(value = "newUser", notes = "新增用户")
    @PostMapping("/new")
    @HasRole(value = {"SUPER_ADMIN"})
    public ResponseData newUser(@RequestBody User user) {

        ResponseData responseData = new ResponseData();

        if (user.getId() != null) {
            responseData.setCode(105201);
            responseData.setMsg("参数非法");
            return responseData;
        }
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
            responseData.setCode(105201);
            responseData.setMsg("角色数据不能为空");
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
     * @param type 角色类型
     * @return ResponseData
     * @Author jiyeme
     */
    @GetMapping({"/getRoleList/{type}"})
    public ResponseData getRoleList(@PathVariable String type) {
        ResponseData responseData = new ResponseData();
        if ("admin".equals(type)) {
            TokenProfile profile = ProfileHolder.getProfile();
            Set<String> permissions = profile.getPermissions();
            boolean user_add = permissions.contains("USER_ADD");
            if (!user_add) {
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
     * @Author jiyec
     * @Date 2021/7/29 14:34
     * @Version 2.0
     **/
    @PostMapping("/register")
    public ResponseData register(@RequestBody User user) {
        ResponseData response = new ResponseData();
        if (user.getId() != null) {
            response.setCode(102201);
            response.setMsg("参数非法");
            return response;
        }
        if (user.getRole() == null) {
            response.setCode(102403);
            response.setMsg("fail");
            response.setError("缺少角色参数");
            return response;
        }
        boolean userExist = registerService.isUserExist(user.getLogin(), user.getEmail());
        if (userExist) {
            response.setCode(102201);
            response.setMsg("fail");
            response.setError("用户名或邮箱已被使用");
            return response;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPass(encoder.encode(user.getPass()));

        String register = registerService.register(user);

        if (register != null) {
            response.setCode(102201);
            response.setMsg("fail");
            response.setError(register);
        } else {
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
        if (userInfo == null) {
            //用户不存在
            response.setCode(101404);
            response.setMsg("fail");
            response.setError("用户不存在");
            return response;
        }

        // 加密  密码检查
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        boolean isPassRight = encoder.matches(user.getPass(), userInfo.getPass());

        if (!isPassRight) {
            //密码不正确
            response.setCode(101401);
            response.setMsg("fail");
            response.setError("账户密码不正确");
            return response;
        }

        Map<String, Object> login = loginService.login(userInfo);
        if (login == null) {
            response.setCode(101401);
            response.setMsg("fail");
            response.setError("登录失败，出现未知异常");
        } else {
            login.put("expire_time", TimeUtil.parse(expireTime));
            response.setCode(200);
            response.setMsg("success");
            response.setData(login);
        }

        return response;
    }

    /**
     * @Author Sorakado
     * @Optimize jiye
     * @Date 2021/7/29 18:54
     * @Version 1.0
     * 企业信息完善（不包括财务开户)
     **/
    @HasRole(value = {"USER_SALE", "USER_BUY"}, logical = Logical.ANY)
    @PostMapping("/complete")
    public ResponseData complete(@RequestBody CompanyInformation info) {
        TokenProfile profile = ProfileHolder.getProfile();
        info.setUserId(Long.parseLong(profile.getId()));
        //  info.setUserId(7L);
        ResponseData result = userService.complete(info);

        return result;
    }

    /**
     * @Author Sorakado
     * @REFACTOR jiye
     * @Date 2021/7/31 22:26
     * @Version 1.0
     * 文件上传功能
     **/
    @HasRole(value = {"USER_SALE", "USER_BUY"}, logical = Logical.ANY)
    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestPart("file") MultipartFile file, @RequestParam CertType type){
        ResponseData response = new ResponseData();
        if (file.isEmpty()) {
            response.setCode(400);
            response.setMsg("fail");
            response.setError("文件上传失败！");
            response.setData(null);
            return response;
        }
        TokenProfile profile = ProfileHolder.getProfile();
        long userId = Long.parseLong(profile.getId());

        // 转存文件到服务器
        String storePath = fileService.storeFile2Local(file, type, userId);
        if(storePath == null ){
            response.setCode(400);
            response.setMsg("fail");
            response.setError("文件转存失败！");
            return response;
        }

        // 存储路径到数据库
        boolean b = fileService.storeCert2DB(storePath, type, userId);
        if(!b){
            response.setCode(400);
            response.setMsg("fail");
            response.setError("文件数据存储失败");
            return response;
        }
        response.setCode(200);
        response.setMsg("success");
        response.setData(new HashMap<String, String>(){{
            put("path", storePath);
        }});

        return response;
    }

    /**
     * @Author Sorakado
     * @Date 2021/8/2 18:47
     * @Version 1.0
     * 生成企业财务账户表和财务用户功能
     **/
    @HasRole(value = {"USER_SALE", "USER_BUY"}, logical = Logical.ANY)
    @PostMapping("/finance")
    public ResponseData openFinancialAccount(@RequestBody FinanceProperty finance) {
        TokenProfile profile = ProfileHolder.getProfile();
        finance.setMainUserid((long) Integer.parseInt(profile.getId()));
        //  finance.setMainUserid(8L);
        ResponseData result = userService.finance(finance);
        return result;
    }

    /**
     * @Author Sorakado
     * @Date 2021/8/4 10:47
     * @Version 1.0
     * 获取登录用户的基本信息
     **/
    @GetMapping("info")
    public ResponseData getUserInfo() {
        TokenProfile profile = ProfileHolder.getProfile();
        ResponseData result = userService.getInfo(Long.parseLong(profile.getId()));
        return result;
    }

}
