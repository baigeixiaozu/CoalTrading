package cn.coal.trading.controller;

import cn.coal.trading.bean.AuditOpinion;
import cn.coal.trading.bean.Request;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.reqdata.BuyPubData;
import cn.coal.trading.bean.reqdata.SalePubData;
import cn.coal.trading.services.RequestService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:18
 * @Version 1.0
 **/
@Api(tags = "挂牌模块")
@RestController
@RequestMapping("/request")
@ApiSupport(author = "jiyec")
public class RequestController {

    @Resource
    RequestService requestService;
    @Resource


    @Value("${ct.uploadFile.location}")
    private String BASE_STORE_PATH;

    /**
     * 首页获取可用的需求
     *
     * @param userId    null全部|ID指定用户
     * @param page      页码
     * @param limit     每页数量
     */
    @ApiOperation(value = "getRequestList",notes = "获取可用的需求列表")
    @GetMapping("/all/list")
    public ResponseData getList(@RequestParam(defaultValue = "", required = false) Long userId, @RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        Map<String, Object> list = requestService.listAvailable(userId, page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(list);
        return responseData;
    }

    /**
     * 我的需求
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "getMyList",notes = "获取已发布的需求列表")
    @GetMapping("/my/list")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData myList(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        TokenProfile profile = ProfileHolder.getProfile();
        String userId = profile.getId();
        Map<String, Object> myList = requestService.myList(Long.parseLong(userId), page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(myList);
        return responseData;
    }
    @ApiOperation(value = "getDetail",notes = "获取某已发布需求的详细信息")
    @GetMapping("/my/detail/{id}")
    public ResponseData myDetail(@PathVariable long id){
        ResponseData response = new ResponseData();
        TokenProfile profile = ProfileHolder.getProfile();
        String userId = profile.getId();

        Request request = requestService.myDetail(Long.parseLong(userId), id);
        if(request == null){
            response.setCode(201);
            response.setMsg("fail");
            response.setError("挂牌信息不存在！");
        }else{
            response.setCode(200);
            response.setMsg("success");
            response.setData(request);
        }
        return response;
    }

    /**
     * 需求发布（待审核），草稿
     *
     */
    @ApiOperation(value = "publishRequest",notes = "发布新需求")
    @PostMapping("/publish")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData publish(@RequestBody Request req){
        ResponseData responseData = new ResponseData();

        // 挂牌参数处理（为保证安全，仅提取需要的数据）
        Request request = new Request(){{
            setStatus(req.getPublish()?2:1);     // 1. 草稿  2. 发布待审核
            setDetail(req.getDetail());
        }};
        TokenProfile profile = ProfileHolder.getProfile();
        Set<String> roles = profile.getRoles();
        // 根据用户角色设定需求类型，检查提交的数据类型是否正确
        if(roles.contains("USER_SALE") && request.getDetail() instanceof SalePubData){
            // 供应商, 卖出
            request.setType(1);
        }else if(roles.contains("USER_BUY") && request.getDetail() instanceof BuyPubData){
            // 采购商，买入
            request.setType(2);
        }else{
            responseData.setCode(201);
            responseData.setMsg("fail");
            responseData.setError("提交的数据与用户类型不符");
            return responseData;
        }
        request.setUserId(Long.parseLong(profile.getId()));

        long id = requestService.newReq(request);
        if(id == 0){
            responseData.setCode(201);
            responseData.setMsg("fail");
        }else{
            responseData.setCode(200);
            responseData.setMsg("success");
            responseData.setData(new HashMap<String, Long>(){{
                // 需求ID
                put("reqId", id);
            }});
        }
        return responseData;
    }

    /**
     * 需求编辑
     */
    @ApiOperation(value = "editRequest",notes = "编辑需求")
    @PostMapping("/edit")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData edit(@RequestBody Request req){
        ResponseData responseData = new ResponseData();

        // 挂牌参数处理（为保证安全，仅提取需要的数据）
        Request request = new Request(){{
            setId(req.getId());
            setStatus(req.getPublish()?2:1);     // 1. 草稿  2. 发布待审核
            setDetail(req.getDetail());
        }};

        TokenProfile profile = ProfileHolder.getProfile();
        Set<String> roles = profile.getRoles();
        // 根据用户角色检查提交的数据类型是否正确
        if(!((roles.contains("USER_SALE") && request.getDetail() instanceof SalePubData)
                || (roles.contains("USER_BUY") && request.getDetail() instanceof BuyPubData))){
            responseData.setCode(201);
            responseData.setMsg("fail");
            responseData.setError("提交的数据与用户类型不符");
            return responseData;
        }
        int edit = requestService.edit(request);
        if(edit == 1){
            responseData.setCode(200);
            responseData.setMsg("success");
        }else{
            responseData.setCode(31201);
            responseData.setMsg("fail");
        }
        return responseData;
    }

    /**
     * 待审核需求列表
     */
    @ApiOperation(value = "auditPending",notes = "获取待审核的需求列表")
    @GetMapping("/audit/pending")
    @HasRole("TRADE_AUDITOR")
    public ResponseData auditPending(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData response = new ResponseData();
        Map<String, Object> auditList = requestService.auditPending(page, limit);
        response.setCode(200);
        response.setMsg("success");
        response.setData(auditList);
        return response;
    }

    @ApiOperation(value = "auditDetail",notes = "获取待审核需求列表中的某一详细需求信息")
    @GetMapping("/audit/detail/{request_id}")
    @HasRole("TRADE_AUDITOR")
    public ResponseData auditDetail(@PathVariable long request_id){
        ResponseData response = new ResponseData();

        Request request = requestService.auditDetail(request_id);
        response.setData(request);
        response.setMsg("success");
        response.setCode(200);
        return response;
    }

    /**
     * 审核操作
     */
    @ApiOperation(value = "auditDo",notes = "审核需求")
    @PostMapping("/audit/do/{req_id}")
    @HasRole("TRADE_AUDITOR")
    public ResponseData auditDo(@RequestBody Map<String, Object> request, @PathVariable long req_id) {
        ResponseData response = new ResponseData();
        String opinion = (String) request.get("opinion");
        boolean accept = (boolean) request.get("accept");
        Request req = new Request(){{
            setId(req_id);
            setStatus(accept?3:7);
            setOpinion(opinion);
        }};
        boolean b = requestService.doAudit(req);
        response.setCode(b?200:151201);
        response.setMsg(b?"success":"fail");
        return response;
    }

    @ApiOperation(value = "contractUpload",notes = "交易合同上传")
    @PostMapping("/contract/upload/{req_id}")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData contractUpload(@RequestParam MultipartFile contract, @PathVariable("req_id") long requestId){
        ResponseData response = new ResponseData();
        if(contract.isEmpty()){
            response.setCode(201);
            response.setMsg("fail");
            response.setError("文件上传失败");
            return response;
        }
        TokenProfile profile = ProfileHolder.getProfile();
        String id = profile.getId();

        String uploadName = contract.getOriginalFilename();
        String suffix = StringUtils.substringAfter(uploadName, ".");

        String dir = String.format("/request/contract/%d", requestId);
        File file = new File(BASE_STORE_PATH + dir);
        if(!file.isFile() && !file.exists()){
            file.mkdirs();
        }
        String exactPath = dir + String.format("/contract_%s.%s", id, suffix);

        // if(true){
        //
        //     response.setData(new HashMap<String, Object>(){{
        //         put("reqId", requestId);
        //         put("name", contract.getName());
        //         put("originalName", contract.getOriginalFilename());
        //         put("suffix", exactPath);
        //     }});
        //     return response;
        // }
        try {
            contract.transferTo(new File(BASE_STORE_PATH + exactPath));
        } catch (IOException e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMsg("fail");
            response.setError("文件转存失败，请重试");
            return response;
        }
        boolean b = requestService.updateContract(requestId, Long.parseLong(id), exactPath);
        if(!b){
            // 数据库更新失败
            response.setCode(403);
            response.setMsg("fail");
            response.setError("数据更新失败。");
            return response;
        }
        response.setCode(200);
        response.setMsg("success");
        response.setData(new HashMap<String, Object>(){{
            put("path", exactPath);
        }});
        return response;
    }
    @ApiOperation(value = "getContractFile",notes = "获取上传的交易合同")
    @GetMapping("/contract/file/{req_id}")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData contractFile(HttpServletResponse response, @PathVariable("req_id") long requestId, @RequestParam String path){

        ResponseData responseData = new ResponseData();

        TokenProfile profile = ProfileHolder.getProfile();
        String id = profile.getId();

        // 文件路径前缀，唯一
        String exactPath = String.format("/request/contract/%d/contract_%s.", requestId, id);

        // 简单的合法性验证
        if(path == null || !path.startsWith(exactPath)){
            // 参数有误
            responseData.setCode(201);
            responseData.setMsg("fail");
            responseData.setError("参数有误！");
            return responseData;
        }
        ServletOutputStream outputStream = null;
        BufferedInputStream bis = null;
        try {
            File file = new File(BASE_STORE_PATH + path);
            // 文件存在性判定
            if(!file.exists()){
                responseData.setCode(404);
                responseData.setMsg("fail");
                responseData.setError("文件丢失！");
                return responseData;
            }

            // 准备
            outputStream = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024];

            // 开始
            int read = bis.read(buff);
            while (read != -1){
                outputStream.write(buff, 0, read);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseData.setCode(500);
            responseData.setMsg("fail");
            responseData.setError(e.getMessage());
            return responseData;
        }finally {
            // 关闭流
            if(bis != null){
                try {
                    bis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        responseData.setCode(200);
        responseData.setMsg("success");
        return responseData;
    }


}
