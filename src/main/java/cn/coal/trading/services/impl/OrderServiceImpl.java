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
    public Map<String, Object> list(Order order, int page, int limit) {
        Page<Order> orders = orderMapper.selectPage(new Page<>(page, limit), new QueryWrapper<Order>() {{
            eq("user_id", order.getUserId());
        }});
        Map<String, Object> result = new HashMap<String, Object>() {{
            put("rows", orders.getRecords());
            put("current", orders.getCurrent());
            put("total", orders.getTotal());
            put("pages", orders.getPages());
        }};
        return result;
    }

    /**
     *
     * @Author Heming233
     * @Date 2021/8/12
     * @version v1.1
     */
    //生成订单
    @Override
    public Order addNewOrder(Long userId,Long requestId){
        //QueryWrapper<Order> wrapper=new QueryWrapper<>();

        Order order=new Order();
        order.setReqId(requestId);
        order.setUserId(userId);
        order.setStatus(1);//状态码“1”表示订单进行中
        order.setNum(String.valueOf((Math.random()*9+1)*100000));

/*        List<Order> list=orderMapper.selectList(wrapper);
        if( list.isEmpty()){
            order.setId(1L);
        }*/

        orderMapper.insert(order);
        return order;
    }

    //获取订单详情
    @Override
    public Order getOrderDetail(Long id){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();

        wrapper.eq("id",id);

        return orderMapper.selectOne(wrapper);
    }
}
