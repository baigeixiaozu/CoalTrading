package cn.coal.trading.services;

import cn.coal.trading.bean.FinanceLog;
import cn.coal.trading.bean.FinanceProperty;

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
    FinanceProperty getFinanceById(Long userId);

    //缴纳保证金
    Boolean setDeposit(Long userId,double number);
}
