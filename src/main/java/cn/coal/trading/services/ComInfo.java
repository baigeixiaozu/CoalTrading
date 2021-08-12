package cn.coal.trading.services;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.News;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ComInfo {

    Page<CompanyInformation> getAuditingList(int current, int size) ;
}
