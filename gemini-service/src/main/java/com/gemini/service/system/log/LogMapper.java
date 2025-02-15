package com.gemini.service.system.log;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author edison
 */
@Mapper
public interface LogMapper extends MPJBaseMapper<LogEntity> {

    List<LogVO> getAllLogs();
}
