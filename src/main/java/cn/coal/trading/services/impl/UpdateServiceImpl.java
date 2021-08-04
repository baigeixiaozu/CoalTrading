package cn.coal.trading.services.impl;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.User;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.services.UpdateService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UpdateServiceImpl implements UpdateService {
    
    @Resource
    UserMapper userMapper;

    /**
     * @Author Sorakado
     * @Date 2021/8/2 22:48
     * @Version 1.0
     **/
    @Override
    public ResponseData updateUser(User user) {
        ResponseData response = new ResponseData();
        if(user.getPass()!=null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
            String password = encoder.encode(user.getPass());
            user.setPass(password);
        }
        int i = userMapper.updateById(user);

        if(i==1){
            response.setCode(201);
            response.setMsg("数据上传成功");
            response.setError("无");
            response.setData(user);

        }else{
            response.setData(null);
            response.setError("资源冲突，或者资源被锁定");
            response.setCode(409);
            response.setMsg("未知错误");
        }

        return response;
    }
}
