package cn.coal.trading.services;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.RegisteredUser;
import cn.coal.trading.bean.ResponseData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * @Author Sorakado
 * @Date 2021/7/28 22:37
 * @Version 1.0
 **/
public interface CompanyService {
   ResponseData complete(CompanyInformation companyInformation);
}
