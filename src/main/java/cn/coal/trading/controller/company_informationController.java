package cn.coal.trading.controller;

import cn.coal.trading.bean.CompanyInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.coal.trading.bean.*;
import cn.coal.trading.services.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/info")
public class company_informationController {
    @Autowired
    Company_Information company_information;
    @GetMapping("/{id}")
    public ResponseData getById(@PathVariable Long id){
        ResponseData responseData=new ResponseData();
        CompanyInformation companyInformation=company_information.getCompanyInfoById(id);
        if (companyInformation != null) {
            responseData.setCode(200);
            responseData.setMsg("success");
            responseData.setData(companyInformation);
        } else {
            responseData.setCode(201);
            responseData.setMsg("fail");
            responseData.setError("未查询到此公司");
        }
        return responseData;
    }
}
