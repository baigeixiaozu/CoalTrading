package cn.coal.trading.services.impl;

import cn.coal.trading.bean.User;
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
    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    @Override
    public boolean isUserExist(String login, String email) {
        Integer count = userMapper.selectCount(new QueryWrapper<User>() {{
            eq("login", login).or().eq("email", email);
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
    public String register(User user) {

        // 设置用户状态为基础注册状态
        user.setStatus(2);
        int insert = userMapper.insert(user);
        if(insert == 1){
            int insert1 = userRoleMapper.insert(new UserRoleBinding(user.getId(), user.getRole()));
            if(insert1 == 0){
                userMapper.deleteById(user.getId());
                return "角色绑定失败";
            }
        }else{
            return "新增用户失败，原因未知";
        }
        return null;
    }
}
