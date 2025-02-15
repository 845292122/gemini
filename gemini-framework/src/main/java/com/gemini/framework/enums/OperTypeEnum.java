package com.gemini.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author edison
 */
@Getter
@AllArgsConstructor
public enum OperTypeEnum {

    ADD(1, "新增"),
    EDIT(2, "编辑"),
    REMOVE(3, "删除"),
    QUERY(4, "查询"),
    LOGIN(5, "登录"),
    OTHER(0, "其它");

    private int value;
    private String description;
}
