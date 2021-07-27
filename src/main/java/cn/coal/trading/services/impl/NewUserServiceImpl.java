package cn.coal.trading.services.impl;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.UserRole;
import cn.coal.trading.mapper.UserRoleMapper;
import cn.coal.trading.services.NewUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:56
 * @Version 1.0
 **/
@Service
public class NewUserServiceImpl implements NewUserService {

    @Resource
    UserRoleMapper userRoleMapper;
    /**
     * 新建用户（不是注册）
     * @return
     */
    @Override
    public Map<String, Object> newUser() {
        return null;
    }

    @Override
    public BaseUser findBaseUserById(String id) {
        return new BaseUser();
    }

    @Override
    public List<UserRole> getRoleList() {
        return userRoleMapper.selectList(null);
    }

}
