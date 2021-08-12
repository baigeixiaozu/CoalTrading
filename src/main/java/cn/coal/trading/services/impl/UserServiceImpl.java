package cn.coal.trading.services.impl;

import cn.coal.trading.bean.*;
import cn.coal.trading.mapper.*;
import cn.coal.trading.services.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Resource
    UserMetaMapper userMetaMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData finance(FinanceProperty financeProperty) {
        ResponseData response = new ResponseData();
        //生成并获取财务用户的账号和密码
        Map<String, String> usermap = financeAccount();
        long financeUserid = Long.parseLong(usermap.get("id"));
        financeProperty.setFinanceUserid(financeUserid);

        //往企业用户财务表插入信息
        int insert = financeMapper.insert(financeProperty);
        if (insert == 1) {

            //准备往用户角色表插入数据
            UserRoleBinding financeUser = new UserRoleBinding();
            financeUser.setUserId(financeUserid);

            //查询财务用的roleid
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("name", "财务用户");

            Role role = roleMapper.selectOne(wrapper);

            financeUser.setRoleId(role.getId());

            userRoleMapper.insert(financeUser);

            Map map = new HashMap<String, Object>();
            map.put("registerInfo", financeProperty);
            map.put("financeAccount", usermap.get("login"));
            map.put("financePassword", usermap.get("pass"));
            response.setCode(201);
            response.setMsg("数据上传成功");
            response.setError("无");
            response.setData(map);

        } else {
            response.setData(null);
            response.setError("资源冲突，或者资源被锁定");
            response.setCode(409);
            response.setMsg("该公司已注册！或者未知错误");
        }

        return response;
    }

    /**
     * 随机生成财务账户【审核通过时才会调用】
     *
     * @Author Sorakado
     * @Date 2021/7/30 23:10
     * @Version 1.0
     **/
    @Override
    public Map<String, String> financeAccount() {
        HashMap<String, String> map = new HashMap<>();

        String login = "";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            login += String.valueOf(random.nextInt(10));
        }

        String pass = "";

        for (int i = 0; i < 10; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                pass += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                pass += String.valueOf(random.nextInt(10));
            }
        }
        User user = new User();

        user.setLogin(login);

        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String password = encoder.encode(pass);
        user.setPass(password);
        user.setStatus(2);
        int insert = userMapper.insert(user);
        if (insert == 1) {
            map.put("id", "" + user.getId());
            map.put("login", login);
            map.put("pass", pass);


            return map;
        }
        return null;
    }

    /**
     * @Author Sorakado
     * @Date 2021/8/3 23:10
     * @Version 1.0
     * 获取用户基本信息
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData getInfo(long id) {
        ResponseData response = new ResponseData();
        User user = userMapper.selectById(id);
        Map<String, Object> map = new HashMap<>(7);

        map.put("login", user.getLogin());

        map.put("nick", user.getNick());
        map.put("email", user.getEmail());

        QueryWrapper<UserRoleBinding> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        UserRoleBinding userRoleBinding = userRoleMapper.selectOne(wrapper);

        Role role = roleMapper.selectById(userRoleBinding.getRoleId());

        map.put("userType", role.getName());

        if (user != null) {
            response.setCode(200);
            response.setError("无");
            response.setMsg("获取数据成功");
            response.setData(map);
        } else {
            response.setCode(404);
            response.setError("资源，服务未找到");
            response.setMsg("获取失败");
            response.setData(null);
        }
        return response;
    }

    @Override
    public User getFullInfo(long id) {
        CompanyInformation userComInfo = companyMapper.selectOne(new QueryWrapper<CompanyInformation>() {{
            eq("user_id", id);
        }});
        FinanceProperty userFinanceInfo = financeMapper.selectOne(new QueryWrapper<FinanceProperty>() {{
            eq("main_userid", id);
        }});
        return new User(){{
            setComInfo(userComInfo);
            setFinanceInfo(userFinanceInfo);
        }};
    }

    /**
     * @Author Sorakado
     * @Date 2021/7/31 23:10
     * @Version 1.0
     * 完善企业信息
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData complete(CompanyInformation companyInformation) {

        ResponseData response = new ResponseData();
        // 企业信息存储更新
        int i = companyMapper.update(companyInformation, new UpdateWrapper<CompanyInformation>() {{
            eq("user_id", companyInformation.getUserId());
            set("status", 2);               // 置为审核阶段
        }});
        if (i == 0) {
            i = companyMapper.insert(companyInformation);
        }

        FinanceProperty financeInfo = companyInformation.getFinanceInfo();
        financeInfo.setMainUserid(null);
        financeInfo.setFinanceUserid(null);
        int update1 = financeMapper.update(financeInfo, new UpdateWrapper<FinanceProperty>() {{
            eq("main_userid", companyInformation.getUserId());
        }});
        if(update1 == 0){
            financeInfo.setMainUserid(companyInformation.getUserId());
            update1 = financeMapper.insert(financeInfo);
        }

        // 财务邮件存储更新
        String financeEmail = financeInfo.getEmail();
        int update = userMetaMapper.update(new UserMeta() {{
            setMvalue(financeEmail);
        }}, new UpdateWrapper<UserMeta>() {{
            eq("user_id", companyInformation.getUserId());
            eq("mkey", "finance_email");
        }});
        // update失败就insert
        if (update == 0) {
            update = userMetaMapper.insert(new UserMeta() {{
                setUserId(companyInformation.getUserId());
                setMkey("finance_email");
                setMvalue(financeEmail);
            }});
        }

        if (i == 1 && update1 ==1 && update == 1) {
            response.setCode(200);
            response.setMsg("数据上传成功");
            response.setError("无");
            response.setData(companyInformation);
        } else {
            response.setData(null);
            response.setError("资源冲突，或者资源被锁定");
            response.setCode(409);
            response.setMsg("该公司已注册！或者未知错误");
        }

        return response;
    }


}
