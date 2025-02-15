package com.gemini.service.system.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author edison
 */
@Mapper
public interface UserMapper extends MPJBaseMapper<UserEntity> {

    @Select("SELECT menu_id FROM sys_user_menu WHERE user_id = #{userId}")
    List<Integer> getMenuIdsByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM sys_user_menu WHERE user_id = #{userId}")
    void removeMenuIdsByUserId(@Param("userId") Integer userId);

    void insertMenuIdsByUserId(@Param("userId") Integer userId, @Param("menuIds") List<Integer> menuIds);
}
