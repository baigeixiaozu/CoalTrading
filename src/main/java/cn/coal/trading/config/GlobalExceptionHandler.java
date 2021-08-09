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
    public ResponseData defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e) throws Exception {
        e.printStackTrace();
        ResponseData r = new ResponseData();
        r.setError(e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            response.setStatus(404);
            r.setCode(404);
        } else {
            response.setStatus(500);
            r.setCode(500);
        }
        r.setData(null);
        return r;
    }
    // @ExceptionHandler(value = RuntimeException.class) //@ExceptionHandler 该注解声明异常处理方法
    // public String defaultErrorHandler(HttpServletResponse resp, RuntimeException e) throws Exception {
    //     return "error";
    // }
}
