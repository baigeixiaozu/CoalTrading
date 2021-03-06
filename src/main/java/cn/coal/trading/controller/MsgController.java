package cn.coal.trading.controller;

import cn.coal.trading.bean.Msg;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.MsgService;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/28 16:35
 * @Version 1.0
 **/
@Api(tags = "消息模块")
@RestController
@RequestMapping("/message")
@ApiResponses({@ApiResponse(code = 200,message = "操作成功",response = ResponseData.class),
        @ApiResponse(code = 400,message = "参数列表错误",response = ResponseData.class),
        @ApiResponse(code = 401,message = "未授权",response = ResponseData.class),
        @ApiResponse(code = 403,message = "授权受限，授权过期",response = ResponseData.class),
        @ApiResponse(code = 404,message = "资源，服务未找到",response = ResponseData.class),
        @ApiResponse(code = 409,message = "资源冲突，或者资源被锁定",response = ResponseData.class),
        @ApiResponse(code = 429,message = "请求过多被限制",response = ResponseData.class),
        @ApiResponse(code = 500,message = "系统内部错误",response = ResponseData.class),
        @ApiResponse(code = 501,message = "接口未实现",response = ResponseData.class)})
@Slf4j
@ApiSupport(author = "jiyec")
public class MsgController {
    @Resource
    MsgService msgService;

    @ApiOperation(value = "getMyMsgList",notes = "获取个人消息列表")
    @GetMapping("/myMsg")
    public Map<String, Object> getMyMsgList(@RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "10", required = false) Integer limit){
        TokenProfile profile = ProfileHolder.getProfile();
        long userId = Long.parseLong(profile.getId());
        Map<String, Object> msgMap = msgService.getMsgByToId(userId, page, limit);
        return new HashMap<String, Object>(){{
            put("code", 200);
            put("msg", "success");
            put("data", msgMap);
        }};
    }

    @ApiOperation(value = "msgDetail",notes = "获取详细消息")
    @GetMapping("/detail/{msg_id}")
    public ResponseData msgDetail(@PathVariable("msg_id") Integer msgId){
        ResponseData responseData = new ResponseData();
        // 获取当前登录用户的ID
        TokenProfile profile = ProfileHolder.getProfile();
        long userId = Long.parseLong(profile.getId());

        // 获取消息详情
        Msg msgDetail = msgService.getMsgDetail(msgId, userId);

        if(msgDetail == null){
            responseData.setCode(121404);
            responseData.setMsg("fail");
            responseData.setError("该消息不存在");
        }else{
            // 未读标记为已读
            if(msgDetail.getReadStatus() == 1)
                msgService.markAsRead(new HashSet<Long>(){{
                    add(msgDetail.getId());
                }}, userId);
            responseData.setCode(200);
            responseData.setMsg("success");
            responseData.setData(msgDetail);
        }
        return responseData;
    }

    @ApiOperation(value = "markAsRead",notes = "将消息设为已读")
    @PostMapping("/markAsRead")
    public ResponseData markAsRead(@RequestBody HashSet<Long> ids){
        log.info("{}", ids);
        ResponseData responseData = new ResponseData();
        if(ids.size() == 0)
            return  responseData;
        // 获取当前登录用户的ID
        TokenProfile profile = ProfileHolder.getProfile();
        long userId = Long.parseLong(profile.getId());

        boolean b = msgService.markAsRead(ids, userId);
        responseData.setCode(b?200:121201);
        responseData.setMsg(b?"success":"fail");
        return responseData;
    }
}
