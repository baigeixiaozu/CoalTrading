package cn.coal.trading;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"cn.coal.trading.mapper"})
public class CTApplication {

	public static void main(String[] args) {
		SpringApplication.run(CTApplication.class, args);
	}

}
