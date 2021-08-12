package cn.coal.trading.services;

import cn.coal.trading.bean.AuditOpinion;
import cn.coal.trading.bean.ResponseData;
import com.baomidou.shaun.core.profile.TokenProfile;

import java.util.Map;

public interface DelistService {

    /**
     * @author Sorakado
     * 摘牌功能
     * @param userId, requestId
     * @return
     */
    ResponseData delist(long userId, long requestId);


    /**
     * @author Sorakado
     * 获取摘牌列表
     *
     * @param page      页码
     * @param limit     每页数量
     * @return          需求数据
     */
    Map<String,Object> listDelist(int page, int limit);

    /**
     * @author Sorakado
     * 获取挂牌摘牌的详细信息
     * @param delistId   挂牌id
     * @return          需求数据
     */
    ResponseData getDetailInfo(long delistId);
    /**
     * @author Sorakado
     * 审核功能
     * @param delistId   挂牌id
     * @return          需求数据
     */
    ResponseData examine(long delistId, AuditOpinion opinion);

    /**
     * @author Sorakado
     * 获取摘牌列表
     * @param page      页码
     * @param limit     每页数量
     * @return          需求数据
     */
    Map<String,Object> listDelistFinance(TokenProfile profile, int page, int limit);

    /**
     * 公司账户获取指定的摘牌详细信息
     * @author Sorakado
     * @time 2021/8/11/ 20:24
     * @version 1.0
     */
    ResponseData getDetailInfoForUser(TokenProfile profile,long delistId);
}
