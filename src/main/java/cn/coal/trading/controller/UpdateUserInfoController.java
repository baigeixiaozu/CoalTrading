package cn.coal.trading.controller;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.User;
import cn.coal.trading.services.impl.UpdateServiceImpl;
import com.baomidou.shaun.core.annotation.HasPermission;
import com.baomidou.shaun.core.annotation.Logical;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/update")
public class UpdateUserInfoController {

    @Resource
    UpdateServiceImpl updateService;

    /**
     * @Author Sorakado
     * @Date 2021/8/2 23:04
     * @Version 1.0
     **/

   // @HasPermission(value={"PUB_SALE","PUB_BUY"},logical = Logical.ANY)
    @PostMapping("/userinfo")
    public ResponseData updateUser(@RequestBody User user){
        // TokenProfile profile=ProfileHolder.getProfile();
        // user.setId((long)Integer.parseInt(profile.getId()));
        user.setId(15L);
        ResponseData result = updateService.updateUser(user);
        return result;
    }
}
