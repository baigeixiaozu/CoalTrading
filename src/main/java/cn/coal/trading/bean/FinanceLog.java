package cn.coal.trading.bean;

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
public class FinanceLog {
    private Integer userId;             // 用户ID
    private Date date;                  // 变动时间
    private Integer logType;               // 变动操作
    private Double fundQuantity;        // 金额数量
    private String cert;                // 交易凭证（文件）
}
