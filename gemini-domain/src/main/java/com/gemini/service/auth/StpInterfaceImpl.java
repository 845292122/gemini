package com.gemini.service.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gemini.framework.constant.Constants;
import com.gemini.service.system.menu.MenuEntity;
import com.gemini.service.system.menu.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author edison
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final MenuMapper menuMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permList = null;
        if (Constants.ADMIN_ID.equals(StpUtil.getLoginIdAsInt())) {
            LambdaQueryWrapper<MenuEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.select(MenuEntity::getPerm);
            List<MenuEntity> sysMenus = menuMapper.selectList(wrapper);
            permList = sysMenus.stream().map(MenuEntity::getPerm).collect(Collectors.toList());
        } else {
            permList = menuMapper.findPermListByUserId(String.valueOf(loginId));
        }
        return permList;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
