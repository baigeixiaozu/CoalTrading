package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author jiyec
 * @Date 2021/7/31 22:01
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_request")
public class Request {
    private Long id;                // 需求ID
    private Long userId;            // 用户ID
    private Date createdTime;       // 创建时间
    private Date endedTime;         // 结束时间
    private Integer type;           // [ 1.卖出| 2.采购]
    private Integer status;         // 需求状态[1.草稿| 2.发布| 3.被摘取| 4.隐藏| 5.完成]
    private Object detail;          // 需求信息(JSON)
}
