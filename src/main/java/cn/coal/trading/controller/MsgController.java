package cn.coal.trading.controller;

import cn.coal.trading.bean.Msg;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/28 16:35
 * @Version 1.0
 **/
@RestController
@RequestMapping("/message")
@Slf4j
public class MsgController {
    @Resource
    MsgService msgService;

    // @Resource
    // SecurityManager securityManager;

    @GetMapping("/myMsg")
    public Map<String, Object> getMyMsgList(@RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "10", required = false) Integer limit){
        // TokenProfile profile = ProfileHolder.getProfile();
        // String id = profile.getId();
        String id = "1";
        Long userId = Long.parseLong(id);
        Map<String, Object> msgMap = msgService.getMsgByToId(userId, page, limit);
        return new HashMap<String, Object>(){{
            put("code", 200);
            put("msg", "success");
            put("data", msgMap);
        }};
    }

    @GetMapping("/detail/{msg_id}")
    public ResponseData msgDetail(@PathVariable("msg_id") Integer msgId){
        ResponseData responseData = new ResponseData();
        Long userId = 1L;
        Msg msgDetail = msgService.getMsgDetail(msgId, userId);
        if(msgDetail == null){
            responseData.setCode(121404);
            responseData.setMsg("fail");
            responseData.setError("没有该消息");
        }else{
            responseData.setCode(200);
            responseData.setMsg("success");
            responseData.setData(msgDetail);
        }
        return responseData;
    }
}
