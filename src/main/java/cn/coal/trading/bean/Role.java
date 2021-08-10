package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色表实体类
 *
 * @Author jiyec
 * @Date 2021/7/27 14:31
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ct_userrole")
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;         // 角色ID
    private String name;        // 角色名
    private String mark;        // 角色标记
    private String type;        // 角色类型（标记）
}
