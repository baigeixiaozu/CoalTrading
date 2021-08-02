package cn.coal.trading.services;

import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.bean.User;

public interface UpdateService {
    /**
     * 修改财务账户
     * @Author Sorakado
     * @Date 2021/8/1 15:04
     * @Version 1.0
     **/
    ResponseData updateUser(User user);
}
