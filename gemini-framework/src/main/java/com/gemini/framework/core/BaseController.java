package com.gemini.framework.core;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @author edison
 */
public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtil.parse(text));
            }
        });
    }

    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.ok() : AjaxResult.fail();
    }
}
