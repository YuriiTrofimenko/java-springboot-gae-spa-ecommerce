/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.entity;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;

/**
 *
 * @author gachechega
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscription implements Serializable {

    @Id
    private Long id;
    @Load
    @Index
    private Boolean subscription;
    Ref<User> user;

    public Subscription(Long id, Boolean subscription, Ref<User> user) {
        this.id = id;
        this.subscription = subscription;
        this.user = user;
    }

    public Subscription(User user) {
        subscription = true;
        setUser(user);
    }

    public Subscription(Boolean subscription ,User user) {
        this.subscription = subscription;
        setUser(user);
    }
    // Метод получения из хранилища обїекта типа user
    // по его идентификатору
    public User getUser() {
        User user = new User();
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                User userLocal = Subscription.this.user.get();
                if (userLocal != null) {
                    CopyHelper.copy(userLocal, user);
                }
            }
        });
        return user;
    }

    // @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    // Метод на входе принимает обїект сущности User
    // и в поле user записівает его идентификатор
    public void setUser(User user) {
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                Subscription.this.user = Ref.create(user);
            }
        });
}

}
