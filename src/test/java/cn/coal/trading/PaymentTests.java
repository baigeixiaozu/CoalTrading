package cn.coal.trading;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Created by Heming233
 * Date:2021/8/9
 * version:v1.1
 */

@SpringBootTest
public class PaymentTests {
    @Resource
    PaymentService paymentService;

    @Test
    void show(){
        FinanceProperty user=paymentService.getFinanceById(8L);
    }
}
