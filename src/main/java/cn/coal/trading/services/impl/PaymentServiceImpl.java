package cn.coal.trading.services.impl;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.Request;
import cn.coal.trading.mapper.FinanceMapper;
import cn.coal.trading.services.PaymentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Heming233
 * date:2021/8/6
 * version:v1.1
 *
 * update:2021/8/7
 * version:v1.2
 *
 * update:2021/8/8
 * version:v1.3
 */

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    FinanceMapper financeMapper;

    //获取用户财产信息
    @Override
    public FinanceProperty getFinanceById(Long userId){
        QueryWrapper<FinanceProperty> wrapper=new QueryWrapper<>();

        wrapper.eq("finance_userid",userId);

        return financeMapper.selectOne(wrapper);
    }

    //缴纳保证金
    @Override
    public Boolean setDeposit(Long userId,double number){
        QueryWrapper<FinanceProperty> financeWrapper=new QueryWrapper<>();
        QueryWrapper<Request> requestWrapper=new QueryWrapper<>();
    }

}
