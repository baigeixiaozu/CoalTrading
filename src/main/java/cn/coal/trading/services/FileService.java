package cn.coal.trading.services;

import cn.coal.trading.bean.CertType;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Sorakado
 * @Date 2021/7/29 18:54
 * @Version 1.0
 **/
public interface FileService {
    String storeFile2Local(MultipartFile multipartFile, CertType type, long userId);
    boolean storeCert2DB(String path, CertType type, long userId);
}
