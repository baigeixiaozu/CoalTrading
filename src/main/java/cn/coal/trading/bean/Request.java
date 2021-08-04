package cn.coal.trading.bean;

import cn.coal.trading.bean.reqdata.BuyGetData;
import cn.coal.trading.bean.reqdata.SaleGetData;
import cn.coal.trading.bean.reqdata.BuyPubData;
import cn.coal.trading.bean.reqdata.SalePubData;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.Date;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:01
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ct_request", autoResultMap = true)
public class Request {
    @TableId(type = IdType.AUTO)
    private Long id;                        // 需求ID
    private Long userId;                    // 用户ID
    private Date createdTime;               // 创建时间
    private Integer type;                   // 需求类型[ 1.卖出| 2.采购]
    private Integer status;                 // 需求状态[ 1.草稿| 2.审核中| 3.发布| 4.被摘取| 5.隐藏| 6.完成]
    private Long zpId;                      // 摘牌者ID
    private Object zpDetail;                // 摘牌信息（JSON）
    private String contractFile;            // 合同文件（路径）
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object detail;                  // 需求（挂牌）详细

    @TableField(exist = false)
    private BuyPubData buyPubData;          // 采购商挂牌信息（仅起到检查基本数据类型的作用）
    @TableField(exist = false)
    private SalePubData salePubData;        // 供应商挂牌信息（仅起到检查基本数据类型的作用）
    @TableField(exist = false)
    private BuyGetData buyGetData;          // 采购商摘牌信息（仅起到检查基本数据类型的作用）
    @TableField(exist = false)
    private SaleGetData saleGetData;        // 供应商摘牌信息（仅起到检查基本数据类型的作用）

    // 采购商挂牌信息
    public void setBuyPubData(BuyPubData buyPub){
        detail = buyPub;
    }
    // 供应商挂牌信息
    public void setSalePubData(SalePubData salePub) {
        detail = salePub;
    }

    // 采购商摘牌信息
    public void setBuyGetData(BuyGetData buyGetData) {
        this.zpDetail = buyGetData;
    }
    // 供应商摘牌信息
    public void setSaleGetData(SaleGetData saleGetData) {
        this.zpDetail = saleGetData;
    }
}
