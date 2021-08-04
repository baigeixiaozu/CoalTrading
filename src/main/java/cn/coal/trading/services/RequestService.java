package cn.coal.trading.services;

import cn.coal.trading.bean.Request;

import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/8/2 7:08
 * @Version 1.0
 **/
public interface RequestService {
    /**
     * 获取需求列表
     * @param userId    用户ID|null(所有)
     * @param page      页码
     * @param limit     每页数量
     * @return          需求数据
     */
    Map<String,Object> listAvailable(Integer userId, int page, int limit);

    /**
     * 新建需求
     * @param request   需求数据
     * @return      需求ID
     */
    long newReq(Request request);

    /**
     * 编辑需求
     * 根据需求ID和用户ID（自动获取）定位
     *
     * @param request   需求数据
     * @return      影响行数
     */
    int edit(Request request);
}
