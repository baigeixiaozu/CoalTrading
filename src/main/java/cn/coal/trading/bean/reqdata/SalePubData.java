package cn.coal.trading.bean.reqdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 卖方挂牌数据
 *
 * @Author jiyec
 * @Date 2021/8/2 7:13
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalePubData {
    private Double supplyQuantity;          // 供应数量（万吨）
    private Long calorificValue;            // 热值（KCal/kg）≥
    private Double unitPrice;               // 原煤单价(元/吨)
    private Double ts;                      // 全硫(%)
    private String location;                // 产地
    private Double transportPrice;          // 运费单价
    private Double vc;                      // 挥发分(%)
    private String sendLocal;               // 发站（发货港口）
    private Double kgjhf;                       // 空干基灰分（%）
    private Double ms;                      // 全水分（%）
}