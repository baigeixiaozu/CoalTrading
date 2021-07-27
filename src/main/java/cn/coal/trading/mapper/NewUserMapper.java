package cn.coal.trading.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:36
 * @Version 1.0
 **/
public interface NewUserMapper{
    @Select("SELECT * FROM ct_userrole")
    Map<String, Object> getRoleList();
}
