package cn.coal.trading;

import cn.coal.trading.bean.RegisteredUser;
import cn.coal.trading.mapper.RegisteredUserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@MapperScan("cn.coal.trading.mapper")
@SpringBootTest
class DemoApplicationTests {

//	private RegisteredUserMapper registeredUserMapper;
//	@Test
//	void contextLoads() {
//		RegisteredUser user = new RegisteredUser();
//		user.setLogin("17237");
//		user.setPass("123456");
//		user.setNick("需求商");
//		user.setEmail("10000@163.com");
//		registeredUserMapper.insert(user);
//	}




}
