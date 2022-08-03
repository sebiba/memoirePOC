package com.example.testspring.dbAccess;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class DBManagerAspect {
    private static final Logger logger = Logger.getLogger(DBManagerAspect.class.getName());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Pointcut("target(com.example.testspring.dbAccess.DBManager)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void logMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Map<String, Object> parameters = getParameters(joinPoint);
        try {
            if(parameters.size()>0){
                logger.info(formatter.format(new Date())+" before:\t"+signature.getMethod().getName()+"\t"+ parameters);
            }else{
                logger.info(formatter.format(new Date())+" before:\t"+signature.getMethod().getName());
            }
        } catch (NullPointerException e){
            logger.warn(e.getMessage());
        }
    }

    @After("pointcut()")
    public void logMethodAfter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            logger.info(formatter.format(new Date())+" after:\t"+signature.getMethod().getName());
        } catch (NullPointerException e){
            logger.warn(e.getMessage());
        }
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "error")
    public void test(JoinPoint joinPoint, Throwable error){
        logger.error(formatter.format(new Date())+" Exception in "+
            joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+
            "() with cause = "+(error.getCause() != null ? error.getCause() : "NULL"));
    }
    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        HashMap<String, Object> map = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        return map;
    }

}
