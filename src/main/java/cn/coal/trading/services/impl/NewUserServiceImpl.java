package cn.coal.trading.services.impl;

import cn.coal.trading.bean.BaseUser;
import cn.coal.trading.services.NewUserService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:56
 * @Version 1.0
 **/
@Service
public class NewUserServiceImpl implements NewUserService {

    /**
     * 新建用户（不是注册）
     * @return
     */
    @Override
    public Map<String, Object> newUser() {
        return null;
    }

    @Override
    public BaseUser findBaseUserById(String id) {
        return new BaseUser();
    }
}
