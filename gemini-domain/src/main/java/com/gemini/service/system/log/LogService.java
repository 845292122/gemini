package com.gemini.service.system.log;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemini.framework.core.PageQuery;
import com.gemini.service.system.user.UserEntity;
import com.gemini.service.system.user.UserMapper;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author edison
 */
@Service
public class LogService {

    @Autowired
    LogMapper logMapper;

    @Autowired
    UserMapper userMapper;

    @Async
    public void saveLog(LogEntity log) {
        logMapper.insert(log);
    }

    public Page<LogVO> getLogList(LogVO logVO, PageQuery pageQuery) {

        MPJLambdaWrapper<LogEntity> joinWrapper = JoinWrappers.lambda(LogEntity.class)
                .selectAll(LogEntity.class)
                .selectAs(UserEntity::getUsername, LogVO::getOperName)
                .leftJoin(UserEntity.class, UserEntity::getId, LogEntity::getUserId)
                .eq(Objects.nonNull(logVO.getStatus()), LogEntity::getStatus, logVO.getStatus())
                .eq(Objects.nonNull(logVO.getOperName()), UserEntity::getUsername, logVO.getOperName())
                .orderByAsc(LogEntity::getOperTime);

        if (Objects.nonNull(logVO.getStartDate()) && Objects.nonNull(logVO.getEndDate())) {
            joinWrapper.ge(LogEntity::getOperTime, DateUtil.beginOfDay(logVO.getStartDate()))
                    .le(LogEntity::getOperTime, DateUtil.endOfDay(logVO.getEndDate()));
        }

        Page<LogVO> logVOPage = logMapper.selectJoinPage(pageQuery.build(), LogVO.class, joinWrapper);
        return logVOPage;
    }

    public List<LogVO> getAllLogList() {
        List<LogVO> logs = logMapper.getAllLogs();
        return logs;
    }
}
