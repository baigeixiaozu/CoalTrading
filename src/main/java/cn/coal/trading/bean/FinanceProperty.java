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
 * @Description 财务用户
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_finance")
public class FinanceProperty implements Serializable {
    private Long mainUserid;
    private Long financeUserid;
    private String comName;
    private String bankName;
    private String bankAcc;
    private String balance;
    private String freeze;
    private String aoPermitFile;
}
