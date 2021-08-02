package cn.coal.trading;

import cn.coal.trading.bean.News;
import cn.coal.trading.services.NewsService;
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
 */

@SpringBootTest
public class NewsTests {

    @Resource
    NewsService newsService;

    @Test
    void show(){
        List<News> hashMap=newsService.getAllNews();
    }

    @Test
    void selectById(){
        News news=newsService.getNewsById(4L);
    }
}
