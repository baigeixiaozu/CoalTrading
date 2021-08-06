package cn.coal.trading.controller;

import cn.coal.trading.bean.News;
import cn.coal.trading.services.NewsService;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
 *
 * update:2021/8/2
 * version:v1.3
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
                put("infoList", NewsList);
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
    @GetMapping("/detail/{newsId}")
    public Map<String,Object> detailNews(@PathVariable("newsId") Long id){
        try{
            News news=newsService.getNewsById(id);
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
    }

    //查询资讯
    @GetMapping("/more/{newsTitle}")
    public Map<String,Object> moreNews(@PathVariable("newsTitle") String title){
        try{
            List<News> NewsList= newsService.getNewsByTitle(title);

            return new HashMap<String,Object>(){{
                put("code", 200);
                put("msg", "success");
                put("infoList", NewsList);
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

    //发布资讯
    @PostMapping("/publish")
    public Map<String,Object> pushNews(@RequestBody News news){
        try{
            TokenProfile profile=ProfileHolder.getProfile();
            Long authorId=Long.parseLong(profile.getId());
            newsService.setOneNews(news,authorId);

            return new HashMap<String,Object>(){{
                put("code", 200);  
                put("msg", "success");
            }};
        }
        catch (Exception e){
            return new HashMap<String,Object>(){{
                put("code", 204);//204代码：操作成功执行，但没有返回数据
                put("msg", "error");
                put("error","push news failed");
            }};
        }
    }

    //存草稿
    @PostMapping("/draft")
    public  Map<String,Object> saveDraft(@RequestBody News news){
        try{
            TokenProfile profile=ProfileHolder.getProfile();
            Long authorId=Long.parseLong(profile.getId());
            newsService.setOneDraft(news,authorId);

            return new HashMap<String,Object>(){{
                put("code", 200);
                put("msg", "success");
            }};
        }
        catch (Exception e){
            return new HashMap<String,Object>(){{
                put("code", 204);//204代码：操作成功执行，但没有返回数据
                put("msg", "error");
                put("error","save draft failed");
            }};
        }
    }

    //审核资讯通过
/*    @GetMapping("/newsPass")
    public Map<String,Object> auditNews()*/

}
