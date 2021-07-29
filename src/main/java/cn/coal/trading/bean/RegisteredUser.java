package cn.coal.trading.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Sorakado
 * @time 7.28 20:00
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ct_users")
public class RegisteredUser  {
    private String login;
    private String pass;
    private String nick;
    private String email;
}
