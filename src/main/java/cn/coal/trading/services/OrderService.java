package cn.coal.trading.services;

import cn.coal.trading.bean.Order;

import java.util.Map;

/**
 * 订单服务
 *
 * @Author jiyec
 * @Date 2021/8/2 18:08
 * @Version 1.0
 **/
public interface OrderService {
    Map<String, Object> list(long userId, int page, int limit);

    Order getOrderDetail(Long id);
}
