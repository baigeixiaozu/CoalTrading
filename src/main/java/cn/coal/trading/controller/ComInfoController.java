package cn.coal.trading.controller;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.impl.FileServiceImpl;
import com.baomidou.shaun.core.annotation.HasRole;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@HasRole("USER_REG_AUDITOR")
@RestController
@RequestMapping("/info")

@Api(tags = "企业信息模块")
@ApiSupport(author = "songyan.bai")
public class ComInfoController {
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    FileServiceImpl fileService;
    // 不能把响应体写在此处

    @ApiOperation(value = "basicInfo",notes = "显示待审核信息")
    @GetMapping("/{id}")//显示信息
    public ResponseData basicInfo(@PathVariable Long id) {
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

    @ApiOperation(value = "opinion",notes = "提交审核意见")
    @GetMapping("/{id}/{opinion}")//提交审核意见
    public ResponseData opinion(@PathVariable Long id,@PathVariable String opinion){
        Boolean flag=companyMapper.Opinion(id, opinion);
        ResponseData responseData = new ResponseData();
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
    }
    //审核意见
    @ApiOperation(value = "verify",notes = "确认按钮")
    @GetMapping("/verify/{id}")
    public ResponseData verify(@PathVariable Long id){
        Boolean flag=companyMapper.verify(id);
        ResponseData responseData = new ResponseData();
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
    }
    //确认按钮
    @ApiOperation(value = "reject",notes = "未通过按钮")
    @GetMapping("/reject/{id}")
    public ResponseData reject(@PathVariable Long id){

        Boolean flag=companyMapper.verify(id);
        ResponseData responseData = new ResponseData();
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
    @ApiOperation(value = "download",notes = "下载图片or显示")
    @GetMapping("/download/{id}/{name}")//下载，不是图片显示，在这里不实现
    public ResponseData download(@PathVariable Long id,@PathVariable String name) throws IOException {//文件下载，不采用
        String down = companyMapper.download(id, name);
        System.out.println(down);
        fileService.download(down, name);
        ResponseData responseData = new ResponseData();
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
