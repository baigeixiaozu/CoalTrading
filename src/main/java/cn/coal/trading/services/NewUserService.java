package cn.coal.trading.services;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.Role;

import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:51
 * @Version 1.0
 **/
public interface NewUserService {
    Integer newUser(BaseUser user);
    BaseUser findBaseUserById(String id);
    List<Role> getRoleList();
}
