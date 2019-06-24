/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.security;

/**
 *
 * @author gachechega
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author yurii
 */
@Configuration
@EnableWebSecurity
@Order(1)
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private ObjectifyWebAuthProvider authProvider;
    
    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }*/
    
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    @Autowired
    private SavedReqAwareAuthSuccessHandler savedReqAwareAuthSuccessHandler;
    
    /* @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    } */

    //@Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
    
    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        // .formLogin().disable()
        // .headers().frameOptions().disable().and()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .authorizeRequests()
        //.anyRequest().permitAll()
        .antMatchers("/api/user/").permitAll()
        .antMatchers("/api/product/").permitAll()
        .antMatchers("/api/category/").permitAll()
        .antMatchers("/api/user/delete").authenticated()
        .antMatchers("/api/user/check").permitAll()
        .antMatchers("/api/role/**").permitAll()//.hasRole("admin")
        .antMatchers("/api/init/**").permitAll()
        .antMatchers("/api/feedback/**").authenticated()
       // .antMatchers("/public/**").permitAll()
        .antMatchers("/share/**").permitAll()
        .antMatchers("/admin/**").hasRole("admin")
        .antMatchers("/api/admin/**").hasRole("admin")
        .and()
        .formLogin()//.loginPage("/login").permitAll()
        .successHandler(savedReqAwareAuthSuccessHandler)
        .failureUrl("/api/user/onerror")
        .and()
        .logout()
        //.logoutUrl("/logout");
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/api/user/onsignout");
        // .failureHandler(myFailureHandler)
        //.and()
        //.logout().permitAll();
        
        // Testing auth demo
        // 1. https://java-springboot-gae-ecommerce.appspot.com/login (POST)
        //username=user2&password=2 - admin
        //username=user1&password=1 - user
        // 2. https://javaportalspringboot1.appspot.com/public/testpublic.html (GET)
        // 3. https://javaportalspringboot1.appspot.com/admin/testadmin.html (GET)
        // 4. https://javaportalspringboot1.appspot.com/api/user (GET)
        // 5. https://javaportalspringboot1.appspot.com/api/role (GET)
        // 6. https://javaportalspringboot1.appspot.com/logout (GET)
        // 7. TODO Get current user, user cart works
    }
    
    
}
