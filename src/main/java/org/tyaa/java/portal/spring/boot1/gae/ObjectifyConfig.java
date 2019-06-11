/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae;

import com.googlecode.objectify.ObjectifyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author gachechega
 */
@Configuration
public class ObjectifyConfig {
    
    @Bean
    public FilterRegistrationBean<ObjectifyFilter>
        objectifyFilterRegistration() {

        final FilterRegistrationBean<ObjectifyFilter> registration =
                new FilterRegistrationBean<>();
        registration.setFilter(new ObjectifyFilter());
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        return registration;
    }
        
    @Bean
    public ServletListenerRegistrationBean<ObjectifyListener>
        listenerRegistrationBean() {

        ServletListenerRegistrationBean<ObjectifyListener> bean
                = new ServletListenerRegistrationBean<>();
        bean.setListener(new ObjectifyListener());
        return bean;
    }
}
