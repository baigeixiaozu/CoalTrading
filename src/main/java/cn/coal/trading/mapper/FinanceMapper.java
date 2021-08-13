package cn.coal.trading.mapper;

import cn.coal.trading.bean.FinanceProperty;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface FinanceMapper extends BaseMapper<FinanceProperty> {
    @Select("SELECT main_userid FROM ct_finance WHERE finance_userid=#{fid}")
    long getMIdByFId(long fid);
}
