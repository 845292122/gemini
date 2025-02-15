package com.gemini.framework.core;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Objects;

/**
 * ajax结果类
 * @author edison
 */
@NoArgsConstructor
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE = "code";

    /** 返回内容 */
    public static final String MSG = "msg";

    /** 数据对象 */
    public static final String DATA = "data";

    public AjaxResult(int code, String msg) {
        super.put(CODE, code);
        super.put(MSG, msg);
    }

    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE, code);
        super.put(MSG, msg);
        if (Objects.nonNull(data)) {
            super.put(DATA, data);
        }
    }

    public static AjaxResult ok() {
        return AjaxResult.ok("操作成功");
    }

    public static AjaxResult ok(Object data) {
        return AjaxResult.ok("操作成功", data);
    }

    public static AjaxResult ok(String msg) {
        return AjaxResult.ok(msg, null);
    }

    public static AjaxResult ok(String msg, Object data) {
        return new AjaxResult(HttpStatus.OK.value(), msg, data);
    }
    
    public static AjaxResult fail() {
        return AjaxResult.fail("操作失败");
    }

    public static AjaxResult fail(String msg) {
        return AjaxResult.fail(msg, null);
    }

    public static AjaxResult fail(String msg, Object data) {
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static AjaxResult fail(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
