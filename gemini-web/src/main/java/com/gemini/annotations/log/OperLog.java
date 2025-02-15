package com.gemini.annotations.log;

import com.gemini.framework.enums.OperTypeEnum;

import java.lang.annotation.*;

/**
 * @author edison
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    String title() default "";

    OperTypeEnum type() default OperTypeEnum.OTHER;

    String desc() default "";
}
