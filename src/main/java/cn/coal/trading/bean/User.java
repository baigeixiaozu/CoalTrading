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
    private Long    id;
    private String  login;
    private String  pass;
    private String  nick;
    private String  email;
    private Date    registered;
    private Integer status;
    @TableField(exist = false)
    private Integer role;
    @TableField(exist = false)
    private FinanceProperty financeInfo;
    @TableField(exist = false)
    private CompanyInformation comInfo;
}
