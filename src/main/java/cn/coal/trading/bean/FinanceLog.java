package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author jiyec
 * @Date 2021/8/1 18:23
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_finance_log")
public class FinanceLog {
    private Long userId;                // 用户ID
    private Date date;                  // 变动时间
    private Integer logType;            // 变动操作[ 1. 预存（增加）2. 缴纳给平台（冻结）  3. 平台扣除指定额度的冻结款项（减少）]
    private Double quantity;            // 金额数量
    private String cert;                // 交易凭证（文件）
}
