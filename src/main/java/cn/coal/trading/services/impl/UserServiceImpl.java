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
    private SecurityManager securityManager;

    @Autowired
    private CompanyMapper companyMapper;

    @Resource
    private UserRolePermitMapper userRolePermitMapper;

    /**
     * @Author Sorakado
     * @Date 2021/7/30 17:34
     * @Version 2.0
     *
     * @return*/
    @Override
    public String login(BaseUser loginUser) {
        QueryWrapper<BaseUser> wrapper = new QueryWrapper<>();
        // 加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        wrapper.eq("login", loginUser.getLogin());

        BaseUser user = userMapper.selectOne(wrapper);
        boolean isPassRight = encoder.matches(loginUser.getPass(), user.getPass());
        String token = null;
        if(isPassRight){
            // 获取角色，权限
            final TokenProfile profile = new TokenProfile();
            profile.setId(user.getId().toString());

            List<Map<String, Object>> relation = userRolePermitMapper.getRelation(user.getId());

            profile.addRole((String)relation.get(0).get("role_mark"));

            //profile.setRoles(roles:Set);
            for (Map<String, Object> map : relation) {
                profile.addPermission((String)map.get("permission"));
            }
            //profile.setPermissions(permissions:Set);
            //profile.addAttribute("key","value");
            token = securityManager.login(profile);
            //如果选择token存cookie里,securityManager.login会进行自动操作
        }
        return token;
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



            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            wrapper.eq("name",baseUser.getNick());
            Role roleUser = roleMapper.selectOne(wrapper);


            UserRoleBinding urb = new UserRoleBinding(baseUser.getId(), roleUser.getId());
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
