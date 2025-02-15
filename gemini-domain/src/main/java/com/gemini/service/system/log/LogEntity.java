package com.gemini.service.system.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author edison
 */
@Data
@NoArgsConstructor
@TableName("sys_log")
public class LogEntity {

    public LogEntity(LogVO logVO) {
        this.id = logVO.getId();
        this.userId = logVO.getUserId();
        this.title = logVO.getTitle();
        this.type = logVO.getType();
        this.typeName = logVO.getTypeName();
        this.description = logVO.getDescription();
        this.param = logVO.getParam();
        this.result = logVO.getResult();
        this.status = logVO.getStatus();
        this.operTime = logVO.getOperTime();
        this.costTime = logVO.getCostTime();
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("title")
    private String title;

    @TableField("type")
    private Integer type;

    @TableField("type_name")
    private String typeName;

    @TableField("description")
    private String description;

    @TableField("param")
    private String param;

    @TableField("result")
    private String result;

    @TableField("status")
    private Integer status;

    @TableField("oper_time")
    private Date operTime;

    @TableField("cost_time")
    private Long costTime;
}
