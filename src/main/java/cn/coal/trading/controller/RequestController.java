package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/request")
public class RequestController {

    @GetMapping("/list")
    public ResponseData getList(){
        ResponseData responseData = new ResponseData();

        return responseData;
    }
}
