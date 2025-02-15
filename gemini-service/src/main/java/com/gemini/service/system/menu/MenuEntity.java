package com.gemini.service.system.menu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gemini.framework.core.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author edison
 */
@Setter
@Getter
@TableName("sys_menu")
@NoArgsConstructor
public class MenuEntity extends BaseEntity {

    public MenuEntity(MenuVO menuVO) {
        if (Objects.nonNull(menuVO)) {
            this.id = menuVO.getId();
            this.parentId = menuVO.getParentId();
            this.name = menuVO.getName();
            this.perm = menuVO.getPerm();
        }
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("name")
    private String name;

    @TableField("perm")
    private String perm;
}
