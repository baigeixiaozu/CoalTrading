package cn.coal.trading;

import cn.coal.trading.bean.FinanceLog;
import cn.coal.trading.mapper.FinanceLMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * Finance Log Test 资金日志测试
 *
 * @Author jiyec
 * @Date 2021/8/1 18:52
 * @Version 1.0
 **/
@SpringBootTest
@Slf4j
public class FLTests {

    @Resource
    FinanceLMapper financeLMapper;
    @Test
    public void doLog(){
        FinanceLog log = new FinanceLog(){{
            setUserId(7L);
            setLogType(1);
            setFundQuantity(10.53);
            setCert(".././/.");
        }};
        financeLMapper.insert(log);
    }

    @Test
    public void getLog(){
        List<FinanceLog> financeLogs = financeLMapper.selectList(null);
        log.info("{}", financeLogs);
    }
}
