package cn.coal.trading.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String login;
    private String pass;
}
