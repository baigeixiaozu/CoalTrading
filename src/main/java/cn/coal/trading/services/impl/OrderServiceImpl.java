package cn.coal.trading.services.impl;

import cn.coal.trading.bean.Order;
import cn.coal.trading.mapper.OrderMapper;
import cn.coal.trading.services.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/8/2 18:08
 * @Version 1.0
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;

    @Override
    public Map<String, Object> list(long userId, int page, int limit) {
        Page<Order> orders = orderMapper.selectPage(new Page<>(page, limit), new QueryWrapper<Order>() {{
            eq("gp_userid", userId).or().eq("zp_userid", userId);
        }});
        Map<String, Object> result = new HashMap<String, Object>() {{
            put("rows", orders.getRecords());
            put("current", orders.getCurrent());
            put("total", orders.getTotal());
            put("pages", orders.getPages());
        }};
        return result;
    }

    //获取订单详情
    @Override
    public Order getOrderDetail(Long id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();

        wrapper.eq("id", id);

        return orderMapper.selectOne(wrapper);
    }
}
