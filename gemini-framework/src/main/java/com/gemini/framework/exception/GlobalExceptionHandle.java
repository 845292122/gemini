package com.gemini.framework.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.gemini.framework.core.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author edison
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    /**
     * 未登录
     */
    @ExceptionHandler(NotLoginException.class)
    public AjaxResult handleNotLoginException(NotLoginException e) {
        log.error("登录凭证过期: {}", e.getMessage());
        return AjaxResult.fail(HttpStatus.UNAUTHORIZED.value(), "登录凭证过期.");
    }

    /**
     * 角色权限异常
     */
    @ExceptionHandler(NotRoleException.class)
    public AjaxResult handleNotRoleException(NotRoleException e) {
        log.error("角色权限异常: {}", e.getMessage());
        return AjaxResult.fail(HttpStatus.FORBIDDEN.value(), "权限不足,请联系管理员");
    }

    /**
     * 操作权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public AjaxResult handleNotPermissionException(NotPermissionException e) {
        log.error("操作权限异常: {}", e.getMessage());
        return AjaxResult.fail(HttpStatus.FORBIDDEN.value(), "权限不足,请联系管理员");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        log.error("请求地址'{}',不支持'{}'请求", request.getRequestURI(), e.getMethod());
        return AjaxResult.fail(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public AjaxResult handleServiceException(BizException e) {
        return AjaxResult.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.fail(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String errorMsg = String.format("请求地址'%s',发生未知异常.", request.getRequestURI());
        log.error(errorMsg, e);
        return AjaxResult.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult unknownException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.fail(e.getMessage());
    }
}
