/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tyaa.java.portal.spring.boot1.gae.utils.ErrorsGetter;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;

/**
 *
 * @author gachechega
 */
@Configuration
@Aspect
public class ExceptionLogger {
    
    protected static Logger logger;

    public ExceptionLogger(){
        logger =
            Logger.getLogger(ExceptionLogger.class.getName());
    }

    @Around("execution(* org.tyaa.java.portal.spring.boot1.gae.dao.*.*(..))")
    public Object daoExceptionLog(ProceedingJoinPoint pjp) throws Throwable {
        Object output = null;
        try {
            output = pjp.proceed();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ErrorsGetter.printException(ex));
            throw ex;
        }
        return output;
    }

    @Around("execution(* org.tyaa.java.portal.spring.boot1.gae.service.*.*(..))")
    public Object serviceExceptionLog(ProceedingJoinPoint pjp) throws Throwable {
        Object output = null;
        try {
            output = pjp.proceed();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ErrorsGetter.printException(ex));
            output =
                new JsonHttpResponse(
                    JsonHttpResponse.errorStatus
                    , ErrorsGetter.printException(ex)
                );
        }
        return output;
    }
}
