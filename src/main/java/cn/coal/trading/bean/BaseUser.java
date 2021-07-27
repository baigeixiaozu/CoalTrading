package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @Author jiyec
 * @Date 2021/7/26 21:42
 * @Version 1.0
 **/
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "ct_users")
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "user")
public class BaseUser {
    @TableId
    private Long id;
    private String login;
    private String pass;
    private String nick;
    private String email;
    private Date registered;
    private Integer status;
    @TableField(exist = false)
    private Integer role;
}
