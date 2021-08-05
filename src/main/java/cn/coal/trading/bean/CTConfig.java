package cn.coal.trading.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 属性配置文件
 * 参考：https://docs.spring.io/spring-boot/docs/2.5.2/reference/html/configuration-metadata.html#configuration-metadata.annotation-processor
 *
 * @Author jiyec
 * @Date 2021/8/5 14:45
 * @Version 1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "ct")
public class CTConfig {
    // 文件配置
    private UploadFile uploadFile;
    @Data
    public static class UploadFile {
        // 文件路径
        private String location;
    }
}
