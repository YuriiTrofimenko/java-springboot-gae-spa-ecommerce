package org.tyaa.java.portal.spring.boot1.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import org.springframework.stereotype.Repository;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;
import static com.googlecode.objectify.ObjectifyService.ofy;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;

@Repository
public class ProductHibernateDAO extends AbstractDAO<Product> {
    
    public Product read(String _name) throws Exception {
            
        Product productEntity = null;
        
            Product finalProductEntity = new Product();
            ObjectifyService.run(new VoidWork() {
                @Override
                public void vrun() {
                    Product productEntityResult =
                        ofy().load().type(Product.class)
                            .filter("name", _name)
                            .first()
                            .now();
                    if (productEntityResult != null) {
                        CopyHelper.copy(productEntityResult, finalProductEntity);
                    }
                }
            });
            /*if (true) {
                throw new Exception("test ex");
            }*/
            productEntity = finalProductEntity;
            
        return productEntity;
    }
}