package cn.coal.trading.controller;

import cn.coal.trading.bean.LoginUser;
import cn.coal.trading.bean.RegisteredUser;
import cn.coal.trading.services.impl.RegisteredUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * @author Sorakado
 * @time 7.29 11:02
 * @version v2.0
 */
@RestController
@RequestMapping("/user")
public class UserController {


        @Autowired
        RegisteredUserServiceImpl rs;

        @PostMapping("/register")
        public Map<String,Object> regisered(@RequestBody RegisteredUser user){

            Map<String, Object> result = rs.register(user);

            return result;
        }
//        @GetMapping("/login")
//        public Map<String,Object> login(@RequestBody LoginUser user){
//            Map<String, Object> result = rs.login(user);
//
//            return result;
//        }
//        @GetMapping("/loginout")
//        public Map<String,Object> loginOut(@RequestBody LoginUser user){
//        Map<String, Object> result = rs.loginOut(user);
//
//            return result;

}
