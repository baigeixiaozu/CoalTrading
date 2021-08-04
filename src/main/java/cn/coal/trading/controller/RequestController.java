package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.Request;
import cn.coal.trading.bean.reqdata.BuyPubData;
import cn.coal.trading.bean.reqdata.SalePubData;
import cn.coal.trading.services.RequestService;
import com.baomidou.shaun.core.annotation.HasRole;
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

    @GetMapping("/list")
    public ResponseData getList(@RequestParam(defaultValue = "", required = false) Integer userId, @RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        Map<String, Object> list = requestService.listAvailable(userId, page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(list);
        return responseData;
    }

    /**
     * 需求发布，草稿
     *
     */
    @PostMapping("/publish")
    @HasRole({"USER_SALE", "USER_BUY"})
    public ResponseData publish(@RequestBody Request request){
        ResponseData responseData = new ResponseData();

        // 挂牌参数校验
        if ( request.getId()!=null
                || request.getUserId()!=null
                || request.getZpId()!=null
                || request.getZpDetail()!=null
                || request.getType() != null){
            responseData.setError("参数非法");
            responseData.setCode(21201);
            responseData.setMsg("fail");
            return responseData;
        }
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
    public ResponseData edit(@RequestBody Request request){
        ResponseData responseData = new ResponseData();
        // 挂牌参数校验
        if ( request.getId()==null
                || request.getUserId()!=null
                || request.getZpId()!=null
                || request.getZpDetail()!=null
                || request.getType() != null){
            responseData.setError("参数非法");
            responseData.setCode(21201);
            responseData.setMsg("fail");
            return responseData;
        }
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
}
