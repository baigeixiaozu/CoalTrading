package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.Request;
import cn.coal.trading.services.RequestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/request")
public class RequestController {

    @Resource
    RequestService requestService;

    @GetMapping("/list")
    public ResponseData getList(@RequestParam(defaultValue = "null", required = false) Integer type, @RequestParam int page, @RequestParam int limit){
        ResponseData responseData = new ResponseData();
        Map<String, Object> list = requestService.list(null, page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(list);
        return responseData;
    }

    /**
     * 需求发布，草稿
     *
     */
    @PostMapping("/publish")
    public ResponseData publish(@RequestBody Request request){
        ResponseData responseData = new ResponseData();

        // 挂牌参数校验
        if ( request.getId()!=null || request.getUserId()!=null || request.getZpId()!=null){
            responseData.setError("参数非法");
            responseData.setCode(21201);
            responseData.setMsg("fail");
            return responseData;
        }

        int affect = requestService.newReq(request);
        if(affect == 1){
            responseData.setCode(200);
            responseData.setMsg("success");
        }else{
            responseData.setCode(201);
            responseData.setMsg("fail");
        }
        return responseData;
    }

    /**
     * 需求编辑
     */
    @PostMapping("/edit")
    public ResponseData edit(@RequestBody Request request){
        ResponseData responseData = new ResponseData();
        int edit = requestService.edit(request);
        if(edit == 1){
            responseData.setCode(200);
            responseData.setMsg("success");
        }else{
            responseData.setCode(31201);
            responseData.setMsg("fail");
        }
        return responseData;
    }
}
