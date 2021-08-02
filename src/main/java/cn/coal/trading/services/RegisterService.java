package cn.coal.trading.services;

import cn.coal.trading.bean.User;
import cn.coal.trading.bean.ResponseData;

/**
 * @Author jiyec
 * @Date 2021/7/31 6:39
 * @Version 1.0
 **/
public interface RegisterService {
    boolean isUserExist(String login);
    /**
     * @Author Sorakado
     * @ReWrite jiyeme
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    String register(User user);
}
