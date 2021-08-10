package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司信息表实体类
 *
 * @author Sorakado
 * @time 7.28 22:22
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_company")
public class CompanyInformation {
    @TableId(type = IdType.INPUT)
    private Long userId;                        // 企业主用户ID
    private String comName;                     // 企业名称
    private String comIntro;                    // 企业介绍
    private String legalName;                   // 法人代表姓名
    private String legalId;                     // 法人身份证号
    private String legalIdFile;                 // 法人身份证文件（路径）
    private String comAddr;                     // 企业注册地区
    private String comContact;                  // 企业联系电话
    private String comZip;                      // 企业邮政编码
    private String businessLicenseId;           // 企业营业执照号
    private String businessLicenseFile;       // 企业营业执照文件（路径）
    private String manageLicenseId;             // 经营许可证编号
    private String manageLicenseFile;         // 煤炭经营许可证（文件）[供应商]
    private String fax;                         // 传真
    private Double registeredCapital;           // 注册资金（万元）
    private String oibCode;                     // 组织机构代码
    private String oibCodeFile;                 // 组织机构代码证（文件）
    private String trCert;                      // 税务登记证代码
    private String trCertFile;                  // 税务登记证文件（路径）
    private String coalStoreSite;               // 煤炭存放地点[供应商]
    private Double coalQuantity;                // 煤炭数量[供应商]
    private String coalQuality;                 // 煤炭质量[供应商]
    private String coalTransport;               // 运输方式及保障能力[供应商]
    private Integer status;                     // 信息状态[1.不可用| 2.审核阶段| 3.可用]
    private String auditOpinion;                // 审核意见
    @TableField(exist = false)
    private FinanceProperty financeInfo;
}
