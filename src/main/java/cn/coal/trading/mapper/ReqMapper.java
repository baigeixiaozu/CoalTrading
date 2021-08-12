package cn.coal.trading.mapper;

import cn.coal.trading.bean.Request;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:16
 * @Version 1.0
 **/
public interface ReqMapper extends BaseMapper<Request> {
    @Select("SELECT * FROM ct_request WHERE id=(SELECT req_id FROM ct_zp WHERE user_id=#{userId} AND id=#{id})")
    Request selectOneByZPId(long userId, long id);
}
