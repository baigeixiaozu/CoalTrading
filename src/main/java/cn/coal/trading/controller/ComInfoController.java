package cn.coal.trading.controller;
import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.impl.FileServiceImpl;
import com.baomidou.shaun.core.annotation.HasRole;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
//@HasRole("USER_REG_AUDITOR")
@HasRole("SUPER_ADMIN")
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
    public void Opinion(@PathVariable Long id,@PathVariable String opinion){
            companyMapper.Opinion(id, opinion);
    }
    @GetMapping("/verify/{id}")
    public void verify(@PathVariable Long id){
        companyMapper.verify(id);
    }
    @GetMapping("/reject/{id}")
    public void reject(@PathVariable Long id){
        companyMapper.verify(id);
    }
    @GetMapping("/download/{id}/{name}")//legal_id_file
    public ResponseData download(@PathVariable Long id,@PathVariable String name) throws IOException {//名字是想下载文件的键值
        String down=companyMapper.download(id,name);
//        String down=null;
        fileService.download(down,name);//下载文件并输入想要下载的文件的名字
//        fileService.download("/Users/songyanbai/Desktop/C57AD86F2C992341FC0E326DA2CCDEB4.png",name);
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
