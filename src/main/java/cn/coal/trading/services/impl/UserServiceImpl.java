package cn.coal.trading.services.impl;

import cn.coal.trading.bean.*;
import cn.coal.trading.mapper.*;
import cn.coal.trading.services.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.shaun.core.mgt.SecurityManager;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/29 18:34
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Resource
    RoleMapper roleMapper;


    @Autowired
    private CompanyMapper companyMapper;



    /**
     * @Author Sorakado
     * @ReWrite jiyeme
     *
     * @Date 2021/7/30 17:35
     * @Version 2.0
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData register(TradeUser user) {
        // TODO: 重写
        ResponseData response = new ResponseData();

        // 设置用户状态为待审核
        user.setStatus(1);
        int result = userMapper.insert(user);

        if (result == 1) {
            long userId = user.getId();

            UserRoleBinding urb = new UserRoleBinding(user.getId(), user.getRole());
            userRoleMapper.insert(urb);

            response.setCode(201);
            response.setMsg("创建成功");
            response.setError("无");
        } else {
            response.setCode(409);
            response.setMsg("创建失败，用户名已存在");
            response.setError("资源冲突，或者资源被锁定");
        }
        response.setData(null);
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
