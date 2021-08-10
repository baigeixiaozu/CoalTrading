package cn.coal.trading.controller;

import cn.coal.trading.bean.AuditOpinion;
import cn.coal.trading.bean.Request;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.DelistService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
@Api(tags="摘牌模块")
@RestController
@RequestMapping("/delist")
public class DelistController {
    @Resource
    DelistService delistService;
    /**
     * @author Sorakado
     * @time 2021/8/6/ 23:20
     * @version 1.0
     * 获取指定的详细需求
     */
    @PostMapping("/detailRequest")
    @HasRole(value = {"USER_SALE", "USER_BUY"},logical = Logical.ANY)
    public ResponseData getDetail(@RequestParam int request_id){
        ResponseData response = new ResponseData();

        Request reqDetails = delistService.getReqDetails(request_id);
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
    public ResponseData delistRequest(@RequestParam int request_id){
        TokenProfile profile= ProfileHolder.getProfile();


        ResponseData result =delistService.delist(Long.parseLong(profile.getId()), request_id);
        return result;
    }

    /**
     * 获取所有摘牌信息
     * @param page      页码
     * @param limit     每页数量
     */
    @GetMapping("/all/listDelist")
    @HasRole(value = {"TRADE_AUDITOR"})
    public ResponseData getDelistList( @RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        Map<String, Object> list = delistService.listDelist(page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(list);
        return responseData;
    }

    /**
     * @author Sorakado
     * @time 2021/8/7/ 23:20
     * @version 1.0
     * 获取指定的摘牌信息
     */
    @GetMapping("/detailInfo")
    @HasRole(value = {"TRADE_AUDITOR","USER_MONEY"},logical = Logical.ANY)
    public ResponseData getDetailInfo(@RequestParam long delistId){

        ResponseData result =delistService.getDetailInfo(delistId);
        return result;
    }
    /**
     * @author Sorakado
     * @time 2021/8/7/ 00:20
     * @version 1.0
     * 审核操作
     */
    @PostMapping("/examine")
    @HasRole(value = {"TRADE_AUDITOR"})
    public ResponseData examineTransaction(@RequestParam int zpId, @RequestBody AuditOpinion opinion){

        ResponseData result = delistService.examine(zpId,opinion);
        return result;
    }

    /**
     * 获取财务用户的公司所有摘牌信息
     * @param page      页码
     * @param limit     每页数量
     * @return ResponseData
     */
    @GetMapping("/financeDelist")
    @HasRole(value = {"USER_MONEY","USER_SALE","USER_BUY"},logical = Logical.ANY)
    public ResponseData getDelistListFinance( @RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        TokenProfile profile=ProfileHolder.getProfile();
        Map<String, Object> list = delistService.listDelistFinance(profile,page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(list);
        return responseData;
    }
}
