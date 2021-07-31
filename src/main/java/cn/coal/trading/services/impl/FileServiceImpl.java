package cn.coal.trading.services.impl;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @Author Sorakado
 * @Date 2021/7/31 22:54
 * @Version 1.0
 **/
@Service
public class FileServiceImpl implements FileService {
    @Value("${uploadFile.location}")
    private String uploadFileLocation;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
    @Override
    public ResponseData uploadFiles(MultipartFile[] multipartFile) throws IOException {

        ResponseData response=new ResponseData();
        if (multipartFile == null) {
            response.setCode(400);
            response.setMsg("文件为空");
            response.setError("参数列表错误！（缺少，格式不匹配）");
            response.setData(null);

            return response;
        }
        for(MultipartFile file:multipartFile) {
            String fileName = file.getOriginalFilename();


            String filePath = uploadFileLocation + fileName;
            File target = new File(filePath);
            Files.copy(file.getInputStream(), target.toPath());
            System.out.println(target.getPath());
        }
//        File saveFile = new File(uploadFileLocation, fileName);
//        System.out.println("文件保存成功：" + saveFile.getPath());
//
//        File file=new File(saveFile.getPath());
//        multipartFile.transferTo(saveFile);

        response.setCode(201);
        response.setMsg("上传成功");
        response.setError("无");
        response.setData(null);

        return response;
    }
}