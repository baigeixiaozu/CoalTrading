package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jiyec
 * @Date 2021/7/31 21:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("/order")
@HasRole(value = {"USER_SALE", "USER_BUY"}, logical = Logical.ANY)      // 部分功能需管理员？
public class OrderController {
    @GetMapping("/list")
    public ResponseData orderList(){

        return null;
    }
}
