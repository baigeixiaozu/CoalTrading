package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/*
Created by Heming233
Date:2021/7/27
Version:v1.0
 */

@Data//配置Setter、Getter方法
/*@Component//将pojo类实例化到spring容器内*/
@NoArgsConstructor//配置无参构造方法
@AllArgsConstructor//配置有参构造方法
@TableName(value="ct_news")//配置对应数据库的表
public class News implements Serializable {
    private String newsID;//资讯编号
    private String newTitle;//资讯标题
    private String newsContent;//资讯内容
    private Date newDate;//资讯日期
    private Integer status;//状态：草稿：1；发布：2
    private String authorID;//发布人员ID
    private String auditorID;//审核人员ID
}
