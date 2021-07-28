package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Author jiyec
 * @Date 2021/7/27 9:10
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "wm")
@TableName(value = "ct_website_message")
public class Msg implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    // 消息内容
    private String context;
    private Date created;
    private Long fromUserid;
    private String fromUsername;
    private Long toUserid;
    private String toUsername;
    private Integer readStatus;
}
