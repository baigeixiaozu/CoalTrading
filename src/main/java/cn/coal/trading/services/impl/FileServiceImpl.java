package cn.coal.trading.services.impl;

import cn.coal.trading.bean.CertType;
import cn.coal.trading.bean.CompanyInformation;
import cn.coal.trading.bean.FinanceProperty;
import cn.coal.trading.mapper.CompanyMapper;
import cn.coal.trading.mapper.FinanceMapper;
import cn.coal.trading.services.FileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @Author Sorakado
 * @Date 2021/7/31 22:54
 * @Version 1.0
 **/
@Service
public class FileServiceImpl implements FileService {

    @Resource
    CompanyMapper companyMapper;

    @Resource
    FinanceMapper financeMapper;

    @Value("${ct.uploadFile.location}")
    private String uploadFileLocation;      // 上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值

    @Override
    public String storeFile2Local(MultipartFile file, CertType type, long userId){

        String basePath = uploadFileLocation + "/userCert/" + userId + "/";
        File basePathDir = new File(basePath);
        if (!basePathDir.exists()) {
            basePathDir.mkdirs();
        }

        // 取后缀
        String suffix = StringUtils.substringAfter(file.getOriginalFilename(), ".");
        String fileName = type.name() + "_" + userId + "." + suffix;
        String filePath = basePath + fileName;

        File target = new File(filePath);
        try {
            file.transferTo(target);        // 转移上传的文件
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePath;
    }

    public boolean storeCert2DB(String path, CertType type, long userId){

        if(type == CertType.AO_PERMIT_FILE){
            // i
            int i = financeMapper.update(new FinanceProperty(), new UpdateWrapper<FinanceProperty>() {{
                set("ao_permit_file", path);
                eq("main_userid", userId);
            }});
            if(i == 0){
                // insert
                i = financeMapper.insert(new FinanceProperty() {{
                    setMainUserid(userId);
                    setAoPermitFile(path);
                }});
            }
            return i == 1;
        }else{
            // 企业信息
            int i;
            i = companyMapper.updateCert(type.name().toLowerCase(Locale.ROOT), path, userId);
            if(i == 0){
                i = companyMapper.insertCert(type.name().toLowerCase(Locale.ROOT), userId, path);
            }
            return i == 1;
        }
    }
}
