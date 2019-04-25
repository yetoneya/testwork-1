package ru.elena.zh.testwork.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static Logger logger = Logger.getLogger(LoggingAspect.class.getName());
       
    @AfterReturning(pointcut = "execution(* ru.elena.zh.testwork.service.StoreService.*(..))", returning = "result")
    public void logAfterreturning(JoinPoint joinPoint, Object result) {        
        logger.info(joinPoint + " : " + result);
    }
}
