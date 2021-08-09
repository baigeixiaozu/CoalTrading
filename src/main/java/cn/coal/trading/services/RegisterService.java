package cn.coal.trading.services;

import cn.coal.trading.bean.User;

/**
 * @Author jiyec
 * @Date 2021/7/31 6:39
 * @Version 1.0
 **/
public interface RegisterService {
    /**
     * 判断登录名是否被占用
     * @param login 登录名
     * @param email
     * @return  boolean true占用|false未占用
     */
    boolean isUserExist(String login, String email);

    /**
     * @Author Sorakado
     * @ReWrite jiyeme
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    String register(User user);
}
