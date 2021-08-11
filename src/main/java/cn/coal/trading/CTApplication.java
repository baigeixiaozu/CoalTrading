package cn.coal.trading;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@MapperScan(basePackages = {"cn.coal.trading.mapper"})
@ServletComponentScan("cn.coal.trading.Servlet")
public class CTApplication {

	public static void main(String[] args) {
		SpringApplication.run(CTApplication.class, args);
	}

}
