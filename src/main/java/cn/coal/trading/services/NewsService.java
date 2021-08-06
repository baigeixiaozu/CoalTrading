package cn.coal.trading.services;

import cn.coal.trading.bean.News;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Heming233
 * Date:2021/7/29
 * Version:v1.0
 *
 * update:2021/8/2
 * version:v1.3
 */

public interface NewsService {

    //刷新页面展示所有资讯
    List<News> getAllNews();

    //点击单条资讯查看详细信息
    News getNewsById(Long id);

    //查询咨询
    List<News> getNewsByTitle(String title);

    //发布资讯
    News setOneNews(News content, Long authorId) throws JsonProcessingException;

    //存草稿
    News setOneDraft(News content, Long authorId);
}
