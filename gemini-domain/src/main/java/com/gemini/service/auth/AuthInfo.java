package com.gemini.service.auth;

import com.gemini.service.system.menu.MenuEntity;
import com.gemini.service.system.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edison
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfo implements Serializable {

    public AuthInfo (UserEntity user, List<MenuEntity> menus) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.status = user.getStatus();
        this.avatar = user.getAvatar();
        this.menus = menus;
    }

    private Integer userId;
    private String nickname;
    private String username;
    private String avatar;
    private String email;
    private String mobile;
    private Integer status;
    private List<MenuEntity> menus;
}
