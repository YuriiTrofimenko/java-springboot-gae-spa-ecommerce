package org.tyaa.java.portal.spring.boot1.gae.dao;


import org.tyaa.java.portal.spring.boot1.gae.entity.Role;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.VoidWork;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;
import org.springframework.stereotype.Repository;


/**
 *
 * @author yurii
 */
@Repository
public class RoleObjectifyDAO extends AbstractObjectifyDAO<Role> {
    
    public Role read(String _name) throws Exception {
            
        Role roleEntity = null;
        
            Role finalRoleEntity = new Role();
            ObjectifyService.run(new VoidWork() {
                @Override
                public void vrun() {
                    Role roleEntityResult =
                        ofy().load().type(Role.class)
                            .filter("name", _name)
                            .first()
                            .now();
                    if (roleEntityResult != null) {
                        CopyHelper.copy(roleEntityResult, finalRoleEntity);
                    }
                }
            });
            roleEntity = finalRoleEntity;
            
        return roleEntity;
    }
}
