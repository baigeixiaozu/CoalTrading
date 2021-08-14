package cn.coal.trading.services;

import cn.coal.trading.bean.CertType;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author jiyecafe@gmail.com
 * @Date 2021/7/29 18:54
 * @Version 1.0
 **/
public interface FileService {
    /**
     * 文件保存至服务器
     * @param multipartFile
     * @param type
     * @param userId
     * @return
     */
    String storeFile2Local(MultipartFile multipartFile, CertType type, long userId);

    /**
     * 文件路径存储至数据库
     * @param path
     * @param type
     * @param userId
     * @return
     */
    boolean storeCert2DB(String path, CertType type, long userId);
}
