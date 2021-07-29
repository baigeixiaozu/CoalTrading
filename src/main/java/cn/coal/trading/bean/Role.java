package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author jiyec
 * @Date 2021/7/27 14:31
 * @Version 1.0
 **/
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@TableName("ct_userrole")
public class Role implements Serializable {
    @TableId
    private Integer id;
    private String name;
    private String mark;
    private String type;
}
