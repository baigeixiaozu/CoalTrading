package cn.coal.trading;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author jiyec
 * @Date 2021/7/31 19:02
 * @Version 1.0
 **/
public class SimpleTests {
    @Test
    public void emailTest(){
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("dnjnfslkffkjkjkslioeo9edkdjfks");
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
    }
}
