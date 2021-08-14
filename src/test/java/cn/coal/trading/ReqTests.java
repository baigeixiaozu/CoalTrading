package cn.coal.trading;

import cn.coal.trading.bean.Request;
import cn.coal.trading.mapper.ReqMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/8/2 16:44
 * @Version 1.0
 **/
@SpringBootTest
public class ReqTests {
    @Resource
    ReqMapper reqMapper;

    @Test
    public void getReqTest(){
        // List<Request> requests = reqMapper.selectList(null);
        // System.out.println(requests);
    }
}
