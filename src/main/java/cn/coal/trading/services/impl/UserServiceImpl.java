package cn.coal.trading.services.impl;

import cn.coal.trading.bean.*;
import cn.coal.trading.mapper.*;
import cn.coal.trading.services.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/7/29 18:34
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    FinanceMapper financeMapper;




    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public String newUser(User user) {

        Integer count = userMapper.selectCount(new QueryWrapper<User>() {{
            eq("login", user.getLogin());
        }});
        if (count > 0) {
            return "用户已存在";
        }
        int insert = userMapper.insert(user);
        if (insert == 0) {
            return "用户创建失败";
        }
        Integer role = user.getRole();

        insert = userRoleMapper.insert(new UserRoleBinding(user.getId(), role));
        if (insert == 0) {
            userMapper.deleteById(user.getId());
            return "用户角色绑定失败";
        }
        return null;
    }

    @Override
    public List<Role> getRoleList(String type) {
        return roleMapper.selectList(new QueryWrapper<Role>() {{
            eq("type", type);
        }});
    }

    /**
     * @Author Sorakado
     * @Date 2021/8/1 16:10
     * @Version 1.0
     **/
    @Override
    public ResponseData finance(FinanceProperty financeProperty) {
        ResponseData response=new ResponseData();

        int insert = financeMapper.insert(financeProperty);
        if(insert==1){
            response.setCode(201);
            response.setMsg("数据上传成功");
            response.setError("无");
            response.setData(financeProperty);




        }else{
            response.setData(null);
            response.setError("资源冲突，或者资源被锁定");
            response.setCode(409);
            response.setMsg("该公司已注册！或者未知错误");
        }

        return response;




    }






    /**
     * @Author Sorakado
     * @Date 2021/7/31 23:10
     * @Version 1.0
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData complete(CompanyInformation companyInformation) {


        ResponseData response = new ResponseData();
        int insert = companyMapper.insert(companyInformation);
        if(insert==1){
            response.setCode(201);
            response.setMsg("数据上传成功");
            response.setError("无");
            response.setData(companyInformation);






        }else{
            response.setData(null);
            response.setError("资源冲突，或者资源被锁定");
            response.setCode(409);
            response.setMsg("该公司已注册！或者未知错误");
        }

        return response;
    }

}
