package cn.coal.trading.controller;

import cn.coal.trading.bean.Msg;
import cn.coal.trading.services.MsgService;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/28 16:35
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class MsgController {
    @Resource
    MsgService msgService;

    // @Resource
    // SecurityManager securityManager;

    @GetMapping("/myMsg")
    public Map<String, Object> getMyMsg(){
        // TokenProfile profile = ProfileHolder.getProfile();
        // String id = profile.getId();
        String id = "1";
        Long userId = Long.parseLong(id);
        List<Msg> myMsg = msgService.getMsgByToId(userId);
        return new HashMap<String, Object>(){{
            put("code", 200);
            put("msg", "success");
            put("data", myMsg);
        }};
    }
}
