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
    /**
     * 新建用户（不是注册）
     *
     * @param user 要新增的用户
     * @return String null成功|”***“错误信息
     */
    String newUser(BaseUser user);
    List<Role> getRoleList(String type);
}
