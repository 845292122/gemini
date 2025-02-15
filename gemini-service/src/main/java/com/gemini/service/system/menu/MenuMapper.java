package com.gemini.service.system.menu;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author edison
 */
@Mapper
public interface MenuMapper extends MPJBaseMapper<MenuEntity> {

    List<String> findPermListByUserId(@Param("userId") String userId);

    List<MenuEntity> findMenuListByUserId(@Param("userId") String userId);
}
