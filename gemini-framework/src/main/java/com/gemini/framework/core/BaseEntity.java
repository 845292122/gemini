package com.gemini.framework.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 * 
 * @author edison
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 创建时间
     */
    @TableField(value = "createAt", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateAt", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 逻辑删除(0:未删除 1:已删除)
     */
    @TableLogic
    @TableField(value = "delFlag")
    private Integer delFlag;
}
