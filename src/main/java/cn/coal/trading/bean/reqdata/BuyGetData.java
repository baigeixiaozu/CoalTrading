package cn.coal.trading.bean.reqdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 买方摘牌数据
 *
 * @Author jiyec
 * @Date 2021/8/2 9:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyGetData {
    private Double supplyQuantity;      // 供应数量
    private Double heatValue;           // 热值
    private Double price;               // 原煤单价
    private Double totalSulphur;        // 全硫
}
