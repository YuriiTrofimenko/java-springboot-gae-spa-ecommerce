package org.tyaa.java.portal.spring.boot1.gae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan({
    "org.tyaa.java.portal.spring.boot1.gae.controller"
        , "org.tyaa.java.portal.spring.boot1.gae.service"
        , "org.tyaa.java.portal.spring.boot1.gae.dao"
        , "org.tyaa.java.portal.spring.boot1.gae.aspect"
        , "org.tyaa.java.portal.spring.boot1.gae.security"
})
@EnableAspectJAutoProxy
public class JavaPortalSpringBoot1Application {

	public static void main(String[] args) {
		SpringApplication.run(JavaPortalSpringBoot1Application.class, args);
	}

}
