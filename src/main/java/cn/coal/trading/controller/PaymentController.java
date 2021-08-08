package cn.coal.trading.controller;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.PaymentService;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.sun.xml.internal.ws.client.ResponseContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Resource
    PaymentService paymentService;

    //展示用户财产信息
    @GetMapping("show")
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

    @PostMapping("number")
    public ResponseData SecurityPayment(@RequestBody double number){
        try{
            TokenProfile profile= ProfileHolder.getProfile();
            Long id=Long.parseLong(profile.getId());

            if(paymentService.setDeposit(id,number)){

            }
            return new ResponseData(){{
                setCode(200);
                setMsg("success");
            }};
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