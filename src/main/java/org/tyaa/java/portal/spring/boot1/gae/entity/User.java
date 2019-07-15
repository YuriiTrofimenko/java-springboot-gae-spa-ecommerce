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
public class User implements Serializable {

    @Id
    private Long id;
    @Index
    private String name;
    private String password;
    private String mail;
    @Load
    @Index
    Ref<Role> role;

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        setRole(role);
    }

    public User(Long id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.mail = mail;
        setRole(role);
    }
    
   
    public User(String name, String password, String mail, Role role) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        setRole(role);
    }

    public User(String name) {
        this.name = name;    }

    // @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public final Role getRole() {
        Role role = new Role();
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                Role roleLocal = User.this.role.get();
                if (roleLocal != null) {
                    CopyHelper.copy(roleLocal, role);
                }
            }
        });
        return role;
    }

    // @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public final void setRole(Role role) {
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                User.this.role = Ref.create(role);
            }
        });
    }
    
    
}
