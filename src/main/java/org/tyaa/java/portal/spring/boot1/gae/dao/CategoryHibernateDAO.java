package org.tyaa.java.portal.spring.boot1.gae.dao;


import org.tyaa.java.portal.spring.boot1.gae.entity.Role;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.VoidWork;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;
import org.springframework.stereotype.Repository;
import org.tyaa.java.portal.spring.boot1.gae.entity.Category;


/**
 *
 * @author yurii
 */
@Repository
public class CategoryHibernateDAO extends AbstractDAO<Category> {
    
    public Category read(String _name) throws Exception {
            
        Category categoryEntity = null;
        
            Category finalCategoryEntity = new Category();
            ObjectifyService.run(new VoidWork() {
                @Override
                public void vrun() {
                    Category categoryEntityResult =
                        ofy().load().type(Category.class)
                            .filter("name", _name)
                            .first()
                            .now();
                    if (categoryEntityResult != null) {
                        CopyHelper.copy(categoryEntityResult, finalCategoryEntity);
                    }
                }
            });
            categoryEntity = finalCategoryEntity;
            
        return categoryEntity;
    }

    public void create(Category category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}