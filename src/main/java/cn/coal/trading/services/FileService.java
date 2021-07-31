package cn.coal.trading.services;

import cn.coal.trading.bean.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Sorakado
 * @Date 2021/7/29 18:54
 * @Version 1.0
 **/
public interface FileService {
    ResponseData uploadFiles(MultipartFile[] multipartFile) throws IOException;
}
