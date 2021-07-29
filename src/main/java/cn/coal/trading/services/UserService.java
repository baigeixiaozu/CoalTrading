package cn.coal.trading.services;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.Role;

import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/7/29 18:33
 * @Version 1.0
 **/
public interface UserService {
    /**
     * @Author Sorakado
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    ResponseData complete(CompanyInformation companyInformation);
    /**
     * @Author Sorakado
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    ResponseData login(BaseUser user);
    /**
     * @Author Sorakado
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    ResponseData register(BaseUser user);
    /**
     * 新建用户（不是注册）
     *
     * @param user 要新增的用户
     * @return String null成功|”***“错误信息
     */
    String newUser(BaseUser user);

    /**
     * 获取指定角色类型的角色列表
     *
     * @param type 角色类型
     * @return 角色列表
     */
    List<Role> getRoleList(String type);
}
