package cn.coal.trading.services.impl;

import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.News;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.services.ComInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.shaun.core.annotation.HasAuthorization;
import com.baomidou.shaun.core.annotation.HasPermission;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
//import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ComInfoImpl implements ComInfo {
    @Resource
    CompanyMapper companyMapper;

    @Override
    @HasRole(value = "USER_REG_AUDITOR",logical = Logical.ANY)
    public Page<CompanyInformation> getAuditingList(int current, int size) {
        Page<CompanyInformation> page=new Page<>(current,size);
        QueryWrapper<CompanyInformation> wrapper=new QueryWrapper<>();
        wrapper.eq("status","2");
        wrapper.select("user_id","com_name");
        return companyMapper.selectPage(page,wrapper);
    }
}
