package com.gemini.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author edison
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private int status;
    private String description;
}
