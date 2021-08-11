package cn.coal.trading.controller;
import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.impl.FileServiceImpl;
import com.baomidou.shaun.core.annotation.HasRole;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@HasRole("USER_REG_AUDITOR")
@RestController
@RequestMapping("/info")
public class ComInfoController {
    public ComInfoController(CompanyMapper companyMapper){this.companyMapper=companyMapper;}
    private CompanyMapper companyMapper;
    ResponseData responseData=new ResponseData();
    FileServiceImpl fileService=new FileServiceImpl();
    @GetMapping("/{id}")
    public ResponseData BasicInfo(@PathVariable Long id){
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
    public ResponseData Opinion(@PathVariable Long id,@PathVariable String opinion){
        Boolean flag=companyMapper.Opinion(id, opinion);
        if(!flag){
            responseData.setCode(400);
            responseData.setMsg("错了");
            responseData.setError("错");
        }
        else{
            responseData.setCode(200);
            responseData.setMsg("对了");
            responseData.setError("无");
        }
        return responseData;
    }//审核意见
    @GetMapping("/verify/{id}")
    public ResponseData verify(@PathVariable Long id){
        Boolean flag=companyMapper.verify(id);
        if(!flag){
            responseData.setCode(400);
            responseData.setMsg("错了");
            responseData.setError("错");
        }
        else{
            responseData.setCode(200);
            responseData.setMsg("对了");
            responseData.setError("无");
        }
        return responseData;
    }//确认按钮
    @GetMapping("/reject/{id}")
    public ResponseData reject(@PathVariable Long id){

        Boolean flag=companyMapper.verify(id);
        if(!flag){
            responseData.setCode(400);
            responseData.setMsg("错了");
            responseData.setError("错");
        }
        else{
            responseData.setCode(200);
            responseData.setMsg("对了");
            responseData.setError("无");
        }
        return responseData;
    }//未通过按钮
    @GetMapping("/download/{id}/{name}")//legal_id_file
    public ResponseData download(@PathVariable Long id,@PathVariable String name) throws IOException {//文件下载，不采用
         String down=companyMapper.download(id,name);
        fileService.download(down,name);
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
