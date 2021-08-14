package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_finance_store")
public class FinanceStore {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;                // 用户ID
    private Date date;                  // 变动时间
    private Double quantity;            // 金额数量
    private String cert;                // 交易凭证（文件）
    private Integer status;                 // 1. 待审核 2. 驳回（审核不通过） 3. 审核通过
}
