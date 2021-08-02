package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.Request;
import cn.coal.trading.mapper.ReqMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/request")
public class RequestController {

    @Resource
    ReqMapper reqMapper;

    @GetMapping("/list/{type}")
    public ResponseData getList(@PathVariable("type")String type){
        ResponseData responseData = new ResponseData();

        return responseData;
    }

    @PostMapping("/publish")
    public ResponseData publish(@RequestBody Request request){
        // TODO: TEST Ver
        ResponseData responseData = new ResponseData();
        // int insert = reqMapper.insert(request);
        responseData.setData(new HashMap<String, Object>(){{
            // put("insert", insert);
            put("req-data", request);
        }});
        return responseData;
    }
}
