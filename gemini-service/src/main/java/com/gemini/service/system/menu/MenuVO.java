package com.gemini.service.system.menu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

/**
 * @author edison
 */
@Data
@NoArgsConstructor
public class MenuVO {

    public MenuVO (MenuEntity menu) {
        if (Objects.nonNull(menu)) {
            this.id = menu.getId();
            this.parentId = menu.getParentId();
            this.name = menu.getName();
            this.perm = menu.getPerm();
            this.createTime = menu.getCreateTime();
            this.updateTime = menu.getUpdateTime();
        }
    }

    private Integer id;
    private Integer parentId;
    private String name;
    private String perm;
    private Date createTime;
    private Date updateTime;
}
