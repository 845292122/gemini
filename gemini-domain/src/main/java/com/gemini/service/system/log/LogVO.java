package com.gemini.service.system.log;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author edison
 */
@Data
@NoArgsConstructor
@ExcelTarget("operLog")
public class LogVO implements Serializable {

    public LogVO(LogEntity log) {
        this.id = log.getId();
        this.userId = log.getUserId();
        this.title = log.getTitle();
        this.type = log.getType();
        this.typeName = log.getTypeName();
        this.description = log.getDescription();
        this.param = log.getParam();
        this.result = log.getResult();
        this.status = log.getStatus();
        this.operTime = log.getOperTime();
        this.costTime = log.getCostTime();
    }

    @Excel(name = "日志编号", orderNum = "1")
    private Integer id;

    @ExcelIgnore
    private Integer userId;

    @Excel(name = "模块标题", orderNum = "2")
    private String title;

    @ExcelIgnore
    private Integer type;

    @Excel(name = "操作类型", orderNum = "3")
    private String typeName;

    @Excel(name = "操作描述", orderNum = "4")
    private String description;

    @ExcelIgnore
    private String param;

    @ExcelIgnore
    private String result;

    @Excel(name = "操作状态", orderNum = "5", replace = { "成功_1", "失败_0" })
    private Integer status;

    @Excel(name = "操作时间", orderNum = "6", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    @Excel(name = "消耗时间(ms)", orderNum = "7")
    private Long costTime;

    @Excel(name = "操作人员", orderNum = "8")
    private String operName;

    @ExcelIgnore
    private Date startDate;

    @ExcelIgnore
    private Date endDate;
}
