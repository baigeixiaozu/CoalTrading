package cn.coal.trading.services.impl;

import cn.coal.trading.bean.User;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.mapper.UserRolePermitMapper;
import cn.coal.trading.services.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.shaun.core.mgt.SecurityManager;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/31 5:20
 * @Version 1.0
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private UserRolePermitMapper userRolePermitMapper;

    @Autowired
    private SecurityManager securityManager;

    @Override
    public User getUserInfo(User loginUser) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("login", loginUser.getLogin());

        return userMapper.selectOne(wrapper);
    }

    /**
     * @return
     * @Author jiyeme & Sorakado
     * @Date 2021/7/30 17:34
     * @Version 2.0
     */
    @Override
    public String login(User loginUser) {
        // 获取角色，权限
        final TokenProfile profile = new TokenProfile();
        profile.setId(loginUser.getId().toString());

        List<Map<String, Object>> relation = userRolePermitMapper.getRelation(loginUser.getId());

        // 角色只能一个
        profile.addRole((String) relation.get(0).get("role_mark"));

        //profile.setRoles(roles:Set);
        // 权限有多个
        for (Map<String, Object> map : relation) {
            profile.addPermission((String) map.get("permission"));
        }
        //profile.setPermissions(permissions:Set);
        //profile.addAttribute("key","value");
        //如果选择token存cookie里,securityManager.login会进行自动操作

        return securityManager.login(profile);
    }
}
