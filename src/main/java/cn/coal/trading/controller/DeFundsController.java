package cn.coal.trading.controller;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.FinanceStore;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.DeFundsMapper;
import cn.coal.trading.services.UserFileService;
import cn.coal.trading.services.FinanceStoreService;
import cn.coal.trading.services.impl.UserFileServiceImpl;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@HasRole("USER_MONEY")
@RequestMapping("/fin")
@Api(tags = "资金预存模块")
@ApiResponses({@ApiResponse(code = 200,message = "操作成功",response = ResponseData.class),
        @ApiResponse(code = 400,message = "参数列表错误",response = ResponseData.class),
        @ApiResponse(code = 401,message = "未授权",response = ResponseData.class),
        @ApiResponse(code = 403,message = "授权受限，授权过期",response = ResponseData.class),
        @ApiResponse(code = 404,message = "资源，服务未找到",response = ResponseData.class),
        @ApiResponse(code = 409,message = "资源冲突，或者资源被锁定",response = ResponseData.class),
        @ApiResponse(code = 429,message = "请求过多被限制",response = ResponseData.class),
        @ApiResponse(code = 500,message = "系统内部错误",response = ResponseData.class),
        @ApiResponse(code = 501,message = "接口未实现",response = ResponseData.class)})
@ApiSupport(author = "songyan.bai")
@RestController
public class DeFundsController {
    @Resource
    private DeFundsMapper deFundsMapper;
    @Resource
    FinanceStoreService financeStoreService;
    @Value("${ct.uploadFile.location}")
    String BaseUploadPath;

    @GetMapping("/info")
    @ApiOperation(value = "basicInfo",notes = "获取企业信息")
    public ResponseData basicInfo(){//数据库中为int，不是long
        ResponseData responseData=new ResponseData();
        TokenProfile profile = ProfileHolder.getProfile();
        int id=Integer.parseInt(profile.getId());
        List<FinanceProperty> financeProperties=deFundsMapper.getFinInfo(id);
        responseData.setData(financeProperties);
        if(financeProperties.size()==0){
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

    @Deprecated
    @ApiOperation(value = "setQuanity",notes = "设置预存资金数量")
    @PostMapping("/updateQ/{quantity}")//提交数量
    public ResponseData setQuantity(@PathVariable Double quantity){
        ResponseData responseData=new ResponseData();
        FinanceStore financeStore=new FinanceStore();
        TokenProfile profile = ProfileHolder.getProfile();
        Long id=Long.parseLong(profile.getId());
        Date date=new Date();
        financeStore.setUserId(id);
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

    @ApiOperation(value = "upload",notes = "提交文件")
    @PostMapping("/updateF/{path}")//提交文件
    public ResponseData upLoad(@PathVariable Double quantity, @PathVariable String path){
        ResponseData responseData=new ResponseData();
        UserFileService fileService=new UserFileServiceImpl();
        TokenProfile profile = ProfileHolder.getProfile();
        long id=Long.parseLong(profile.getId());
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

    @ApiOperation(value = "upload",notes = "资金预存，上传文件同时提交数据，By jiyecafe@gmail.com")
    @PostMapping("/store")
    public ResponseData store(@RequestBody MultipartFile cert, @RequestParam double quantity){
        ResponseData response = new ResponseData();
        if(cert.isEmpty()){
            response.setCode(400);
            response.setMsg("fail");
            response.setError("文件上传失败");
            return response;
        }
        TokenProfile profile = ProfileHolder.getProfile();
        String id = profile.getId();
        String suffix = StringUtils.substringAfter(cert.getOriginalFilename(), ".");

        // 构建文件路径
        String certBasePath = "finance/store/" + id + "/";


        try {
            String md5 = DigestUtils.md5DigestAsHex(cert.getInputStream());
            File dir = new File(BaseUploadPath + certBasePath);
            if(!dir.exists())dir.mkdirs();

            String certPath = certBasePath + "cert_" + md5 + "." + suffix;
            cert.transferTo(Paths.get(BaseUploadPath + certPath));
            boolean store = financeStoreService.store(quantity, certPath);

            if(store){
                response.setCode(200);
                response.setMsg("success");
            }else{
                response.setCode(201);
                response.setMsg("fail");
                response.setError("数据存储异常");
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMsg("fail");
            response.setError("文件存储失败");
            return response;
        }
    }
}
