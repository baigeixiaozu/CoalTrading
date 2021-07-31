package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户角色绑定表实体类
 *
 * @Author jiyec
 * @Date 2021/7/27 18:57
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ct_user_role_relationships")
public class UserRoleBinding implements Serializable {
    private Long userId;        // 用户ID
    private Integer roleId;     // 角色ID
}
