package cn.coal.trading.services;

import cn.coal.trading.bean.LoginUser;
import cn.coal.trading.bean.ResponseData;


import java.util.Map;

public interface UserLoginService {
    /**
     * @Author Sorakado
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    ResponseData login(LoginUser loginUserUser);
}
