package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author jiyec
 * @Date 2021/7/31 21:45
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_order")
public class Order {
    private Long id;            // 系统订单ID
    private String num;         // 订单号
    private Long reqId;         // 对应需求ID
    private Long userId;        // 用户ID
    private Date createdTime;   // 创建时间
    private Integer status;     // 订单状态[ 1.进行中| 2.超时| 3.完成| 4.取消]
}
