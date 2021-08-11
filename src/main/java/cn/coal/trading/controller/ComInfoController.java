package cn.coal.trading.controller;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.impl.FileServiceImpl;
import com.baomidou.shaun.core.annotation.HasRole;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@HasRole("USER_REG_AUDITOR")
@RestController
@RequestMapping("/info")
public class ComInfoController {
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    FileServiceImpl fileService;
    // 不能把响应体写在此处

    @GetMapping("/{id}")
    public ResponseData BasicInfo(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        List<CompanyInformation> companyInformation = companyMapper.getInfo(id);
        responseData.setData(companyInformation);
        if (companyInformation == null) {
            responseData.setCode(400);
            responseData.setMsg("没这个人");
            responseData.setError("查无此人");
        } else {
            responseData.setCode(200);
            responseData.setMsg("对了");
            responseData.setError("无");
        }
        return responseData;
    }

    @GetMapping("/{id}/{opinion}")
    public void Opinion(@PathVariable Long id, @PathVariable String opinion) {
        companyMapper.Opinion(id, opinion);
    }

    @GetMapping("/verify/{id}")
    public void verify(@PathVariable Long id) {
        companyMapper.verify(id);
    }

    @GetMapping("/reject/{id}")
    public void reject(@PathVariable Long id) {
        companyMapper.verify(id);
    }

    @GetMapping("/download/{id}/{name}")//legal_id_file
    public ResponseData download(@PathVariable Long id, @PathVariable String name) throws IOException {
        ResponseData responseData = new ResponseData();
        String down = companyMapper.download(id, name);
        fileService.download(down, name);
        if (down == null) {
            responseData.setCode(400);
            responseData.setMsg("wu");
            responseData.setError("无链接");
        } else {
            responseData.setData(down);
            responseData.setCode(201);
            responseData.setError("无");
            responseData.setMsg("wu");
        }
        return responseData;
    }

}
