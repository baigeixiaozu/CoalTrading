package cn.coal.trading.services;

import cn.coal.trading.bean.User;

import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/31 5:20
 * @Version 1.0
 **/
public interface LoginService {
    User getUserInfo(User user);
    /**
     * @Author Sorakado
     * @Date 2021/7/29 15:04
     * @Version 1.0
     *
     * @return*/
    Map<String, Object> login(User user);
}
