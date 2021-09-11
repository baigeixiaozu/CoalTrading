package cn.coal.trading.controller;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.ComInfo;
import cn.coal.trading.services.impl.UserFileServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.shaun.core.annotation.HasRole;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@HasRole("USER_REG_AUDITOR")
@RestController
@RequestMapping("/info")
@ApiResponses({@ApiResponse(code = 200,message = "操作成功",response = ResponseData.class),
            @ApiResponse(code = 400,message = "参数列表错误",response = ResponseData.class),
        @ApiResponse(code = 401,message = "未授权",response = ResponseData.class),
        @ApiResponse(code = 403,message = "授权受限，授权过期",response = ResponseData.class),
        @ApiResponse(code = 404,message = "资源，服务未找到",response = ResponseData.class),
        @ApiResponse(code = 409,message = "资源冲突，或者资源被锁定",response = ResponseData.class),
        @ApiResponse(code = 429,message = "请求过多被限制",response = ResponseData.class),
        @ApiResponse(code = 500,message = "系统内部错误",response = ResponseData.class),
        @ApiResponse(code = 501,message = "接口未实现",response = ResponseData.class)})
@Api(tags = "企业信息模块")
@ApiSupport(author = "songyan.bai")
public class ComInfoController {
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    UserFileServiceImpl fileService;
    // 不能把响应体写在此处
    @Resource
    ComInfo comInfo;

    @ApiOperation(value = "basicInfo", notes = "显示待审核信息")
    @GetMapping("/{id}")//显示信息
    public ResponseData basicInfo(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        List<CompanyInformation> companyInformation = companyMapper.getInfo(id);
        responseData.setData(companyInformation);
        if (companyInformation == null) {
            responseData.setCode(400);
            responseData.setMsg("error");
            responseData.setError("查无此人");
        } else {
            responseData.setCode(200);
            responseData.setMsg("success");
        }
        return responseData;
    }

    @ApiOperation(value = "opinion", notes = "提交审核意见")
    @PostMapping("/{id}/{opinion}")//提交审核意见
    public ResponseData opinion(@PathVariable Long id, @PathVariable String opinion) {
        Boolean flag = companyMapper.Opinion(id, opinion);
        ResponseData responseData = new ResponseData();
        if (!flag) {
            responseData.setCode(400);
            responseData.setMsg("error");
            responseData.setError("未提交成功");
        } else {
            responseData.setCode(200);
            responseData.setMsg("success");
        }
        return responseData;
    }

    //审核意见
    @ApiOperation(value = "verify", notes = "确认按钮")
    @PostMapping ("/verify/{id}")
    public ResponseData verify(@PathVariable Long id) {
        Boolean flag = companyMapper.verify(id);
        ResponseData responseData = new ResponseData();
        if (!flag) {
            responseData.setCode(400);
            responseData.setMsg("error");
            responseData.setError("未确认成功");
        } else {
            responseData.setCode(200);
            responseData.setMsg("sucess");
        }
        return responseData;
    }

    //确认按钮
    @ApiOperation(value = "reject", notes = "未通过按钮")
    @PostMapping("/reject/{id}")
    public ResponseData reject(@PathVariable Long id) {

        Boolean flag = companyMapper.verify(id);
        ResponseData responseData = new ResponseData();
        if (!flag) {
            responseData.setCode(400);
            responseData.setMsg("error");
            responseData.setError("未通过成功");
        } else {
            responseData.setCode(200);
            responseData.setMsg("success");
        }
        return responseData;
    }//未通过按钮

    @ApiOperation(value = "download", notes = "下载图片or显示")
    @RequestMapping("/download")
    public Map<String,String> download(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException, UnsupportedEncodingException {
        Map<String,String> map = new HashMap<>();
        String rootPath = "//var/lib/data";//存储文件的目录
        String FullPath = rootPath + fileName;//文件的位置
        File packetFile = new File(FullPath);
        String fn = packetFile.getName(); //下载的文件名
        System.out.println("filename:"+fn);
        File file = new File(FullPath);
        // 如果文件名存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
            } catch (Exception e) {
                System.out.println("Download the song failed!");
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        } else {//对应文件不存在
            map.put("result","failed");
            return map;
        }

    }


    @ApiOperation(value = "showList", notes = "获取审核名单")
    @GetMapping("/list")
    public ResponseData showNews(@RequestParam(value = "size", defaultValue = "20") int size, @RequestParam(value = "current", defaultValue = "1") int current) {
        try {
            Page<CompanyInformation> companyInformationPage = comInfo.getAuditingList(current, size);
            return new ResponseData() {{
                setCode(200);
                setData(companyInformationPage);
                setMsg("success");
            }};
        } catch (Exception e) {
            return new ResponseData() {{
                setCode(204);
                setMsg("error");
                setError("no news caught");
            }};
        }
    }
}

