package cn.coal.trading;

import cn.coal.trading.bean.Msg;
import cn.coal.trading.services.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
        for(int i=0; i < 100; i++) {
            int finalI = i+1;
            Msg msg = new Msg(){{
                setTitle("这是站内信标题" + finalI);
                setContent("这是消息内容" + finalI);
                setFromUserid(null);    // 系统消息userid置空
                setMsgType(1);
                setFromUsername("系统消息");
                setToUserid(1L);
                setToUsername("超级管理员");
            }};
            boolean send = msgService.send(msg);
            log.info("发送结果：{}", send);
        }
    }

    @Test
    void getMsg(){
        // 获取某个用户收到的消息
        Map<String, Object> msgMap = msgService.getMsgByToId(1L, 0, 10);
        log.info("{}", msgMap);

        // 获取来自系统的消息（这个用处似乎不大）
        List<Msg> msgList2 = msgService.getMsgByFromId(null);
        log.info("{}", msgList2);
    }
}
