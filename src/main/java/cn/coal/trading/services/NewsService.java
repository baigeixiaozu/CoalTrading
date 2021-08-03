package cn.coal.trading.services;

import cn.coal.trading.bean.News;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Heming233
 * Date:2021/7/29
 * Version:v1.0
 */

public interface NewsService {

    //刷新页面展示所有资讯
    List<News> getAllNews();

    //点击单条资讯查看详细信息
    News getNewsById(Long id);

    //查询咨询
    List<News> getNewsByTitle(String title);
}
