package cn.coal.trading.controller;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.FinanceStore;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.DeFundsMapper;
import cn.coal.trading.services.impl.FileServiceImpl;
import com.baomidou.shaun.core.annotation.HasAuthorization;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@HasRole("USER_BUY")
@RequestMapping("/fin")
@RestController
public class deFundsController {
    private DeFundsMapper deFundsMapper;
    public deFundsController(DeFundsMapper deFundsMapper){
        this.deFundsMapper=deFundsMapper;
    }
    @GetMapping("/info")
    public ResponseData BasicInfo(){//数据库中为int，不是long
        ResponseData responseData=new ResponseData();
        TokenProfile profile = ProfileHolder.getProfile();
        int id=Integer.parseInt(profile.getId());
        List<FinanceProperty> financeProperties=deFundsMapper.getFinInfo(id);
        responseData.setData(financeProperties);
        if(financeProperties==null){
            responseData.setCode(400);
            responseData.setMsg("没了");
            responseData.setError("没了");
        }
        else{
            responseData.setCode(200);
            responseData.setMsg("对了");
            responseData.setError("无");
        }
        return responseData;
    }
//    @GetMapping("/update/{cert}/{quantity}")
//    public void TransInfo(@PathVariable String cert,@PathVariable Double quantity){
//        FinanceLog financeLog=new FinanceLog();
//        TokenProfile profile = ProfileHolder.getProfile();
//        Date date=new Date();
//        financeLog.setUserId(Long.parseLong(profile.getId()));
//        financeLog.setDate(date);
//        financeLog.setCert(cert);
//        financeLog.setQuantity(quantity);
//        deFundsMapper.TransInfo(financeLog);
//    }
    @PostMapping("/updateQ/{quantity}")//提交数量
    public ResponseData setQuantity(@PathVariable Double quantity){
        ResponseData responseData=new ResponseData();
        FinanceStore financeStore=new FinanceStore();
        TokenProfile profile = ProfileHolder.getProfile();
        Long id=Long.parseLong(profile.getId());
        Date date=new Date();
        financeStore.setUser_id(id);
        financeStore.setDate(date);
        financeStore.setCert(null);
        financeStore.setStatus(1);
        financeStore.setQuantity(quantity);
        Boolean flag=deFundsMapper.TransInfo(financeStore);
        if(!flag){
            responseData.setCode(400);
            responseData.setMsg("错误");
            responseData.setError("错误");
        }
        else if(flag){
            responseData.setCode(200);
            responseData.setMsg("对了");
            responseData.setError("无");
        }
        return responseData;
    }
    @PostMapping("/updateF/{path}")//提交文件
    public ResponseData upLoad(@PathVariable Double quantity, @PathVariable String path){
        ResponseData responseData=new ResponseData();
        FileServiceImpl fileService=new FileServiceImpl();
        TokenProfile profile = ProfileHolder.getProfile();
        Long id=Long.parseLong(profile.getId());
        Boolean flag2=fileService.storeCert2DB(path,null, id);
        Boolean flag1=deFundsMapper.updateF(path,id);
        if(flag1&&flag2){
            responseData.setCode(200);
            responseData.setMsg("对了");
            responseData.setError("无");
        }
        else{
            responseData.setCode(400);
            responseData.setMsg("错误");
            responseData.setError("错误");
        }
        return responseData;
    }
}
