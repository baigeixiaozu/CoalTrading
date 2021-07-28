package cn.coal.trading.config;

import lombok.extern.slf4j.Slf4j;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.exception.http.ForbiddenAction;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.UnauthorizedAction;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author jiyec
 * @Date 2021/7/28 18:02
 * @Version 1.0
 **/
@ControllerAdvice //@ControllerAdvice 该注解定义全局异常处理类
@Slf4j
public class GlobalExceptionHandler {
    // @ExceptionHandler({HttpAction.class})
    // public String httpCodeException(HttpServletResponse response, HttpAction action) {
    //     log.info("异常捕获");
    //     if (action instanceof UnauthorizedAction) {
    //         response.setStatus(HttpConstants.UNAUTHORIZED);
    //         return "请登录";
    //     } else if (action instanceof ForbiddenAction) {
    //         response.setStatus(HttpConstants.FORBIDDEN);
    //         return "你没有权限";
    //     }
    //     return "未知异常";
    // }
    // @ExceptionHandler(value = RuntimeException.class) //@ExceptionHandler 该注解声明异常处理方法
    // public String defaultErrorHandler(HttpServletResponse resp, RuntimeException e) throws Exception {
    //     return "error";
    // }
}
