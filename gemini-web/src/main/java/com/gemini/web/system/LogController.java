package com.gemini.web.system;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemini.framework.core.AjaxResult;
import com.gemini.framework.core.BaseController;
import com.gemini.framework.core.PageQuery;
import com.gemini.service.system.log.LogService;
import com.gemini.service.system.log.LogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author edison
 */
@Api("操作日志模块")
@RequestMapping("system/log")
@RestController
@SaCheckPermission("system:log")
public class LogController extends BaseController {

    @Autowired
    LogService logService;

    @ApiOperation(value = "日志列表")
    @GetMapping
    public AjaxResult list(LogVO sysLogVO, PageQuery pageQuery) {
        Page<LogVO> logPage = logService.getLogList(sysLogVO, pageQuery);
        return AjaxResult.ok(logPage);
    }

    @ApiOperation(value = "日志列表导出")
    @PostMapping("export")
    public void exportList(HttpServletResponse response) throws IOException {
        List<LogVO> logList = logService.getAllLogList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "日志列表"), LogVO.class, logList);
        String fileName = "oper-log.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


}
