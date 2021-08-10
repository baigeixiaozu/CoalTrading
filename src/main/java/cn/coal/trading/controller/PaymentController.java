package cn.coal.trading.controller;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.PaymentService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Heming233
 * date:2021/8/5
 * version:v1.1
 *
 * update:2021/8/7
 * version:v1.2
 *
 * update:2021/8/8
 * version:v1.3
 */

@Api(tags = "保证金模块")
@RestController
@RequestMapping("/payment")
@ApiSupport(author = "Heming233")
public class PaymentController {
    @Resource
    PaymentService paymentService;

    //展示用户财产信息
    @ApiOperation(value = "showFinanceInfo",notes = "展示用户财务信息")
    @GetMapping("show")
    @HasRole(value = "USER_MONEY",logical = Logical.ANY)
    public ResponseData showInformation(){
        try{
            TokenProfile profile= ProfileHolder.getProfile();
            Long id=Long.parseLong(profile.getId());

            FinanceProperty user=paymentService.getFinanceById(id);
            return new ResponseData(){{
                setCode(200);
                setMsg("success");
                setData(user);
            }};
        }
        catch (Exception e){
                return new ResponseData(){{
                    setCode(204);
                    setMsg("error");
                    setError("no user caught");
                }};
        }
    }


    //输入需求量，计算出保证金并缴纳
    @ApiOperation(value = "securityPayment",notes = "缴纳保证金")
    @PostMapping("number/{requestId}")
    public ResponseData SecurityPayment(@RequestBody double number, @PathVariable String requestId){
        try{
            TokenProfile profile= ProfileHolder.getProfile();
            Long id=Long.parseLong(profile.getId());

            if(paymentService.setDeposit(id,requestId,number)){
                return new ResponseData(){{
                    setCode(200);
                    setMsg("success");
                }};
            }
            else{
                return new ResponseData(){{
                    setCode(403);
                    setMsg("error");
                    setError("This request has been occupied");
                }};
            }
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(204);
                setMsg("error");
                setError("set deposit failed");
            }};
        }

    }
}
