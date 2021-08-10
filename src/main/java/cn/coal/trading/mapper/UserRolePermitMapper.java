package cn.coal.trading.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/30 21:01
 * @Version 1.0
 **/
public interface UserRolePermitMapper extends BaseMapper<Map<String, Object>> {
    @Select("SELECT b.mark AS role_mark, b.type AS role_type, d.name AS permission FROM ct_user_role_relationships a \n" +
            "LEFT JOIN ct_userrole b ON a.role_id=b.id\n" +
            "LEFT JOIN ct_role_permission_relationships c ON b.id=c.role_id\n" +
            "LEFT JOIN ct_permissions d ON c.permission_id=d.id " +
            "WHERE user_id=#{id}")
    List<Map<String, Object>> getRelation(Long id);
}
