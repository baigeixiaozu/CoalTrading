package cn.coal.trading.controller;

import cn.coal.trading.bean.AuditOpinion;
import cn.coal.trading.bean.Delisting;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.DelistService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags="摘牌模块")
@RestController
@RequestMapping("/delist")
@ApiSupport(author = "Sorakdado")
public class DelistController {
    @Resource
    DelistService delistService;

    /**
     * 摘牌功能
     * @author Sorakado
     * @time 2021/8/6/ 23:20
     * @version 1.0
     */
    @ApiOperation(value = "delistRequest",notes = "用户摘牌功能")
    @PostMapping("/delist")
    @HasRole(value = {"USER_SALE", "USER_BUY"},logical = Logical.ANY)
    public ResponseData delistRequest(@RequestParam long request_id){
        TokenProfile profile= ProfileHolder.getProfile();

        Delisting delist = new Delisting();
        delist.setReqId(request_id);
        delist.setUserId(Long.parseLong(profile.getId()));
        delist.setStatus("1");
        delist.setType(profile.getRoles().contains("USER_SALE")?"2":"1");   // 卖方2  买方1
        ResponseData result =delistService.delist(delist);
        return result;
    }

    /**
     * 交易审核员获取所有摘牌列表
     * @param page      页码
     * @param limit     每页数量
     */
    @GetMapping("/all/listDelist")
    @HasRole(value = {"TRADE_AUDITOR"})
    @ApiOperation(value = "delistList",notes = "交易审核员获取所有摘牌列表")
    public ResponseData getDelistList( @RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "10", required = false) int limit){
        ResponseData responseData = new ResponseData();
        Map<String, Object> list = delistService.listDelist(page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(list);
        return responseData;
    }

    /**
     * 交易审核员获取指定的摘牌详细信息及挂牌信息
     * @author Sorakado
     * @time 2021/8/7/ 23:20
     * @version 1.0
     */
    @GetMapping("/detailInfo")
    @HasRole(value = {"TRADE_AUDITOR"})
    @ApiOperation(value = "getDetailInfo",notes = "交易审核员获取指定的摘牌详细信息及摘牌信息")
    public ResponseData getDetailInfo(@RequestParam long delistId){

        ResponseData result =delistService.getDetailInfoByZPId(delistId);
        return result;
    }

    /**
     * 公司账户获取指定的摘牌详细信息及对应的挂牌信息
     * @author Sorakado
     * @time 2021/8/11/ 20:24
     * @version 1.0
     */
    @GetMapping("/detailInfoForUser")
    @HasRole(value = {"USER_BUY","USER_SALE","USER_MONEY"})
    @ApiOperation(value = "getDetailInfoForUser",notes = "公司账户获取指定的摘牌详细信息及对应的挂牌信息")
    public ResponseData getDetailInfoForUser(@RequestParam long delistId){
        TokenProfile profile=ProfileHolder.getProfile();

        ResponseData result =delistService.getDetailInfoForUser(profile,delistId);
        return result;
    }
    @GetMapping("/detailInfoForUser2")
    @HasRole(value = {"USER_BUY","USER_SALE","USER_MONEY"})
    @ApiOperation(value = "getDetailInfoForUser2",notes = "公司账户获取指定的挂牌信息及对应的摘牌详细信息")
    public ResponseData getDetailInfoForUser2(@RequestParam long reqId){
        TokenProfile profile=ProfileHolder.getProfile();

        ResponseData result =delistService.getDetailInfoForUser2(profile, reqId);
        return result;
    }

    /**
     * 审核操作
     * @author Sorakado
     * @time 2021/8/7/ 00:20
     * @version 1.0
     */
    @PostMapping("/examine")
    @HasRole(value = {"TRADE_AUDITOR"})
    @ApiOperation(value = "examineTransactionr",notes = "交易审核操作")
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
    @GetMapping("/my/list")
    @HasRole(value = {"USER_MONEY","USER_SALE","USER_BUY"},logical = Logical.ANY)
    @ApiOperation(value = "getDelistListFinance",notes = "获取财务用户的公司所有摘牌信息")
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
