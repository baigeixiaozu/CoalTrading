package cn.coal.trading.services.impl;

import cn.coal.trading.bean.News;
import cn.coal.trading.mapper.NewsMapper;
import cn.coal.trading.services.NewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Heming233
 * Date:2021/7/29
 * Version:v1.0
 */

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    NewsMapper newsMapper;

    //构建wrapper条件，使用BaseMapper类的selectList方法进行查询全部内容
    @Override
    public List<News> getAllNews(){
        QueryWrapper<News> wrapper=new QueryWrapper<>();
        wrapper.eq("title",1);
        wrapper.eq("content",2);

        return newsMapper.selectList(wrapper);
    }
}
