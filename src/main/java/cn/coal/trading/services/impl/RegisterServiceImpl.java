package cn.coal.trading.services.impl;

import cn.coal.trading.bean.User;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.UserRoleBinding;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.mapper.UserRoleMapper;
import cn.coal.trading.services.RegisterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author jiyec
 * @Date 2021/7/31 6:39
 * @Version 1.0
 **/
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    @Override
    public boolean isUserExist(String login) {
        Integer count = userMapper.selectCount(new QueryWrapper<User>() {{
            eq("login", login);
        }});
        return count != null && count > 0;
    }

    /**
     * 基本注册
     *
     * @Author Sorakado
     * @ReWrite jiyeme
     *
     * @Date 2021/7/30 17:35
     * @Version 2.0
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int register(User user) {

        // 设置用户状态为基础注册状态
        user.setStatus(2);
        return userMapper.insert(user);
    }
}
