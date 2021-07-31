package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础用户实体
 *
 * @Author jiyec
 * @Date 2021/7/26 21:42
 * @Version 1.0
 **/
@Data

@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "ct_users")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long    id;                     // 用户ID
    private String  login;                  // 用户登录名
    private String  pass;                   // 用户密码
    private String  nick;                   // 用户昵称
    private String  email;                  // 用户邮箱
    private Date    registered;             // 用户注册时间
    private Integer status;                 // 激活状态（暂时保留，默认2）[1.未激活 2.激活]
    @TableField(exist = false)
    private Integer role;                   // 用户角色（注册用）
    @TableField(exist = false)
    private FinanceProperty financeInfo;    // 用户财务信息（注册用）
    @TableField(exist = false)
    private CompanyInformation comInfo;     // 企业信息（注册用）
}
