package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type= IdType.AUTO)//edit by Heming233
    private Long id;            // 系统订单ID
    private String num;         // 订单号
    private Long reqId;         // 对应需求ID
    private Long gpUserid;      // 挂牌方用户ID
    private Long zpUserid;      // 摘牌方用户ID
    private Date createdTime;   // 创建时间
    private Integer status;     // 订单状态[ 1.进行中| 2.超时| 3.完成| 4.取消]
}
