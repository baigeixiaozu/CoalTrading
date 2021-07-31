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
     * @Author Sorakado
     * @ReWrite jiyeme
     *
     * @Date 2021/7/30 17:35
     * @Version 2.0
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData register(User user) {
        // TODO: 重写
        ResponseData response = new ResponseData();

        // 设置用户状态为待审核
        user.setStatus(1);
        int result = userMapper.insert(user);

        if (result == 1) {
            long userId = user.getId();

            UserRoleBinding urb = new UserRoleBinding(user.getId(), user.getRole());
            userRoleMapper.insert(urb);

            response.setCode(201);
            response.setMsg("创建成功");
            response.setError("无");
        } else {
            response.setCode(409);
            response.setMsg("创建失败，用户名已存在");
            response.setError("资源冲突，或者资源被锁定");
        }
        response.setData(null);
        return response;
    }
}
