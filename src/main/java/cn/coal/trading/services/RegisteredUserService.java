package cn.coal.trading.services;


import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.bean.ResponseData;

/**
 * @Author Sorakado
 * @Date 2021/7/28 20:13
 * @Version 1.0
 **/
public interface RegisteredUserService {
    ResponseData register(BaseUser user);
}
