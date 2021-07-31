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
 * 消息实体类
 *
 * @Author jiyec
 * @Date 2021/7/27 9:10
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "ct_website_message")
public class Msg implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    // 消息标题
    private String title;
    // 消息内容
    private String context;
    // 消息类型 1.系统消息
    private Integer msgType;
    private Date created;
    private Long fromUserid;
    private String fromUsername;
    private Long toUserid;
    private String toUsername;
    // 读取状态 [1.未读 2.已读]
    private Integer readStatus;
}
