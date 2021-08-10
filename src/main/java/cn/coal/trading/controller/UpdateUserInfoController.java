package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.User;
import cn.coal.trading.services.impl.UpdateServiceImpl;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户信息更新模块")
@RestController
@RequestMapping("/update")
@ApiSupport(author = "Sorakado")
public class UpdateUserInfoController {

    @Resource
    UpdateServiceImpl updateService;

    /**
     * @Author Sorakado
     * @Date 2021/8/2 23:04
     * @Version 1.0
     * 修改用户的基本信息
     **/
    @ApiOperation(value = "updateUserinfo",notes = "更改用基本信息")
    @HasRole(value={"USER_SALE","USER_BUY","USER_MONEY"},logical = Logical.ANY)
    @PatchMapping("/userinfo")
    public ResponseData updateUser(@RequestBody User user){
         TokenProfile profile= ProfileHolder.getProfile();
         user.setId((long)Integer.parseInt(profile.getId()));
        //user.setId(15L);

        ResponseData result = updateService.updateUser(user);
        return result;
    }
}
