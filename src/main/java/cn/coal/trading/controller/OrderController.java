package cn.coal.trading.controller;

import cn.coal.trading.bean.Order;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.OrderService;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/31 21:37
 * @Version 1.0
 **/
@Api(tags = "订单模块")
@RestController
@RequestMapping("/order")
@HasRole(value = {"USER_SALE", "USER_BUY"}, logical = Logical.ANY)
@ApiSupport(author = "jiyec & Heming233")
@ApiResponses({@ApiResponse(code = 200,message = "操作成功",response = ResponseData.class),
        @ApiResponse(code = 400,message = "参数列表错误",response = ResponseData.class),
        @ApiResponse(code = 401,message = "未授权",response = ResponseData.class),
        @ApiResponse(code = 403,message = "授权受限，授权过期",response = ResponseData.class),
        @ApiResponse(code = 404,message = "资源，服务未找到",response = ResponseData.class),
        @ApiResponse(code = 409,message = "资源冲突，或者资源被锁定",response = ResponseData.class),
        @ApiResponse(code = 429,message = "请求过多被限制",response = ResponseData.class),
        @ApiResponse(code = 500,message = "系统内部错误",response = ResponseData.class),
        @ApiResponse(code = 501,message = "接口未实现",response = ResponseData.class)})
@Slf4j
public class OrderController {
    @Resource
    OrderService orderService;

    @GetMapping("/list")
    @ApiOperation(value = "getOrderList",notes = "获取订单列表")
    public ResponseData orderList(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "1", required = false) int limit){
        log.info("{}, {}", page, limit);
        TokenProfile profile = ProfileHolder.getProfile();
        String id = profile.getId();
        ResponseData responseData = new ResponseData();

        Map<String, Object> result = orderService.list(Long.parseLong(id), page, limit);
        responseData.setCode(200);
        responseData.setMsg("success");
        responseData.setData(result);

        return responseData;
    }

    // /**
    //  * Created by Heming233
    //  * Date:2021/8/9
    //  * version:v1.0
    //  */
    // @ApiOperation(value = "newOrder",notes = "创建新订单")
    // @PostMapping("/new")
    // public ResponseData newOrder(@PathVariable Long requestId){
    //     try{
    //         TokenProfile profile=ProfileHolder.getProfile();
    //         Long userId=Long.parseLong(profile.getId());
    //
    //         Order order=orderService.addNewOrder(userId,requestId);
    //         return new ResponseData(){{
    //             setCode(200);
    //             setMsg("success");
    //             setData(order);
    //         }};
    //     }
    //     catch(Exception e){
    //         return new ResponseData(){{
    //             setCode(403);
    //             setMsg("error");
    //             setError("add new order failed");
    //         }};
    //     }
    // }

    /**
     * Created by Heming233
     * Date:2021/8/12
     * version:v1.0
     */
    @ApiOperation(value="getOrderDetail",notes = "获取订单详情")
    @GetMapping("/detail/{orderId}")
    public ResponseData getDetail(@PathVariable String orderId){
        try{
            Order order =orderService.getOrderDetail(Long.parseLong(orderId));

            if(order!=null){
                return new ResponseData(){{
                    setCode(200);
                    setMsg("success");
                    setData(order);
                }};
            }
            else{
                return new ResponseData(){{
                    setCode(204);
                    setMsg("error");
                    setMsg("no such order");
                }};
            }
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(204);
                setMsg("error");
                setError("no order caught");
            }};
        }
    }
}
