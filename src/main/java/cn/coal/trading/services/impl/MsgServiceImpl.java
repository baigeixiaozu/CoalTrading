package cn.coal.trading.services.impl;

import cn.coal.trading.bean.Msg;
import cn.coal.trading.mapper.MsgMapper;
import cn.coal.trading.services.MsgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public Map<String, Object> getMsgByToId(long userId, int page, int limit) {

        Page<Msg> msgs = msgMapper.selectPage(new Page<>(page, limit), new QueryWrapper<Msg>() {{
            select("id", "title", "msg_type", "created", "from_username", "read_status");
            eq("to_userid", userId);
        }});
        return new HashMap<String, Object>(){{
            put("rows", msgs.getRecords());
            put("total", msgs.getTotal());
            put("pages", msgs.getPages());
        }};
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

    @Override
    public Msg getMsgDetail(long msgId, long userId) {
        return msgMapper.selectOne(new QueryWrapper<Msg>() {{
            eq("id", msgId);
            eq("to_userid", userId);
        }});
    }

    @Override
    public boolean markAsRead(Set<Long> ids, long userId) {
        int count = msgMapper.update(new Msg() {{
            setReadStatus(2);       // 标记消息为已读
        }}, new QueryWrapper<Msg>() {{
            in("id", ids);
            eq("to_userid", userId);
        }});
        return count > 0;
    }
}
