package cn.coal.trading.services;

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

public interface PaymentService {

    //获取用户财产信息
    Map<String,Object> getFinanceById(Long userId, long id, String type);

    //缴纳保证金
    Boolean setDeposit(String type, String requestId, double number);
}
