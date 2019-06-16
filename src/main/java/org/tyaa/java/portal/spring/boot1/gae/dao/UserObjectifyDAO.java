package org.tyaa.java.portal.spring.boot1.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import org.springframework.stereotype.Repository;
import org.tyaa.java.portal.spring.boot1.gae.entity.User;
import static com.googlecode.objectify.ObjectifyService.ofy;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;

@Repository
public class UserObjectifyDAO extends AbstractObjectifyDAO<User> {
    
    public User read(String _name) throws Exception {
            
        User userEntity = null;
        
            User finalUserEntity = new User();
            ObjectifyService.run(new VoidWork() {
                @Override
                public void vrun() {
                    User userEntityResult =
                        ofy().load().type(User.class)
                            .filter("name", _name)
                            .first()
                            .now();
                    if (userEntityResult != null) {
                        CopyHelper.copy(userEntityResult, finalUserEntity);
                    }
                }
            });
            /*if (true) {
                throw new Exception("test ex");
            }*/
            userEntity = finalUserEntity;
            
        return userEntity;
    }
}