package cn.coal.trading.services.impl;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.Company_Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Company_InformationImpl implements Company_Information {
    @Autowired
    public CompanyMapper companyMapper;
    @Override
    public CompanyInformation getCompanyInfoById(Long userId) {
        CompanyInformation companyInformation= (CompanyInformation) companyMapper.selectById(userId);
        return companyInformation;
    }
}
