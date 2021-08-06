package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.Request;
import cn.coal.trading.bean.reqdata.BuyPubData;
import cn.coal.trading.bean.reqdata.SalePubData;
import cn.coal.trading.services.RequestService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/request")
public class RequestController {

    @Resource
    RequestService requestService;

    /**
     * 首页获取可用的需求
     *
     * @param userId    null全部|ID指定用户
     * @param page      页码
     * @param limit     每页数量
     */
    @GetMapping("/list")
    public ResponseData getList(@RequestParam(defaultValue = "", required = false) Long userId, @RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        Map<String, Object> list = requestService.listAvailable(userId, page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(list);
        return responseData;
    }

    /**
     * 需求发布（待审核），草稿
     *
     */
    @PostMapping("/publish")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData publish(@RequestBody Request req){
        ResponseData responseData = new ResponseData();

        // 挂牌参数处理（为保证安全，仅提取需要的数据）
        Request request = new Request(){{
            setStatus(req.isPublish()?2:1);     // 1. 草稿  2. 发布待审核
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
    @PostMapping("/edit")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData edit(@RequestBody Request req){
        ResponseData responseData = new ResponseData();

        // 挂牌参数处理（为保证安全，仅提取需要的数据）
        Request request = new Request(){{
            setId(req.getId());
            setStatus(req.isPublish()?2:1);     // 1. 草稿  2. 发布待审核
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
     * 我的需求
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/my")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData my(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        TokenProfile profile = ProfileHolder.getProfile();
        String userId = profile.getId();
        Map<String, Object> myList = requestService.myList(Long.parseLong(userId), page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(myList);
        return responseData;
    }

    /**
     * 待审核需求列表
     */
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


    /**
     * @author Sorakado
     * @time 2021/8/6/ 23:20
     * @version 1.0
     * 获取指定的详细需求
     */
    @PostMapping("/detail")
    @HasRole(value = {"USER_SALE", "USER_BUY"},logical = Logical.ANY)
    public ResponseData getDetail(@RequestParam int request_id){
        ResponseData response = new ResponseData();

        Request reqDetails = requestService.getReqDetails(request_id);
        if(reqDetails!=null){
            response.setData(reqDetails);
            response.setCode(200);
            response.setMsg("资源操作成功");
            response.setError("无");
        }else
        {
            response.setData(null);
            response.setCode(404);
            response.setMsg("不存在该订单的详细信息！");
            response.setError("资源，服务未找到");
        }
        return response;
    }
    /**
     * @author Sorakado
     * @time 2021/8/6/ 23:20
     * @version 1.0
     * 摘牌功能
     */
    @PostMapping("/delist")
    @HasRole(value = {"USER_SALE", "USER_BUY"},logical = Logical.ANY)
    public ResponseData delistRequest(@RequestParam int requestId){
        TokenProfile profile=ProfileHolder.getProfile();


        ResponseData result = requestService.delist(Long.parseLong(profile.getId()), requestId);
        return result;
    }

}
