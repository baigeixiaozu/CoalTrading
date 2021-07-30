package cn.coal.trading.services;

import cn.coal.trading.bean.BaseUser;

/**
 * @Author jiyec
 * @Date 2021/7/31 5:20
 * @Version 1.0
 **/
public interface LoginService {
    BaseUser getUserInfo(BaseUser user);
    /**
     * @Author Sorakado
     * @Date 2021/7/29 15:04
     * @Version 1.0
     *
     * @return*/
    String login(BaseUser user);
}
