package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author jiyec
 * @Date 2021/8/5 17:48
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_zp")
public class ZhaiPai {
    @TableId(type = IdType.AUTO)
    private Long id;            // 摘牌ID
    private Long reqId;         // 摘牌对应需求ID
    private Long userId;        // 摘牌用户ID
    private Double deposit;     // 摘牌保证金数额
    private Integer status;     // 摘牌状态
    private String opinion;     // 审核意见
}
