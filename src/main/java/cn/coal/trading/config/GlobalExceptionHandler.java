package cn.coal.trading.config;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.exception.http.ForbiddenAction;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.UnauthorizedAction;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author jiyec
 * @Date 2021/7/28 18:02
 * @Version 1.0
 **/
@RestControllerAdvice //@RestControllerAdvice 该注解定义全局异常处理类
public class GlobalExceptionHandler {
    @ExceptionHandler({HttpAction.class})
    public Map<String, Object> httpCodeException(HttpServletResponse response, HttpAction action) {
        Map<String, Object> err = new HashMap<>();
        int code;
        String msg;
        if (action instanceof UnauthorizedAction) {
            response.setStatus(HttpConstants.UNAUTHORIZED);
            code = 401;
            msg = "未登录";
        } else if (action instanceof ForbiddenAction) {
            response.setStatus(HttpConstants.FORBIDDEN);
            code = 403;
            msg = "你没有权限";
        }else{
            code = 500;
            msg = "未知异常";
        }

        err.put("code", code);
        err.put("msg", "fail");
        err.put("error", msg);
        return err;
    }
    // @ExceptionHandler(value = RuntimeException.class) //@ExceptionHandler 该注解声明异常处理方法
    // public String defaultErrorHandler(HttpServletResponse resp, RuntimeException e) throws Exception {
    //     return "error";
    // }
}
