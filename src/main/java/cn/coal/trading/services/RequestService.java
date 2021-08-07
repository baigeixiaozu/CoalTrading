package cn.coal.trading.services;

import cn.coal.trading.bean.Request;
import cn.coal.trading.bean.ResponseData;

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
    Map<String,Object> listAvailable(Long userId, int page, int limit);

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

    /**
     * 用户的需求
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    Map<String,Object> myList(Long userId, int page, int limit);

    /**
     * 待审核需求列表
     *
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> auditPending(int page, int limit);

    /**
     * 未审核需求的详情
     *
     * @param req_id
     * @param userId
     * @return
     */
    Request auditDetail(long req_id);

    /**
     * 审核需求
     * @param request     需求信息
     * @return
     */
    boolean doAudit(Request request);

    /**
     * 更新合同文件路径
     * @param reqId             需求ID
     * @param contractPath      合同路径
     * @return                  是否成功
     */
    boolean updateContract(long reqId, long userId, String contractPath);

    /**
     * 获取详细需求
     * @param id    需求id
     * @return
     */
    Request getReqDetails(int id);


    /**
     * 摘牌功能
     * @param userId, requestId
     * @return
     */
    ResponseData delist(long userId,int requestId);



}
