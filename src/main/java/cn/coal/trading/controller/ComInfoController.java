package cn.coal.trading.controller;
import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.mapper.CompanyMapper;
import kotlin.io.path.ExperimentalPathApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.coal.trading.bean.*;
import cn.coal.trading.services.*;

import java.util.List;

@RestController
@RequestMapping("/info")
public class ComInfoController {
    //    @Autowired
//    ComInfo comInfo;
    public ComInfoController(CompanyMapper companyMapper){this.companyMapper=companyMapper;}
    private CompanyMapper companyMapper;
    //    @GetMapping("/{id}")
//    public ResponseData getById(@PathVariable Long id){
//        ResponseData responseData=new ResponseData();
//        CompanyInformation companyInformation= comInfo.getCompanyInfoById(id);
//        if (companyInformation != null) {
//            responseData.setCode(200);
//            responseData.setMsg("success");
//            responseData.setData(companyInformation);
//        } else {
//            responseData.setCode(201);
//            responseData.setMsg("fail");
//            responseData.setError("未查询到此公司");
//        }
//        return responseData;
//    }
    @GetMapping("/{id}")
    public ResponseData BasicInfo(@PathVariable Long id){
        ResponseData responseData=new ResponseData();
        List<CompanyInformation> companyInformation=companyMapper.getInfo(id);
         responseData.setData(companyInformation);
         if(companyInformation==null){
             responseData.setCode(400);
             responseData.setMsg("没这个人");
             responseData.setError("查无此人");
         }
         else{
             responseData.setCode(200);
             responseData.setMsg("对了");
             responseData.setError("无");
         }
        return responseData;
    }
    @GetMapping("/{id}/{opinion}")
    public void Opinion(@PathVariable Long id,@PathVariable String opinion){
            companyMapper.Opinion(id, opinion);
    }
    @GetMapping("/status/{id}/{i}")
    public int verify(@PathVariable Long id,@PathVariable int i){
        companyMapper.verify(id,i);
        return i;
    }
    @GetMapping("/download/{id}")
    public ResponseData download(@PathVariable Long id){
         String down=companyMapper.download(id);
         ResponseData responseData=new ResponseData();
         if(down==null)
         {
             responseData.setCode(400);
             responseData.setMsg("wu");
             responseData.setError("无链接");
         }
         else{
             responseData.setData(down);
             responseData.setCode(201);
             responseData.setError("无");
             responseData.setMsg("wu");
         }
         return responseData;
    }
}
