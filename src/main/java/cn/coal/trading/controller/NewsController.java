package cn.coal.trading.controller;

import cn.coal.trading.bean.News;
import cn.coal.trading.bean.ResponseData;
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
 * <p>
 * update:2021/7/30
 * version:v1.1
 * <p>
 * update:2021/7/31
 * version:v1.2
 * <p>
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
    public ResponseData showNews() {
        try {
            List<News> NewsList = newsService.getAllNews();

            return new ResponseData() {{
                setCode(200);
                setData(NewsList);
                setMsg("success");
            }};
        } catch (Exception e) {
            return new ResponseData() {{
                setCode(204);
                setMsg("error");
                setError("no news caught");
            }};
        }
    }

    //点击资讯查看详细内容
    @GetMapping("/detail/{newsId}")
    public ResponseData detailNews(@PathVariable("newsId") Long id) {
        ResponseData response = new ResponseData();
        News news = newsService.getNewsById(id);
        if (news == null) {
            response.setCode(404);
            response.setMsg("fail");
            response.setError("资讯不存在");
        } else {
            response.setCode(200);
            response.setMsg("success");
            response.setData(news);
        }
        return response;
    }

    //查询资讯
    @GetMapping("/more/{newsTitle}")
    public ResponseData moreNews(@PathVariable("newsTitle") String title) {
        try {
            List<News> NewsList = newsService.getNewsByTitle(title);

            return new ResponseData() {{
                setCode(200);
                setData(NewsList);
                setMsg("success");
            }};
        } catch (Exception e) {
            return new ResponseData() {{
                setCode(204);
                setMsg("error");
                setError("no news caught");
            }};
        }
    }

    //发布资讯
    @PostMapping("/publish/{type}")
    public ResponseData pushNews(@RequestBody News news, @PathVariable String type) {
        try {
            TokenProfile profile = ProfileHolder.getProfile();
            Long authorId = Long.parseLong(profile.getId());
            newsService.setOneNews(news, authorId);

            return new ResponseData() {{
                setCode(200);
                setMsg("success");
            }};
        } catch (Exception e) {
            return new ResponseData() {{
                setCode(204);
                setMsg("error");
                setError("push news failed");
            }};
        }
    }

    //存草稿
    @PostMapping("/draft")
    public ResponseData saveDraft(@RequestBody News news) {
        try {
            TokenProfile profile = ProfileHolder.getProfile();
            Long authorId = Long.parseLong(profile.getId());
            newsService.setOneDraft(news, authorId);

            return new ResponseData() {{
                setCode(200);
                setMsg("success");
            }};
        } catch (Exception e) {
            return new ResponseData() {{
                setCode(204);
                setMsg("error");
                setError("save draft failed");
            }};
        }
    }

    //审核资讯通过
/*    @GetMapping("/newsPass")
    public Map<String,Object> auditNews()*/

}
