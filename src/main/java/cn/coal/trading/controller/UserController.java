package cn.coal.trading.controller;

import cn.coal.trading.bean.*;
import cn.coal.trading.services.impl.CompanyServiceImpl;
import cn.coal.trading.services.impl.UserLoginServiceImpl;
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
        @Autowired
        UserLoginServiceImpl ls;
        @Autowired
        CompanyServiceImpl cs;

        @PostMapping("/register")
        public ResponseData regisered(@RequestBody BaseUser user){

            ResponseData result = rs.register(user);

            return result;
        }
        @GetMapping("/login")
        public ResponseData login(@RequestBody BaseUser user){
            ResponseData result = ls.login(user);

            return result;
        }
//        @GetMapping("/loginout")
//        public ResponseData loginOut(@RequestBody BaseUser user){
//       ResponseData result = rs.loginOut(user);
//
//            return result;
        @PostMapping("/complete")
        public ResponseData complete(@RequestBody CompanyInformation info){

            ResponseData result = cs.complete(info);

            return result;
        }

}
