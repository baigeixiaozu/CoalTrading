package cn.coal.trading.mapper;

import cn.coal.trading.bean.FinanceLog;
import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.FinanceStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Mapper
public interface DeFundsMapper extends BaseMapper {
    @Select("select * from ct_finance where main_userid=#{id}")
    List<FinanceProperty> getFinInfo(int id);

    @Insert("insert into ct_finance_store values(#{id},#{user_id},#{date},#{quantity},#{cert},#{status})")//更新数量以及其他辅助参数
    boolean TransInfo(FinanceStore financeStore);
    @Update("update ct_finance_store set cert =#{path} where user_id=#{id}")//上传文件
    boolean updateF(String path,Long id);
}
