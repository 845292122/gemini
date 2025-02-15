package com.gemini.service.system.user;

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
@TableName("sys_user")
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    public UserEntity (UserVO userVO) {
        if (Objects.nonNull(userVO)) {
            this.id = userVO.getId();
            this.username = userVO.getUsername();
            this.password = userVO.getPassword();
            this.nickname = userVO.getNickname();
            this.avatar = userVO.getAvatar();
            this.mobile = userVO.getMobile();
            this.email = userVO.getEmail();
            this.status = userVO.getStatus();
        }
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("avatar")
    private String avatar;

    @TableField("mobile")
    private String mobile;

    @TableField("email")
    private String email;

    @TableField("status")
    private Integer status;
}
