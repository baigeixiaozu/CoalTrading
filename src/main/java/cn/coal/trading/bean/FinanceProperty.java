package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 财务表实体类
 *
 * @Author jiyec
 * @Date 2021/7/27 19:08
 * @Version 1.0
 * @Description 财务属性表
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_finance")
public class FinanceProperty implements Serializable {
    private Long mainUserid;        // 企业主用户
    private Long financeUserid;     // 企业财务用户
    private String comName;         // 汇款单位名称
    private String bankName;        // 银行名称
    private String bankAcc;         // 银行账号
    private Double balance;         // 账户余额
    private Double freeze;          // 冻结金额
    private String aoPermitFile;    // 开户许可证（文件路径）
    private Integer status;         // 信息状态[1.不可用| 2.审核阶段| 3.可用]
    private String auditOpinion;    // 审核意见
}
