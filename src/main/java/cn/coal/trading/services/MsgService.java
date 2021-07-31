package cn.coal.trading.services;

import cn.coal.trading.bean.Msg;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author jiyec
 * @Date 2021/7/28 13:51
 * @Version 1.0
 **/
public interface MsgService {

    /**
     * 发送消息
     * @param msg 发送的消息
     * @return Boolean
     */
    boolean send(Msg msg);

    /**
     * 根据目标用户获取消息
     * @param userId long 指定用户ID
     * @param page int 页码
     * @param limit int 每页数量
     * @return Map<String, Object> total:总数|rows:消息列表
     */
    Map<String, Object> getMsgByToId(long userId, int page, int limit);

    /**
     * 根据来源用户获取消息
     * @param userId null系统 | Long指定用户
     * @return List<Msg> 消息列表
     */
    List<Msg> getMsgByFromId(Long userId);

    /**
     * 根据消息ID与用户ID查询消息具体内容
     *
     * @param msgId 消息ID
     * @param userId 用户ID
     * @return Msg 消息数据实体
     */
    Msg getMsgDetail(long msgId, long userId);

    /**
     * 将消息标记为已读
     *
     * @param msg 消息实体
     * @return boolean
     */
    boolean markAsRead(Set<Long> ids);
}
