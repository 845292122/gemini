package com.gemini.web.common;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.gemini.framework.core.AjaxResult;
import com.gemini.framework.exception.BizException;
import com.gemini.service.auth.AuthInfo;
import com.gemini.service.auth.AuthService;
import com.gemini.service.auth.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author edison
 */
@Api("认证模块")
@RequestMapping("auth")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public AjaxResult login(@RequestBody LoginVO loginVO) {
        if (StrUtil.isBlank(loginVO.getUsername())) {
            throw new BizException("用户名不能为空");
        }
        if (StrUtil.isBlank(loginVO.getPassword())) {
            throw new BizException("密码不能为空");
        }
        authService.login(loginVO);
        return AjaxResult.ok().put("token", StpUtil.getTokenValue());
    }

    @ApiOperation(value = "认证信息")
    @GetMapping("info")
    public AjaxResult info() {
        AuthInfo authInfo = authService.getAuthInfo();
        return AjaxResult.ok(authInfo);
    }

    @ApiOperation(value = "注销")
    @PostMapping("logout")
    public void logout() {
        authService.logout();
    }

}
