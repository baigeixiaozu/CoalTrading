package cn.coal.trading.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author jiyec
 * @Date 2021/7/26 21:42
 * @Version 1.0
 **/
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class BaseUser {
    private Integer userId;
}
