package com.gemini.service.system.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author edison
 */
@Data
@NoArgsConstructor
public class UserVO {

    public UserVO(UserEntity user) {
        if (Objects.nonNull(user)) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.nickname = user.getNickname();
            this.avatar = user.getAvatar();
            this.mobile = user.getMobile();
            this.email = user.getEmail();
            this.status = user.getStatus();
            this.createTime = user.getCreateTime();
            this.updateTime = user.getUpdateTime();
        }
    }

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String mobile;
    private String email;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private List<Integer> menuIds;
}
