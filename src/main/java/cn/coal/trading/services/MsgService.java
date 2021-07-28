package cn.coal.trading.services;

import cn.coal.trading.bean.Msg;

import java.util.List;

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
     * @param userId Long指定用户
     * @return List<Msg> 消息列表
     */
    List<Msg> getMsgByToId(Long userId);

    /**
     * 根据来源用户获取消息
     * @param userId null系统 | Long指定用户
     * @return List<Msg> 消息列表
     */
    List<Msg> getMsgByFromId(Long userId);
}
