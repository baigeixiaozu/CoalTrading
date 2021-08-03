package cn.coal.trading.services.impl;

import cn.coal.trading.bean.News;
import cn.coal.trading.mapper.NewsMapper;
import cn.coal.trading.services.NewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
//import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * Created by Heming233
 * Date:2021/7/29
 * Version:v1.0
 *
 * update:2021/7/31
 * version:v1.1
 *
 * update:2021/8/2
 * version:v1.3
 */

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    NewsMapper newsMapper;

    //浏览全部资讯
    @Override
    public List<News> getAllNews(){
        QueryWrapper<News> wrapper=new QueryWrapper<>();
/*        wrapper.eq("title",);
        wrapper.eq("context",2);*/

        //设置查询条件
        wrapper.isNotNull("title");
        wrapper.eq("status","4");
        wrapper.select("title","id");

        return newsMapper.selectList(wrapper);
    }

    //点击资讯查看详细内容
    @Override
    public News getNewsById(Long id){
        QueryWrapper<News> wrapper=new QueryWrapper<>();

        //设置查询条件
        wrapper.eq("id",id);
        wrapper.eq("status","4");
        wrapper.select("title","content","date");

        return newsMapper.selectOne(wrapper);
    }

    //查询资讯
    @Override
    public List<News> getNewsByTitle(String title){
        QueryWrapper<News> wrapper=new QueryWrapper<>();

        //设置查询条件
        wrapper.like("title",title);
        wrapper.eq("status","4");
        wrapper.select("title","id");

        return newsMapper.selectList(wrapper);
    }

    //发布资讯
    @Override
    public News setOneNews(String content,Long authorId) throws JsonProcessingException {
        /*QueryWrapper<News> wrapper=new QueryWrapper<>();*/
        ObjectMapper mapper=new ObjectMapper();
        News news=mapper.readValue(content,News.class);
        news.setStatus(2);//刚发布的资讯还处于审查中状态
        news.setAuditorId(0L);
        news.setAuthorId(authorId);

        newsMapper.insert(news);
        return news;
    }

}
