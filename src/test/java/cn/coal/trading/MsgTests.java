package cn.coal.trading;

import cn.coal.trading.bean.Msg;
import cn.coal.trading.services.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/7/28 15:13
 * @Version 1.0
 **/
@SpringBootTest
@Slf4j
public class MsgTests {
    @Resource
    MsgService msgService;

    @Test
    void send(){
        Msg msg = new Msg(){{
            setContext("这是消息内容");
            setFromUserid(null);    // 系统消息userid置空
            setFromUsername("系统消息");
            setToUserid(1L);
            setToUsername("超级管理员");
        }};
        boolean send = msgService.send(msg);
        log.info("发送结果：{}", send);
    }

    @Test
    void getMsg(){
        // 获取某个用户收到的消息
        List<Msg> msgList1 = msgService.getMsgByToId(1L);
        log.info("{}", msgList1);

        // 获取来自系统的消息（这个用处似乎不大）
        List<Msg> msgList2 = msgService.getMsgByFromId(null);
        log.info("{}", msgList2);
    }
}
