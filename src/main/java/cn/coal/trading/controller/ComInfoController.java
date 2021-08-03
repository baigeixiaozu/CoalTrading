package cn.coal.trading.controller;
import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.mapper.CompanyMapper;
import org.springframework.web.bind.annotation.*;

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
    public List<CompanyInformation> BasicInfo(@PathVariable Long id){
        return companyMapper.getInfo(id);
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
}
