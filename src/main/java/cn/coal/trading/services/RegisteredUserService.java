package cn.coal.trading.services;


import cn.coal.trading.bean.RegisteredUser;


import java.util.Map;

/**
 * @Author Sorakado
 * @Date 2021/7/28 20:13
 * @Version 1.0
 **/
public interface RegisteredUserService {
    Map<String,Object> register(RegisteredUser registeredUser);
}
