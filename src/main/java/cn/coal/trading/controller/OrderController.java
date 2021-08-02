package cn.coal.trading.controller;

import cn.coal.trading.bean.Order;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.OrderService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/31 21:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("/order")
@HasRole(value = {"USER_SALE", "USER_BUY"}, logical = Logical.ANY)      // 部分功能需管理员？
@Slf4j
public class OrderController {
    @Resource
    OrderService orderService;
    @GetMapping("/list")
    public ResponseData orderList(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "1", required = false) int limit){
        log.info("{}, {}", page, limit);
        TokenProfile profile = ProfileHolder.getProfile();
        String id = profile.getId();
        ResponseData responseData = new ResponseData();

        Map<String, Object> result = orderService.list(new Order(){{
            setUserId(Long.parseLong(id));
        }}, page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(result);

        return responseData;
    }
}
