package cn.coal.trading.services;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.UserRole;

import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:51
 * @Version 1.0
 **/
public interface NewUserService {
    Map<String, Object> newUser();
    BaseUser findBaseUserById(String id);
    List<UserRole> getRoleList();
}
