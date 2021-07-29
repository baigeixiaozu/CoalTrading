package cn.coal.trading.services.impl;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.Role;
import cn.coal.trading.bean.UserRoleBinding;
import cn.coal.trading.mapper.RoleMapper;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.mapper.UserRoleMapper;
import cn.coal.trading.services.NewUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:56
 * @Version 1.0
 **/
@Service
public class NewUserServiceImpl implements NewUserService {

    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;
    @Resource
    RoleMapper roleMapper;

    @Override
    public String newUser(BaseUser user) {

        Integer count = userMapper.selectCount(new QueryWrapper<BaseUser>() {{
            eq("login", user.getLogin());
        }});
        if(count > 0){
            return "用户已存在";
        }
        int insert = userMapper.insert(user);
        if(insert == 0) {
            return "用户创建失败";
        }
        Integer role = user.getRole();

        insert = userRoleMapper.insert(new UserRoleBinding(user.getId(), role));
        if(insert == 0) {
            userMapper.deleteById(user.getId());
            return "用户角色绑定失败";
        }
        return null;
    }

    @Override
    public List<Role> getRoleList(String type) {
        return roleMapper.selectList(new QueryWrapper<Role>(){{
            eq("type", type);
        }});
    }

}
