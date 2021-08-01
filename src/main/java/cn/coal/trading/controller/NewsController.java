package cn.coal.trading.controller;

import cn.coal.trading.bean.News;
import cn.coal.trading.services.NewsService;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Heming233
 * Date:2021/7/27
 * Version:v1.0
 *
 * update:2021/7/30
 * version:v1.1
 *
 * update:2021/7/31
 * version:v1.2
 */

/*@CrossOrigin//解决跨域请求授权问题*/
@RestController//注释该类为Controller类，return返回值将被转换成json，字符串除外
@RequestMapping("/news")
public class NewsController {
    @Resource
    NewsService newsService;

    //接收打开页面时发送的请求，获取资讯标题
    @GetMapping("/show")
    public Map<String,Object> showNews(){
        try{
            List<News> NewsList= newsService.getAllNews();
            return new HashMap<String,Object>(){{
                put("code", 200);
                put("msg", "success");
                put("title", NewsList);
            }};
        }
        catch (Exception e){
            return new HashMap<String,Object>(){{
                put("code", 204);//204代码：操作成功执行，但没有返回数据
                put("msg", "error");
                put("error","no news caught");
            }};
        }
    }

    //点击资讯查看详细内容
  /*  @GetMapping("/detail")
    public Map<String,Object> detailNews(@RequestParam(value="url",defaultValue = "none") String url){
        try{
            News news=newsService.getNewsByTitle(url);
            return new HashMap<String,Object>(){{
                put("code",200);
                put("msg","success");
                put("data",news);
            }};
        }
        catch (Exception e){
            return new HashMap<String,Object>(){{
                put("code",204);
                put("msg","error");
                put("error","no news caught");
            }};
        }
    }*/
}
