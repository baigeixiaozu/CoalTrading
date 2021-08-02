package cn.coal.trading.services;

import cn.coal.trading.bean.*;

import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/29 18:33
 * @Version 1.0
 **/
public interface UserService {
    /**
     * 完善企业信息
     * @Author Sorakado
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    ResponseData complete(CompanyInformation companyInformation);
    /**
     * 新建用户（不是注册）
     *
     * @param user 要新增的用户
     * @return String null成功|”***“错误信息
     */
    String newUser(User user);

    /**
     * 获取指定角色类型的角色列表
     *
     * @param type 角色类型
     * @return 角色列表
     */
    List<Role> getRoleList(String type);
    /**
     * 生成企业财务账务表
     * @Author Sorakado
     * @Date 2021/8/1 15:04
     * @Version 1.0
     **/
    ResponseData finance(FinanceProperty financeProperty);
    /**
     * 生成财务用户
     * @Author Sorakado
     * @Date 2021/8/1 15:04
     * @Version 1.0
     **/
    Map<String,String> financeAccount();



}
