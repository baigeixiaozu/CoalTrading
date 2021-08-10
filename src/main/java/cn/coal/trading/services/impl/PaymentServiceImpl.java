package cn.coal.trading.services.impl;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.Request;
import cn.coal.trading.mapper.FinanceMapper;
import cn.coal.trading.mapper.ReqMapper;
import cn.coal.trading.services.PaymentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

    @Resource
    ReqMapper reqMapper;

    //获取用户财产信息
    @Override
    public FinanceProperty getFinanceById(Long userId){
        QueryWrapper<FinanceProperty> wrapper=new QueryWrapper<>();

        wrapper.eq("finance_userid",userId);

        return financeMapper.selectOne(wrapper);
    }

    //缴纳保证金
    @Override
    public Boolean setDeposit(Long userId,String requestId,double number){
        QueryWrapper<FinanceProperty> financeWrapper=new QueryWrapper<>();
        UpdateWrapper<FinanceProperty> financePropertyUpdateWrapper=new UpdateWrapper<>();
        QueryWrapper<Request> requestWrapper=new QueryWrapper<>();
        UpdateWrapper<Request> requestUpdateWrapper=new UpdateWrapper<>();

        requestWrapper.eq("id",requestId);
        Request request=reqMapper.selectOne(requestWrapper);
        //检测该需求是否已经被摘牌
        if(request.getStatus()==4){
            return false;
        }
        else{
            //更新需求表
            request.setStatus(4);
            request.setDeposit(number*100000);
            requestUpdateWrapper.eq("id",requestId);
            reqMapper.update(request,requestUpdateWrapper);

            //更新finance表
            financeWrapper.eq("finance_userid",userId);
            financePropertyUpdateWrapper.eq("finance_userid",userId);
            FinanceProperty financeProperty=financeMapper.selectOne(financeWrapper);
            financeProperty.setFreeze(financeProperty.getFreeze()+number*100000);
            financeProperty.setBalance(financeProperty.getBalance()-number*100000);
            financeMapper.update(financeProperty,financePropertyUpdateWrapper);

            return true;
        }
    }

}
