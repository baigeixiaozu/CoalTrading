package cn.coal.trading.controller;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.FinanceStore;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.DeFundsMapper;
import cn.coal.trading.services.impl.FileServiceImpl;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Api(value = "资金预存模块")
@HasRole("USER_BUY")
@RequestMapping("/fin")
@RestController
public class deFundsController {
    private DeFundsMapper deFundsMapper;
    public deFundsController(DeFundsMapper deFundsMapper){
        this.deFundsMapper=deFundsMapper;
    }
    @GetMapping("/{id}")
    public ResponseData BasicInfo(@PathVariable Long id){
        ResponseData responseData=new ResponseData();
        List<FinanceProperty> financeProperties=deFundsMapper.getFinInfo(id);
        responseData.setData(financeProperties);
        if(financeProperties==null){
            responseData.setCode(400);
            responseData.setMsg("没");
            responseData.setError("额");
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
    @GetMapping("/updateQ/{quantity}/{multipartFile}")//提交数量
    public void setQuantity(@PathVariable Double quantity, @PathVariable MultipartFile multipartFile){
        FinanceStore financeStore=new FinanceStore();
        FileServiceImpl fileService=new FileServiceImpl();
        TokenProfile profile = ProfileHolder.getProfile();
        Long id=Long.parseLong(profile.getId());
        Date date=new Date();
        financeStore.setUserId(Long.parseLong(profile.getId()));
        financeStore.setDate(date);
        financeStore.setCert(null);
        financeStore.setStatus(1);
        financeStore.setQuantity(quantity);
        deFundsMapper.TransInfo(financeStore);
    }
    @GetMapping("/updateF/{multipartFile}")//提交数量
    public void upLoad(@PathVariable Double quantity, @PathVariable MultipartFile multipartFile){
        FileServiceImpl fileService=new FileServiceImpl();
        TokenProfile profile = ProfileHolder.getProfile();
        Long id=Long.parseLong(profile.getId());
        String cert=fileService.storeFile2Local(multipartFile,null, id);
        deFundsMapper.updateF(cert,id);
    }
}
