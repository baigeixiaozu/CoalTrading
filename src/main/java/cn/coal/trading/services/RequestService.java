package cn.coal.trading.services;

import cn.coal.trading.bean.Request;

/**
 * @Author jiyec
 * @Date 2021/8/2 7:08
 * @Version 1.0
 **/
public interface RequestService {
    int newReq(Request request);
    int edit(Request request);
}
