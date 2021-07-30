package cn.coal.trading.services;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.TradeUser;

/**
 * @Author jiyec
 * @Date 2021/7/31 6:39
 * @Version 1.0
 **/
public interface RegisterService {
    /**
     * @Author Sorakado
     * @ReWrite jiyeme
     * @Date 2021/7/29 15:04
     * @Version 1.0
     **/
    ResponseData register(TradeUser user);
}
