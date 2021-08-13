package cn.coal.trading.controller;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.PaymentService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Heming233
 * date:2021/8/5
 * version:v1.1
 * <p>
 * update:2021/8/7
 * version:v1.2
 * <p>
 * update:2021/8/8
 * version:v1.3
 */

@Api(tags = "保证金模块")
@ApiResponses({@ApiResponse(code = 200, message = "操作成功", response = ResponseData.class),
        @ApiResponse(code = 400, message = "参数列表错误", response = ResponseData.class),
        @ApiResponse(code = 401, message = "未授权", response = ResponseData.class),
        @ApiResponse(code = 403, message = "授权受限，授权过期", response = ResponseData.class),
        @ApiResponse(code = 404, message = "资源，服务未找到", response = ResponseData.class),
        @ApiResponse(code = 409, message = "资源冲突，或者资源被锁定", response = ResponseData.class),
        @ApiResponse(code = 429, message = "请求过多被限制", response = ResponseData.class),
        @ApiResponse(code = 500, message = "系统内部错误", response = ResponseData.class),
        @ApiResponse(code = 501, message = "接口未实现", response = ResponseData.class)})
@RestController
@RequestMapping("/payment")
@ApiSupport(author = "Heming233")
public class PaymentController {
    @Resource
    PaymentService paymentService;

    //展示用户财产信息
    @ApiOperation(value = "showFinanceInfo", notes = "展示用户财务信息")
    @GetMapping("/show")
    @HasRole(value = "USER_MONEY", logical = Logical.ANY)
    public ResponseData showInformation(
            @RequestParam(required = false) Long gpid,
            @RequestParam(required = false) Long zpid) {
        try {
            TokenProfile profile = ProfileHolder.getProfile();
            Long userId = Long.parseLong(profile.getId());
            String type;
            long id;
            if (gpid != null) {
                type = "gp";
                id = gpid;
            } else if (zpid != null) {
                type = "zp";
                id = zpid;
            } else {
                return new ResponseData() {{
                    setCode(201);
                    setMsg("fail");
                    setError("参数异常");
                }};
            }
            Map<String, Object> map = paymentService.getFinanceById(userId, id, type);
            return new ResponseData() {{
                setCode(200);
                setMsg("success");
                setData(map);
            }};
        } catch (
                Exception e) {
            return new ResponseData() {{
                setCode(204);
                setMsg("error");
                setError("no user caught");
            }};
        }

    }


    //输入需求量，计算出保证金并缴纳
    @ApiOperation(value = "securityPayment", notes = "缴纳保证金")
    @PostMapping("/pay/{type}/{pid}")
    @HasRole(value = "USER_MONEY")
    public ResponseData SecurityPayment(@RequestBody Map<String, Object> data, @PathVariable String pid, @PathVariable TradeType type) {
        try {
            Object margin = data.get("margin");
            if(margin == null){
                return new ResponseData() {{
                    setCode(201);
                    setMsg("fail");
                    setError("参数错误");
                }};
            }
            if (paymentService.setDeposit(type.name(), pid, Double.parseDouble(margin+""))) {
                return new ResponseData() {{
                    setCode(200);
                    setMsg("success");
                }};
            } else {
                return new ResponseData() {{
                    setCode(205);
                    setMsg("error");
                    setError("支付失败");
                }};
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData() {{
                setCode(204);
                setMsg("error");
                setError("set deposit failed");
            }};
        }

    }
}

enum TradeType{
    zp,
    gp
}