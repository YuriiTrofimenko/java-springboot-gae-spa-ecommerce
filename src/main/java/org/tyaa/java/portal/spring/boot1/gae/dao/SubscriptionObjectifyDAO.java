/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.VoidWork;
import org.springframework.stereotype.Repository;
import org.tyaa.java.portal.spring.boot1.gae.entity.Subscription;
import org.tyaa.java.portal.spring.boot1.gae.entity.User;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.VoidWork;
import org.springframework.stereotype.Repository;
import org.tyaa.java.portal.spring.boot1.gae.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;

/**
 *
 * @author gachechega
 */
@Repository
public class SubscriptionObjectifyDAO extends AbstractObjectifyDAO<Subscription> {
    // Метод получения из базы списка всех объектов подписок,
    //у которых поле subscription равно true
    public List<Subscription> readActive() throws Exception {
        // Создали пустой список для объектов Subscription
        List<Subscription> subscriptions = new ArrayList<>();
        // Запустили специальный поток для работы с БД
        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                // Попробовали получить список
                //всех объектов подписок,
                //у которых поле subscription равно true
                List<Subscription> subscriptionsResult =
                    ofy().load()
                        .type(Subscription.class)
                        .filter("subscription", true)
                        .list();
                // Если удалось - перекладываем объекты подписок
                // во внешний список
                if (subscriptionsResult != null) {
                    subscriptions.addAll(subscriptionsResult);
                }
            }
        });
        return subscriptions;
    }
}
