package cn.coal.trading;

import cn.coal.trading.bean.News;
import cn.coal.trading.services.NewsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Heming233
 * Date:2021/7/30
 * Version:v1.0
 *
 * update:2021/8/2
 * version:v1.3
 */

@SpringBootTest
public class NewsTests {

    @Resource
    NewsService newsService;

    @Test
    void show(){
        Page<News> hashMap=newsService.getAllNews(1,20);
    }

    @Test
    void selectById(){
        News news=newsService.getNewsById(4L);
    }

    @Test
    void setOneNews() throws JsonProcessingException {
        News news=new News();
        news.setContent("这是一些内容");
        news.setTitle("山西将建40座绿色开采煤矿");
        newsService.setOneNews(news,2L);
    }
}
