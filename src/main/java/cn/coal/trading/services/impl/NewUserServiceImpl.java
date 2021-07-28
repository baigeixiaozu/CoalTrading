package cn.coal.trading.services.impl;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.Role;
import cn.coal.trading.bean.UserRoleBinding;
import cn.coal.trading.mapper.RoleMapper;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.mapper.UserRoleMapper;
import cn.coal.trading.services.NewUserService;
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
    /**
     * 新建用户（不是注册）
     * @return 0成功|1已存在|2角色分配失败
     */
    @Override
    public Integer newUser(BaseUser user) {
        // TODO:检查用户名是否存在
        int insert = userMapper.insert(user);
        if(insert == 0)return 1;
        Integer role = user.getRole();

        insert = userRoleMapper.insert(new UserRoleBinding(user.getId(), role));
        if(insert == 0) {
            userMapper.deleteById(user.getId());
            return 2;
        }
        return 0;
    }

    @Override
    public BaseUser findBaseUserById(String id) {
        return new BaseUser();
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.selectList(null);
    }

}
