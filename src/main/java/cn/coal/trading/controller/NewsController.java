package cn.coal.trading.controller;

import cn.coal.trading.bean.News;
import cn.coal.trading.services.NewsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Heming233
 * Date:2021/7/27
 * Version:v1.0
 */

/*@CrossOrigin//解决跨域请求授权问题*/
@RestController//注释该类为Controller类，return返回值将被转换成json，字符串除外
@RequestMapping("/news")//接收打开页面时发送的请求，获取资讯标题和详细资讯路径
public class NewsController {
    @Resource
    NewsService newsService;

    @GetMapping("/show")
    public Map<String,Object> showNews(){
        try{
            List<News> NewsList= newsService.getAllNews();
            return new HashMap<String,Object>(){{
                put("code", 200);
                put("msg", "success");
                put("data", NewsList);
            }};
        }
        catch (Exception e){
            return new HashMap<String,Object>(){{
                put("code", 204);//204代码：操作成功执行，但没有返回数据
                put("msg", "success");
            }};
        }

    }
}
