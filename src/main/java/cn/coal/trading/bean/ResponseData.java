package cn.coal.trading.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应体实体
 *
 * @Author jiyec
 * @Date 2021/7/29 11:15
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private Integer code;
    private String msg;
    private String error;
    private Object data;
}
