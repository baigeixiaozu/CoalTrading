package cn.coal.trading.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
Created by Heming233
Date:2021/7/27
Version:v1.0
 */

@Data//配置Setter、Getter方法
@Component//将pojo类实例化到spring容器内
@NoArgsConstructor//配置无参构造方法
@AllArgsConstructor//配置有参构造方法
public class News {
    private int newsID;
    private String newTitle;
    private String newsContent;
    private Date newDate;
}
