package com.gemini.service.auth;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gemini.framework.constant.Constants;
import com.gemini.framework.enums.UserStatusEnum;
import com.gemini.framework.exception.BizException;
import com.gemini.service.system.menu.MenuEntity;
import com.gemini.service.system.menu.MenuMapper;
import com.gemini.service.system.user.UserEntity;
import com.gemini.service.system.user.UserMapper;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author edison
 */
@Service
@Slf4j
public class AuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    @Qualifier("authInfoCache")
    Cache<String, Object> authInfoCache;

    /**
     * 登录
     * @param loginVO
     */
    public void login(LoginVO loginVO) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, loginVO.getUsername());
        UserEntity user = userMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            log.warn("登录账号'{}'不存在", loginVO.getUsername());
            throw new BizException("账号或密码不正确");
        }
        if (UserStatusEnum.DISABLE.getStatus() == user.getStatus()) {
            log.warn("登录账号'{}'已被锁定", loginVO.getUsername());
            throw new BizException("账号已被禁用");
        }
        if (!BCrypt.checkpw(loginVO.getPassword(), user.getPassword())) {
            throw new BizException("账号或密码不正确");
        }
        StpUtil.login(user.getId());
        List<MenuEntity> menuList = null;
        if (Constants.ADMIN_ID.equals(user.getId())) {
            menuList = menuMapper.selectList(null);
        } else {
            menuList = menuMapper.findMenuListByUserId(StpUtil.getLoginIdAsString());
        }
        AuthInfo authInfo = new AuthInfo(user, menuList);
        authInfoCache.put(StpUtil.getLoginIdAsString(), authInfo);
    }

    /**
     * 获取用户信息
     * @return
     */
    public AuthInfo getAuthInfo() {
        String loginId = StpUtil.getLoginIdAsString();
        AuthInfo authInfo = (AuthInfo) authInfoCache.asMap().get(loginId);
        if (Objects.isNull(authInfo)) {
            UserEntity user = userMapper.selectById(loginId);
            List<MenuEntity> menuList = menuMapper.findMenuListByUserId(loginId);
            authInfo = new AuthInfo(user, menuList);
            authInfoCache.put(StpUtil.getLoginIdAsString(), authInfo);
        }
        return authInfo;
    }

    /**
     * 注销
     */
    @Transactional
    public void logout() {
        String loginId = StpUtil.getLoginIdAsString();
        StpUtil.logout();
        authInfoCache.asMap().remove(loginId);
    }
}
