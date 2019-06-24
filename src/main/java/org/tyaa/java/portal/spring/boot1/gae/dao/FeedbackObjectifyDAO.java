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
import org.tyaa.java.portal.spring.boot1.gae.entity.Category;
import org.tyaa.java.portal.spring.boot1.gae.entity.Feedback;
import org.tyaa.java.portal.spring.boot1.gae.utils.CopyHelper;

/**
 *
 * @author gachechega
 */
@Repository
public class FeedbackObjectifyDAO extends AbstractObjectifyDAO<Feedback>{
    
     public Feedback read(String _name) throws Exception {
            
        Feedback feedbackEntity = null;
        
            Feedback finalFeedbackEntity = new Feedback();
            ObjectifyService.run(new VoidWork() {
                @Override
                public void vrun() {
                    Feedback feedbackEntityResult =
                        ofy().load().type(Feedback.class)
                            .filter("name", _name)
                            .first()
                            .now();
                    if (feedbackEntityResult != null) {
                        CopyHelper.copy(feedbackEntityResult, finalFeedbackEntity);
                    }
                }
            });
            feedbackEntity = finalFeedbackEntity;
            
        return feedbackEntity;
    }
}
