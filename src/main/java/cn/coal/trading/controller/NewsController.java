package cn.coal.trading.controller;

import cn.coal.trading.bean.News;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.NewsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.annotation.Logical;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
 *
 * update:2021/8/9
 * version:v1.5
 *
 * update:2021/8/14
 * version:v1.6
 */
@Api(tags = "新闻模块")
@ApiResponses({@ApiResponse(code = 200,message = "操作成功",response = ResponseData.class),
        @ApiResponse(code = 400,message = "参数列表错误",response = ResponseData.class),
        @ApiResponse(code = 401,message = "未授权",response = ResponseData.class),
        @ApiResponse(code = 403,message = "授权受限，授权过期",response = ResponseData.class),
        @ApiResponse(code = 404,message = "资源，服务未找到",response = ResponseData.class),
        @ApiResponse(code = 409,message = "资源冲突，或者资源被锁定",response = ResponseData.class),
        @ApiResponse(code = 429,message = "请求过多被限制",response = ResponseData.class),
        @ApiResponse(code = 500,message = "系统内部错误",response = ResponseData.class),
        @ApiResponse(code = 501,message = "接口未实现",response = ResponseData.class)})
@RestController//注释该类为Controller类，return返回值将被转换成json，字符串除外
@RequestMapping("/news")
@ApiSupport(author = "Heming233")
public class NewsController {
    @Resource
    NewsService newsService;

    //接收打开页面时发送的请求，获取资讯标题
    @ApiOperation(value = "showNews",notes = "加载时获取咨询标题")
    @ApiOperationSupport(author = "Heming233")
    @GetMapping("/show")
    public ResponseData showNews(@RequestParam(value="size",defaultValue = "20") int size,@RequestParam(value = "current",defaultValue = "1") int current){

        //普通用户进入资讯页面可查看到所有资讯
        try{
            Page<News> NewsList= newsService.getAllNews(current,size);

            return new ResponseData(){{
                setCode(200);
                setData(NewsList);
                setMsg("success");
            }};
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(403);
                setMsg("error");
                setError("invalid operation");
            }};
        }

    }

    //资讯审核人员可以直接看到所有待审查的资讯列表
    @ApiOperation(value = "showAuditingNews",notes = "获取待审核的咨询标题")
    @HasRole(value = "NEWS_AUDITOR",logical = Logical.ANY)
    @GetMapping("/showAudit")
    public ResponseData showAuditingNews(@RequestParam(value="size",defaultValue = "20") int size,@RequestParam(value = "current",defaultValue = "1") int current){
        try{
        Page<News> NewsList=newsService.getAuditingNews(current,size);

        return new ResponseData(){{
            setCode(200);
            setData(NewsList);
            setMsg("success");
        }};
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(403);
                setMsg("error");
                setError("invalid operation");
            }};
        }
    }
    //点击资讯查看详细内容
    @ApiOperation(value = "detailNews",notes = "获取详细咨询内容" )
    @GetMapping("/detail/{newsId}")
    public ResponseData detailNews(@PathVariable("newsId") Long id){
        try{
            News news=newsService.getNewsById(id);
            return new ResponseData(){{
                setCode(200);
                setData(news);
                setMsg("success");
            }};
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(204);
                setMsg("error");
                setError("no news caught");
            }};
        }
    }

    //查询资讯
    @ApiOperation(value = "moreNews",notes ="查询更多咨询" )
    @GetMapping("/more/{newsTitle}")
    public ResponseData moreNews(@PathVariable("newsTitle") String title){
        try{
            List<News> NewsList= newsService.getNewsByTitle(title);

            return new ResponseData(){{
                setCode(200);
                setData(NewsList);
                setMsg("success");
            }};
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(204);
                setMsg("error");
                setError("no news caught");
            }};
        }
    }

    //发布资讯
    @ApiOperation(value = "pushNews",notes = "发布咨询")
    @PostMapping("/publish")
    public ResponseData pushNews(@RequestBody News news){
        try{
            TokenProfile profile=ProfileHolder.getProfile();
            Long authorId=Long.parseLong(profile.getId());
            newsService.setOneNews(news,authorId);

            return new ResponseData(){{
                setCode(200);
                setMsg("success");
            }};
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(204);
                setMsg("error");
                setError("push news failed");
            }};
        }
    }

    //存草稿
    @ApiOperation(value = "saveDraft",notes = "发布咨询之存草稿")
    @PostMapping("/draft")
    public  ResponseData saveDraft(@RequestBody News news){
        try{
            TokenProfile profile=ProfileHolder.getProfile();
            Long authorId=Long.parseLong(profile.getId());
            newsService.setOneDraft(news,authorId);

            return new ResponseData(){{
                setCode(200);
                setMsg("success");
            }};
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(204);
                setMsg("error");
                setError("save draft failed");
            }};
        }
    }

    //审核资讯
    @ApiOperation(value = "auditNews",notes = "审核咨询")
    @PostMapping("/audit/{type}")
    public ResponseData auditNews(@PathVariable String type,@RequestBody Long newsId){
        try{
            String status=newsService.newsAudit(type,newsId);;

            return  new ResponseData(){{
                setCode(200);
                setMsg("success");
                setData(status);
            }};
        }
        catch (Exception e){
            return new ResponseData(){{
                setCode(403);
                setMsg("error");
                setError("invalid operation");
            }};
        }
 /*       if("pass".equals(type)){
            newsService.newsAuditPass(newsId);
            return new ResponseData(){{
                setCode(200);
                setMsg("audit passed");
            }};
        }
        else if("reject".equals(type)){
            newsService.newsAuditReject(newsId);
            return new ResponseData(){{
                setCode(200);
                setMsg("audit rejected");
            }};
        }
        return new ResponseData(){{
            setCode(403);
            setMsg("error");
            setError("invalid operation");
        }};*/
    }

}
