package cn.coal.trading.controller;

import cn.coal.trading.bean.*;
import cn.coal.trading.services.UserFileService;
import cn.coal.trading.services.LoginService;
import cn.coal.trading.services.RegisterService;
import cn.coal.trading.services.UserService;
import cn.coal.trading.utils.TimeUtil;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "用户管理", tags = "用户管理")

@ApiResponses({@ApiResponse(code = 200, message = "操作成功", response = ResponseData.class),
        @ApiResponse(code = 400, message = "参数列表错误", response = ResponseData.class),
        @ApiResponse(code = 401, message = "未授权", response = ResponseData.class),
        @ApiResponse(code = 403, message = "授权受限，授权过期", response = ResponseData.class),
        @ApiResponse(code = 404, message = "资源，服务未找到", response = ResponseData.class),
        @ApiResponse(code = 409, message = "资源冲突，或者资源被锁定", response = ResponseData.class),
        @ApiResponse(code = 429, message = "请求过多被限制", response = ResponseData.class),
        @ApiResponse(code = 500, message = "系统内部错误", response = ResponseData.class),
        @ApiResponse(code = 501, message = "接口未实现", response = ResponseData.class)})
@RestController
@RequestMapping("/user")
@ApiSupport(author = "Sorakado & jiyeme")
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
    UserFileService userFileService;

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

        // TODO: BEAN处理
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPass(encoder.encode(user.getPass()));
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
    @ApiOperation(value = "getRoleList", notes = "获取角色列表")
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
     * 用户注册功能
     **/
    @ApiOperation(value = "register", notes = "注册新用户（平台外）")
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
     * 用户登录功能
     **/
    @ApiOperation(value = "login", notes = "用户登录")
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
    @ApiOperation(value = "completeCominfo", notes = "用户完善企业信息")
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
     * @REFACTOR jiyecafe@gmail.com
     * @Date 2021/7/31 22:26
     * @Version 1.0
     * 文件上传功能
     **/
    @ApiOperation(value = "uploadFile", notes = "企业上传资质文件")
    @HasRole(value = {"USER_SALE", "USER_BUY"}, logical = Logical.ANY)
    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestPart("file") MultipartFile file, @RequestParam CertType type) {
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
        String storePath = userFileService.storeFile2Local(file, type, userId);
        if (storePath == null) {
            response.setCode(400);
            response.setMsg("fail");
            response.setError("文件转存失败！");
            return response;
        }

        // 存储路径到数据库
        boolean b = userFileService.storeCert2DB(storePath, type, userId);
        if (!b) {
            response.setCode(400);
            response.setMsg("fail");
            response.setError("文件数据存储失败");
            return response;
        }
        response.setCode(200);
        response.setMsg("success");
        response.setData(new HashMap<String, String>() {{
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
    @Deprecated
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
    @ApiOperation(value = "getUserInfo", notes = "获取登录用户的个人信息")
    @GetMapping("/info")
    public ResponseData getUserInfo() {
        TokenProfile profile = ProfileHolder.getProfile();
        ResponseData result = userService.getInfo(Long.parseLong(profile.getId()));
        return result;
    }

    @ApiOperation(value = "getUserFullInfo", notes = "获取企业所有注册信息")
    @GetMapping("/fullInfo")
    public ResponseData getUserFullInfo() {
        ResponseData responseData = new ResponseData();
        TokenProfile profile = ProfileHolder.getProfile();
        String id = profile.getId();
        User fullInfo = userService.getFullInfo(Long.parseLong(id));
        responseData.setData(fullInfo);
        responseData.setCode(200);
        responseData.setMsg("success");
        return responseData;
    }

}
