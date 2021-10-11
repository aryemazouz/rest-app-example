package com.rest.app;


import com.rest.app.model.Stats;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@Aspect
@Component
public class ExecutionTimeHandler{

    @Autowired
    private StatsService statsService;

    @Around("@annotation(com.rest.app.StatsExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long start = System.nanoTime();

        //
        //Currently doesn't handle exception failure
        Object object = point.proceed();

        long end = System.nanoTime();
        long duration = end - start;

        statsService.addRequest(duration);

        log.trace("Class: {}, Method: {}, Start: {}, End: {}, Duration: {}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), start, end, duration);
        return object;
    }
}
