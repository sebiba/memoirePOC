package com.example.testspring.dbAccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class dbManagerAspect {
    private static final Logger logger = Logger.getLogger(dbManagerAspect.class.getName());

    @Autowired
    private ObjectMapper mapper;

    @Pointcut("target(com.example.testspring.dbAccess.dbManager)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void logMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);

        Map<String, Object> parameters = getParameters(joinPoint);

        try {
            logger.info(signature.getMethod().getName());
        } catch (NullPointerException e){
            logger.warn(e.getMessage());
        }
    }

    @AfterReturning(pointcut = "pointcut()", returning = "entity")
    public void logMethodAfter(JoinPoint joinPoint, ResponseEntity<?> entity) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);

        try {
            logger.info(signature.getMethod().getName());
        } catch (NullPointerException e){
            logger.warn(e.getMessage());
        }
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
