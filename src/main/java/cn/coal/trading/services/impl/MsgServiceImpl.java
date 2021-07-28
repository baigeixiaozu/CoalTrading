package cn.coal.trading.services.impl;

import cn.coal.trading.bean.Msg;
import cn.coal.trading.mapper.MsgMapper;
import cn.coal.trading.services.MsgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author jiyec
 * @Date 2021/7/28 14:25
 * @Version 1.0
 **/
@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    MsgMapper msgMapper;

    @Override
    public boolean send(Msg msg) {
        int insert = msgMapper.insert(msg);
        return insert > 0;
    }

    @Override
    public List<Msg> getMsgByToId(Long userId) {
        return msgMapper.selectList(new QueryWrapper<Msg>() {{
            eq("to_userid", userId);
        }});
    }

    @Override
    public List<Msg> getMsgByFromId(Long userId) {
        return msgMapper.selectList(new QueryWrapper<Msg>() {{
            if(userId == null)
                isNull("from_userid");
            else
                eq("from_userid", userId);
        }});
    }
}
