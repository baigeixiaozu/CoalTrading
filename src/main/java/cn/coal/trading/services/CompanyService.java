package cn.coal.trading.services;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.ResponseData;

/**
 * @Author Sorakado
 * @Date 2021/7/28 22:37
 * @Version 1.0
 **/
public interface CompanyService {
   ResponseData complete(CompanyInformation companyInformation);
}
