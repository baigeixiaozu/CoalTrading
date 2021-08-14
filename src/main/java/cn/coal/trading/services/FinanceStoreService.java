package cn.coal.trading.services;

/**
 * @Author jiyec
 * @Date 2021/8/14 7:02
 * @Version 1.0
 **/
public interface FinanceStoreService {
    boolean store(double quantity, String certPath);
}
