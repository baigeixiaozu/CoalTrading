package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Sorakado
 * @time 7.28 22:22
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName("ct_company")
public class CompanyInformation {
    private int userId;
    private String comName;
    private String comIntro;
    private String legalName;
    private String legalId;
    private String comAddr;
    private String comContact;
    private String comZip;
    private String businessLicenseId;
    private String manageLicenseId;
    private String fax;
    private String registeredCapital;
    private String oibCode;
    private String trCert;
    private String coalStoreSite;
    private int coalQuantity;
    private String coalQuality;
    private String coalTransport;
}
