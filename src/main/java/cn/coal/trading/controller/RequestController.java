package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.reqdata.BuyPubData;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/request")
public class RequestController {

    @GetMapping("/list/{type}")
    public ResponseData getList(){
        ResponseData responseData = new ResponseData();

        return responseData;
    }

    // TEST Ver
    @PostMapping("/publish")
    public ResponseData publish(@RequestBody BuyPubData data){
        ResponseData responseData = new ResponseData();
        responseData.setData(data);
        return responseData;
    }
}
