package cn.coal.trading.services.impl;

import cn.coal.trading.bean.News;
import cn.coal.trading.mapper.NewsMapper;
import cn.coal.trading.services.NewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 *
 * update:2021/8/9
 * version:v1.5
 */

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    NewsMapper newsMapper;

    //浏览全部资讯
    @Override
    public Page<News> getAllNews(int current,int size){
        Page<News> Page=new Page<>(current,size);
        QueryWrapper<News> wrapper=new QueryWrapper<>();
/*        wrapper.eq("title",);
        wrapper.eq("context",2);*/

        //设置查询条件s
        wrapper.isNotNull("title");
        wrapper.eq("status","4");
        wrapper.select("title","id","date");

        return newsMapper.selectPage(Page,wrapper);
    }

    //展示所有待审核的资讯
    @Override
    //@HasRole(value = "NEWS_AUDITOR",logical = Logical.ANY)
    public Page<News> getAuditingNews(int current,int size){
        Page<News> page=new Page<>(current,size);
        QueryWrapper<News> wrapper=new QueryWrapper<>();

        //wrapper.eq("status","2");
        wrapper.select("title","id","date");

        return newsMapper.selectPage(page,wrapper);
    }

    //点击资讯查看详细内容
    @Override
    public News getNewsById(Long id){
        QueryWrapper<News> wrapper=new QueryWrapper<>();

        //设置查询条件
        wrapper.eq("id",id);
        //wrapper.eq("status","4");
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
        wrapper.select("title","id","date");

        return newsMapper.selectList(wrapper);
    }

    //发布资讯
    @Override
    public News setOneNews(News content, Long authorId){
        /*QueryWrapper<News> wrapper=new QueryWrapper<>();*/
        //ObjectMapper mapper=new ObjectMapper();
        //Long Id=2L;//测试用，接口跑通后删除
        content.setStatus(2);//刚发布的资讯还处于审查中状态
        content.setAuditorId(3L);//默认审核员。正式审核时会将当前正在进行审核的审核员ID写进去
        content.setAuthorId(authorId);

        newsMapper.insert(content);
        return content;
    }

    //存草稿
    public  News setOneDraft(News content, Long authorId){
        //ObjectMapper mapper=new ObjectMapper();
        content.setStatus(1);//状态码为1表示草稿
        content.setAuditorId(null);
        content.setAuthorId(authorId);

        newsMapper.insert(content);
        return content;
    }

    //审核资讯
    public String newsAudit(String type,Long newsId,Long auditorId){
        UpdateWrapper<News> wrapper=new UpdateWrapper<>();

        wrapper.eq("id",newsId);
        News news=newsMapper.selectOne(wrapper);
        news.setAuditorId(auditorId);

        if("pass".equals(type)){
            news.setStatus(4);

            newsMapper.update(news,wrapper);
            return "audit passed";
        }
        else if("reject".equals(type)){
            news.setStatus(3);

            newsMapper.update(news,wrapper);
            return "audit reject";
        }
        else{
            return "invalid operation";
        }
    }

}
