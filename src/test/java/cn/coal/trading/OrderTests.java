package cn.coal.trading;

import cn.coal.trading.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Created by Heming233
 * Date:2021/8/9
 * version:v1.0
 */

@SpringBootTest
public class OrderTests {
    @Resource
    OrderService orderService;

    @Test
    void addOrder(){
        // Order order=orderService.addNewOrder(1L,2L);
    }
}
