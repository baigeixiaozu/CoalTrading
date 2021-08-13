package cn.coal.trading.services.impl;

import cn.coal.trading.bean.Delisting;
import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.Request;
import cn.coal.trading.mapper.DelistingMapper;
import cn.coal.trading.mapper.FinanceMapper;
import cn.coal.trading.mapper.ReqMapper;
import cn.coal.trading.services.PaymentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Heming233
 * date:2021/8/6
 * version:v1.1
 * <p>
 * update:2021/8/7
 * version:v1.2
 * <p>
 * update:2021/8/8
 * version:v1.3
 */

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    FinanceMapper financeMapper;

    @Resource
    ReqMapper reqMapper;
    @Resource
    DelistingMapper delistingMapper;

    //获取用户财产信息
    @Override
    public Map<String, Object> getFinanceById(Long userId, long id, String type) {
        QueryWrapper<FinanceProperty> wrapper = new QueryWrapper<>();

        wrapper.eq("finance_userid", userId);
        FinanceProperty financeProperty = financeMapper.selectOne(wrapper);
        Request request;
        if ("zp".equals(type)) {
            // 摘牌ID
            request = reqMapper.selectOneByZPId(financeProperty.getMainUserid(), id);
        } else {
            // 挂牌ID
            request = reqMapper.selectOne(new QueryWrapper<Request>() {{
                eq("user_id", financeProperty.getMainUserid());
                eq("id", id);
            }});
        }
        return new HashMap<String, Object>() {{
            put("financeInfo", financeProperty);
            put("requestInfo", request);
        }};
    }

    //缴纳保证金
    @Override
    public Boolean setDeposit(String type, String pid, double margin) {
        TokenProfile profile = ProfileHolder.getProfile();
        long fId = Long.parseLong(profile.getId());      // 财务ID
        long mId = financeMapper.getMIdByFId(fId);

        // 1.挂牌摘牌表设置金额
        int update = 0;
        if("zp".equals(type)){
            // 摘牌保证金
            update = delistingMapper.update(new Delisting() {{
                setDeposit(margin);
                setStatus("2");
            }}, new UpdateWrapper<Delisting>() {{
                eq("id", pid);
                eq("user_id", mId);          // 摘牌用户ID
            }});
        }else{
            // 挂牌保证金
            update = reqMapper.update(new Request(){{
                setDeposit(margin);
                setStatus("2");
            }}, new UpdateWrapper<Request>(){{
                eq("id", pid);
                eq("user_id", mId);
            }});
        }
        if(update != 1)return false;
        // 2.财务表资金变动计算
        //更新finance表
        update = financeMapper.update(new FinanceProperty(), new UpdateWrapper<FinanceProperty>() {{
            eq("finance_userid", fId);
            setSql("freeze = freeze+" + margin);    // 增加冻结金额
        }});
        return update == 1;
    }

}
