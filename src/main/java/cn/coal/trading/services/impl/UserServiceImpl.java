package cn.coal.trading.services.impl;

import cn.coal.trading.bean.*;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.mapper.RoleMapper;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.mapper.UserRoleMapper;
import cn.coal.trading.services.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/7/29 18:34
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public UserRoleMapper userRoleMapper;
    @Resource
    RoleMapper roleMapper;

    @Autowired
    public CompanyMapper companyMapper;

    /**
     * @Author Sorakado
     * @Date 2021/7/30 17:34
     * @Version 2.0
     **/
    @Override
    public ResponseData login(BaseUser loginUser) {
        ResponseData response = new ResponseData();
        QueryWrapper<BaseUser> wrapper = new QueryWrapper<>();
        // 加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        wrapper.eq("login", loginUser.getLogin());

        BaseUser user = userMapper.selectOne(wrapper);
        boolean isPassRight = encoder.matches(loginUser.getPass(), user.getPass());
        if (isPassRight) {
            response.setCode(200);
            response.setMsg("登录成功");
            response.setError("无");
        } else {
            response.setCode(401);
            response.setMsg("账号密码输入有误！");
            response.setError("未授权");
        }
        return response;

    }

    /**
     * @Author Sorakado
     * @Date 2021/7/30 17:35
     * @Version 2.0
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData register(BaseUser registeredUser) {
        ResponseData response = new ResponseData();
        BaseUser baseUser = new BaseUser();

        baseUser.setLogin(registeredUser.getLogin());
        baseUser.setPass(registeredUser.getPass());
        baseUser.setNick(registeredUser.getNick());
        baseUser.setEmail(registeredUser.getEmail());

        int result = userMapper.insert(baseUser);

        if (result == 1) {

            int role = 0;
            if (baseUser.getNick().equals("供应商")) {
                role = 7;
            } else {
                role = 8;
            }
            UserRoleBinding urb = new UserRoleBinding(baseUser.getId(), role);
            userRoleMapper.insert(urb);

            response.setCode(201);
            response.setMsg("创建成功");
            response.setError("无");
            response.setData(null);

        } else {

            response.setCode(409);
            response.setMsg("创建失败，用户名已存在");
            response.setError("资源冲突，或者资源被锁定");
            response.setData(null);
        }
        return response;
    }

    @Override
    public String newUser(BaseUser user) {

        Integer count = userMapper.selectCount(new QueryWrapper<BaseUser>() {{
            eq("login", user.getLogin());
        }});
        if (count > 0) {
            return "用户已存在";
        }
        int insert = userMapper.insert(user);
        if (insert == 0) {
            return "用户创建失败";
        }
        Integer role = user.getRole();

        insert = userRoleMapper.insert(new UserRoleBinding(user.getId(), role));
        if (insert == 0) {
            userMapper.deleteById(user.getId());
            return "用户角色绑定失败";
        }
        return null;
    }

    @Override
    public List<Role> getRoleList(String type) {
        return roleMapper.selectList(new QueryWrapper<Role>() {{
            eq("type", type);
        }});
    }

    @Override
    public ResponseData complete(CompanyInformation companyInformation) {

        ResponseData response = new ResponseData();

        return null;
    }
}
