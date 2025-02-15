package com.gemini.annotations.log;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.gemini.framework.enums.OperTypeEnum;
import com.gemini.service.system.log.LogEntity;
import com.gemini.service.system.log.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @author edison
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class OperLogAspect {

    private final LogService logService;

    private static final ThreadLocal<StopWatch> TIME_THREADLOCAL = new TransmittableThreadLocal();

    @Before(value = "@annotation(operLog)")
    public void doBefore(JoinPoint joinPoint, OperLog operLog) {
        StopWatch stopWatch = new StopWatch();
        TIME_THREADLOCAL.set(stopWatch);
        stopWatch.start();
    }

    @AfterReturning(pointcut = "@annotation(operLog)", returning = "jsonResult")
    public void doAfterRunning(JoinPoint joinPoint, OperLog operLog, Object jsonResult) {
        handleLog(joinPoint, operLog, null, jsonResult);
    }

    @AfterThrowing(value = "@annotation(operLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperLog operLog, Exception e) {
        handleLog(joinPoint, operLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, OperLog operLog, final Exception e, Object jsonResult) {
        try {
            LogEntity log = new LogEntity();
            int userId = StpUtil.getLoginIdAsInt();
            String title = operLog.title();
            OperTypeEnum type = operLog.type();
            String desc = operLog.desc();
            Object[] args = joinPoint.getArgs();
            StringBuffer requestArgs = new StringBuffer();
            for (Object arg : args) {
                if (requestArgs.length() + String.valueOf(arg).length() < 2000) {
                    requestArgs.append(arg);
                }
            }

            StopWatch stopWatch = TIME_THREADLOCAL.get();
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();

            log.setUserId(userId);
            log.setTitle(title);
            log.setType(type.getValue());
            log.setTypeName(type.getDescription());
            log.setDescription(desc);
            log.setOperTime(new Date());
            log.setParam(requestArgs.toString());
            log.setCostTime(totalTimeMillis);
            if (e != null) {
                log.setStatus(0);
                log.setResult(StrUtil.sub(e.getMessage(), 0, 2000));
            } else {
                log.setStatus(1);
                log.setResult(jsonResult.toString());
            }
            logService.saveLog(log);

        } catch (Exception exp) {
            log.error("异常信息: {}", exp.getMessage());
            exp.printStackTrace();
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

}
