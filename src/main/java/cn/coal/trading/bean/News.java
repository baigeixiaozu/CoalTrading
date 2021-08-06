package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Heming233
 * Date:2021/7/27
 * Version:v1.0
 *
 * update:2021/8/2
 * version:v1.3
 */

@Data//配置Setter、Getter方法
/*@Component//将pojo类实例化到spring容器内*/
@NoArgsConstructor//配置无参构造方法
@AllArgsConstructor//配置有参构造方法
@TableName(value="ct_news")//配置对应数据库的表
public class News implements Serializable {
    @TableId(type= IdType.AUTO)
    private Long id;                //资讯编号
    private String title;           //资讯标题
    private String content;         //资讯内容
    private Date date;              //资讯日期
    private Integer status;         //数据库规定[1.草稿| 2.审核中| 3.驳回（审核不通过）| 4.发布| 5.撤销（隐藏）| 删除（没有记录无状态）]
    private Long authorId;        //发布人员ID
    private Long auditorId;       //审核人员ID，0表示资讯审核人员待定，还未分配到具体的审核手上
    private String opinion;         //审核意见
}
