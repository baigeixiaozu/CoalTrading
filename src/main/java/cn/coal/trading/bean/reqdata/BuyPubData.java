package cn.coal.trading.bean.reqdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 买方挂牌数据
 *
 * @Author jiyec
 * @Date 2021/8/2 7:10
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyPubData {
    private BaseData baseData;                  // 基础信息
    private CoalQuality coalQuality;            // 煤质
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BaseData {
    private String applicant;                   // 申请人
    private String signer;                      // 签发人
    private Date reqDate;                       // 申请日期
    private Date deliveryStartTime;             // 交货开始时间
    private Date deliveryEndTime;               // 交货结束时间
    private String coalType;                    // 煤种[用这种形式：x煤,y煤]
    private Double buyQuantity;                 // 采购数量
    private String transportMode;               // 运输方式
    private String deliveryLocation;            // 交货地点
    private String settlementMethod;            // 结算方式
    private String acceptanceMethod;            // 验收方式
    private String paymentMethod;               // 结算付款方式
    private Integer deposit1;                   // 报价保证金[ 0不要求| >0要求具体金额]（元/吨）
    private Integer deposit2;                   // 履约保证金[ 0不要求| >0要求具体金额]（元/吨）
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CoalQuality {
    private Double lowHeat;             // 收到基低位发热量
    private Double sdjql;               // 收到基全硫
    private Double qsf;                 // 全水分
    private Double sdjhf;               // 收到基灰分
    private Double sdjhff1;             // 收到基挥发分
    private Double sdjhff2;             // 收到基挥发分

    private Double kgjsf;               // 空干基水分
    private Double kgjql;               // 空干基全硫
    private Double kgjhff1;             // 空干基挥发分
    private Double kgjhff2;             // 空干基挥发分

    private Double highHeat;            // 干基高位发热量
    private Double gjql;                // 干基全硫
    private Double gzwhjhff1;           // 干燥无灰基挥发分
    private Double gzwhjhff2;           // 干燥无灰基挥发分

    private Double granularity;         // 粒度
    private Double hrd;                 // 灰熔点
    private Double hskmxs;              // 哈式可磨系数
    private String remark;              // 备注
}