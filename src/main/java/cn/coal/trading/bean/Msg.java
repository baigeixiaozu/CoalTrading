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
    private Long id;                // 消息ID
    private String title;           // 消息标题
    private String context;         // 消息内容
    private Integer msgType;        // 消息类型 1.系统消息
    private Date created;           // 消息发送时间
    private Long fromUserid;        // 消息来源ID[ NULL为系统，其它为特定用户]
    private String fromUsername;    // 消息来源名
    private Long toUserid;          // 消息接收者ID
    private String toUsername;      // 消息接收者名
    private Integer readStatus;     // 读取状态 [1.未读 2.已读]
}
