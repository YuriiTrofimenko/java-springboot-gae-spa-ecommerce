/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import org.tyaa.java.portal.spring.boot1.gae.entity.Role;
import org.tyaa.java.portal.spring.boot1.gae.entity.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.tyaa.java.portal.spring.boot1.gae.entity.Category;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;
import org.tyaa.java.portal.spring.boot1.gae.utils.ErrorsGetter;

/**
 *
 * @author gachechega
 */
@WebListener
public class ObjectifyListener implements ServletContextListener{
    
    private static final Logger log =
            Logger.getLogger(ObjectifyListener.class.getName());
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            if (System.getenv("SPRING_PROFILES_ACTIVE") == null) {
                // local without memcache (gradle bootRun)
                log.info("ObjectifyService.init - 1");
                ObjectifyService.init(new ObjectifyFactory(
                        DatastoreOptions.newBuilder()
                                .setHost("http://localhost:8484")
                                .setProjectId("springgaedatastore")
                                .build()
                                .getService()
                ));
            }
            else {
                // on appengine
                log.info("ObjectifyService.init - 1-1");
                ObjectifyService.init(new ObjectifyFactory(
                        DatastoreOptions.getDefaultInstance().getService()
                ));
                // ObjectifyService.init();
            }
        } catch (Exception ex) {
            log.log(Level.SEVERE, ErrorsGetter.printException(ex));
        }
        log.info("ObjectifyService.init - 2");
        ObjectifyService.register(User.class);
        log.info("ObjectifyService.init - 3");
        ObjectifyService.register(Role.class);
        log.info("ObjectifyService.init - 4");
        ObjectifyService.register(Product.class);
        log.info("ObjectifyService.init - 5");
        ObjectifyService.register(Category.class);
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
