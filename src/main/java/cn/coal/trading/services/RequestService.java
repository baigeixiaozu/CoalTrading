package cn.coal.trading.services;

import cn.coal.trading.bean.Request;
import com.baomidou.shaun.core.profile.TokenProfile;

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
    Map<String,Object> getPublicList(Long userId, int page, int limit);

    /**
     * @author Sorakado
     * 获取详细需求
     * @param id    需求id
     * @return
     */
    Request getPublicDetail(int id);

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
     * @param profile 登录用户信息
     * @param page 页数
     * @param limit 每页数量
     * @return
     */
    Map<String,Object> myList(TokenProfile profile, int page, int limit);

    /**
     * 用户的需求详情
     * @param profile 登录用户信息
     * @param req_id 需求ID
     * @return   Request 需求所有信息
     */
    Request myDetail(TokenProfile profile, long req_id);

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
     * 确认或拒绝合同
     * @param reqId
     * @return
     */
    boolean acceptContract(long reqId, boolean accept);

    String getComName(long id);
}
