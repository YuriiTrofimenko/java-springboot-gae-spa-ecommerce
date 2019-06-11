package org.tyaa.java.portal.spring.boot1.gae.entity;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import java.io.Serializable;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor
public class Product implements Serializable {

    @Id
    private Long id;
    @Index
    private String name;
    @Load
    @Index
    Ref<Category> category;

    public Product(String name, Category category) {
        this.name = name;
        setCategory(category);
    }

    public Product(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        setCategory(category);
    }

    public Product(String name, Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public final Category getCategory() {
        Category category = new Category();
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                Category categoryLocal = Product.this.category.get();
                if (categoryLocal != null) {
                    CopyHelper.copy(categoryLocal, category);
                }
            }
        });
        return category;
    }

    // @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public final void setCategory(Category category) {
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                Product.this.category = Ref.create(category);
            }
        });
    }
}
