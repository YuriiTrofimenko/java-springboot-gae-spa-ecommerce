package org.tyaa.java.portal.spring.boot1.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.VoidWork;
import org.springframework.stereotype.Repository;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;
import com.googlecode.objectify.cmd.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;
import org.tyaa.java.portal.spring.boot1.gae.model.ProductFilter;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;

@Repository
public class ProductObjectifyDAO extends AbstractObjectifyDAO<Product> {
    
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

    public List<Product> getFiltered(ProductFilter filter) {
        
        // Get all products
        final List<Product> filteredProducts = new ArrayList<>();
        
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                
                Query<Product> query =
                    ofy().load().type(Product.class);
                // Add order products rules
                if (filter.getSort() == ProductFilter.OrderBy.sortPriceAsc) {

                    query = query.order("price");
                } else if (filter.getSort() == ProductFilter.OrderBy.sortPriceDesc) {

                    query = query.order("-price");
                }
                
                List<Product> filteredProductsResult = query.list();
                if (filteredProductsResult != null) {
                    filteredProducts.addAll(filteredProductsResult);
                }
            }
        });
        
        
        // Filter products by category ids
        if(filter.getCategories() != null && filter.getCategories().length > 0) {

            // query = query.filter("created_at >=", offerFilter.createdDateFrom);
            List<Long> categoryIdsList =
                Arrays.asList(filter.getCategories());
            filteredProducts.removeIf((p) -> {

                boolean categoryIdAbsents = true;
                for (Long categoryId : categoryIdsList) {
                        if(((Product)p).getCategory().getId().equals(categoryId)) {
                                categoryIdAbsents = false;
                                break;
                        }
                }
                return categoryIdAbsents;
            });
        }
        //***
        
        return filteredProducts;
    }
}