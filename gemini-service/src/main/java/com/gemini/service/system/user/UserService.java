package com.gemini.service.system.user;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemini.framework.constant.Constants;
import com.gemini.framework.core.PageQuery;
import com.gemini.framework.exception.BizException;
import com.github.benmanes.caffeine.cache.Cache;
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
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    @Qualifier("authInfoCache")
    Cache<String, Object> authInfoCache;

    private LambdaQueryWrapper<UserEntity> buildQueryWrapper(UserVO userVO) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.select(
                UserEntity::getId,
                UserEntity::getUsername,
                UserEntity::getNickname,
                UserEntity::getAvatar,
                UserEntity::getMobile,
                UserEntity::getEmail,
                UserEntity::getStatus,
                UserEntity::getCreateTime,
                UserEntity::getUpdateTime);
        wrapper.like(StrUtil.isNotBlank(userVO.getUsername()), UserEntity::getUsername, userVO.getUsername())
                .eq(Objects.nonNull(userVO.getStatus()), UserEntity::getStatus, userVO.getStatus())
                .orderByAsc(UserEntity::getId);
        return wrapper;
    }

    public Boolean validateUserUnique(UserVO userVO) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getUsername, userVO.getUsername())
                .ne(Objects.nonNull(userVO.getId()), UserEntity::getId, userVO.getId());
        boolean exists = userMapper.exists(wrapper);
        return !exists;
    }

    @Transactional
    public int insertUser(UserVO userVO) {
        UserEntity user = new UserEntity(userVO);
        user.setPassword(BCrypt.hashpw(Constants.INIT_PASSWORD));
        int count = userMapper.insert(user);
        if (count > 0) {
            resetUserMenu(user.getId(), userVO.getMenuIds());
        }
        return count;
    }

    @Transactional
    public int modifyUser(UserVO userVO) {
        UserEntity user = new UserEntity(userVO);
        resetUserMenu(userVO.getId(), userVO.getMenuIds());
        return userMapper.updateById(user);
    }

    public int removeUser(Long userId) {
        // todo 验证账户是否可以删除
        return userMapper.deleteById(userId);
    }

    public Page<UserEntity> getPageRecord(UserVO userVO, PageQuery pageQuery) {
        return userMapper.selectPage(pageQuery.build(), this.buildQueryWrapper(userVO));
    }

    public UserVO getUserInfo(Long userId) {
        UserEntity user = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            throw new BizException("用户不存在~~");
        }
        List<Integer> menuIds = userMapper.getMenuIdsByUserId(user.getId());
        UserVO userVO = new UserVO(user);
        userVO.setMenuIds(menuIds);
        return userVO;
    }

    public void resetUserMenu(Integer userId, List<Integer> menuIds) {
        userMapper.removeMenuIdsByUserId(userId);
        if (CollectionUtil.isNotEmpty(menuIds)) {
            userMapper.insertMenuIdsByUserId(userId, menuIds);
        }
    }

    public int changeInfo(UserVO userVO) {
        verifyCurrentUser(userVO.getId().toString());
        UserEntity user = new UserEntity(userVO);
        authInfoCache.asMap().remove(StpUtil.getLoginIdAsString());
        return userMapper.updateById(user);
    }

    public int changePwd(Integer id, String oldPwd, String newPwd) {
        verifyCurrentUser(String.valueOf(id));
        LambdaQueryWrapper<UserEntity> query = Wrappers.lambdaQuery();
        query.select(UserEntity::getPassword).eq(UserEntity::getId, id);
        UserEntity user = userMapper.selectOne(query);
        if (!BCrypt.checkpw(oldPwd, user.getPassword())) {
            throw new BizException("旧密码不正确!");
        }
        String hashpw = BCrypt.hashpw(newPwd);
        UserEntity newUser = new UserEntity();
        newUser.setId(id);
        newUser.setPassword(hashpw);
        return userMapper.updateById(newUser);
    }

    private void verifyCurrentUser(String id) {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        if (!StrUtil.equals(loginIdAsString, id)) {
            throw new BizException("验证用户错误");
        }
    }

    public int updateAvatar(String fullName) {
        int userId = StpUtil.getLoginIdAsInt();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setAvatar(fullName);
        return userMapper.updateById(userEntity);
    }
}
