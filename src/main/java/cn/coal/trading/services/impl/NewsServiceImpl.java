package cn.coal.trading.services.impl;

import cn.coal.trading.bean.News;
import cn.coal.trading.mapper.NewsMapper;
import cn.coal.trading.services.NewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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
 *
 * update:2021/8/3
 * version:v1.4
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
    public News setOneNews(News content, Long authorId) throws JsonProcessingException {
        /*QueryWrapper<News> wrapper=new QueryWrapper<>();*/
        ObjectMapper mapper=new ObjectMapper();
        //Long Id=2L;//测试用，接口跑通后删除
        content.setStatus(2);//刚发布的资讯还处于审查中状态
        content.setAuditorId(3L);
        content.setAuthorId(authorId);

        newsMapper.insert(content);
        return content;
    }

}
