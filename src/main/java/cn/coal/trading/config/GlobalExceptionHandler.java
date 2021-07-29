package cn.coal.trading.config;

import cn.coal.trading.bean.ResponseData;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.exception.http.ForbiddenAction;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.UnauthorizedAction;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseData httpCodeException(HttpServletResponse response, HttpAction action) {
        ResponseData r = new ResponseData(){{
            setStatus(false);
            setMsg("fail");
        }};
        if (action instanceof UnauthorizedAction) {
            response.setStatus(HttpConstants.UNAUTHORIZED);
            r.setCode(401);
            r.setError("未登录");
        } else if (action instanceof ForbiddenAction) {
            response.setStatus(HttpConstants.FORBIDDEN);
            r.setCode(403);
            r.setError("你没有权限");
        }else{
            r.setCode(500);
            r.setError("未知异常");
        }

        return r;
    }
    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        ResponseData r = new ResponseData();
        r.setError(e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            r.setCode(404);
        } else {
            r.setCode(500);
        }
        r.setData(null);
        r.setStatus(false);
        return r;
    }
    // @ExceptionHandler(value = RuntimeException.class) //@ExceptionHandler 该注解声明异常处理方法
    // public String defaultErrorHandler(HttpServletResponse resp, RuntimeException e) throws Exception {
    //     return "error";
    // }
}
