package cn.coal.trading.services.impl;

import cn.coal.trading.bean.FinanceStore;
import cn.coal.trading.mapper.FinanceSMapper;
import cn.coal.trading.services.FinanceStoreService;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author jiyec
 * @Date 2021/8/14 7:02
 * @Version 1.0
 **/
@Service
public class FinanceStoreServiceImpl implements FinanceStoreService {
    @Resource
    FinanceSMapper financeSMapper;

    @Override
    public boolean store(double quantity, String certPath) {
        TokenProfile profile = ProfileHolder.getProfile();
        String id = profile.getId();
        int insert = financeSMapper.insert(new FinanceStore() {{
            setUserId(Long.parseLong(id));
            setCert(certPath);
            setQuantity(quantity);
        }});
        return insert == 1;
    }
}
