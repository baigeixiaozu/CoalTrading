package cn.coal.trading.services.impl;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.ComInfo;
import com.baomidou.shaun.core.annotation.HasRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComInfoImpl implements ComInfo {
    @Autowired
    public CompanyMapper companyMapper;
    @HasRole("NEWS_AUDITOR")
    @Override
    public CompanyInformation getCompanyInfoById(Long userId) {
            CompanyInformation companyInformation = (CompanyInformation) companyMapper.selectById(userId);
            return companyInformation;
    }

}
