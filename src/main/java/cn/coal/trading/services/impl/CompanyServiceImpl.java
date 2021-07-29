package cn.coal.trading.services.impl;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.mapper.UserMapper;
import cn.coal.trading.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public CompanyMapper companyMapper;
    @Override
    public ResponseData complete(CompanyInformation companyInformation) {

        ResponseData response=new ResponseData();






        return null;
    }
}
