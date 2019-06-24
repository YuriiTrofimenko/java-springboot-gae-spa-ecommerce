/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tyaa.java.portal.spring.boot1.gae.dao.FeedbackObjectifyDAO;
import org.tyaa.java.portal.spring.boot1.gae.dao.ProductObjectifyDAO;
import org.tyaa.java.portal.spring.boot1.gae.entity.Category;
import org.tyaa.java.portal.spring.boot1.gae.entity.Feedback;
import org.tyaa.java.portal.spring.boot1.gae.model.CategoryModel;
import org.tyaa.java.portal.spring.boot1.gae.model.FeedbackModel;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;

/**
 *
 * @author gachechega
 */
@Service

public class FeedbackService {
    
    @Autowired
    private FeedbackObjectifyDAO feedbackDAO;
    
    public JsonHttpResponse createFeedback(FeedbackModel _feedbackModel, Authentication _authentication) {
        
        JsonHttpResponse response = new JsonHttpResponse();
        if (_authentication != null && _authentication.isAuthenticated()) {
            Feedback feedback =
            new Feedback(
                    _authentication.getName()
                    , _feedbackModel.getText()
                    , _feedbackModel.getDatestime()
                );
            feedbackDAO.create(feedback);
            response =
                    new JsonHttpResponse(
                    JsonHttpResponse.createdStatus
                    , "Feedback '" + _feedbackModel.getName() + "' created successfully"
                );
        } else {
            response.status = JsonHttpResponse.failStatus;
            response.message = "User is a guest";
        }
        return response;
    }
    
     public JsonHttpResponse<List <FeedbackModel>> readFeedback() {
        
        List<Feedback> feedbacks = feedbackDAO.read();
        List<FeedbackModel> feedbackModels =
            feedbacks.stream()
            .map((r) -> {
                return new FeedbackModel(r.getId(), r.getName(), r.getText(), r.getDatestime());
            })
            .collect(Collectors.toList());
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus
                , "The feedbacks list fetched successfully"
                , feedbackModels
        );
    }

      public JsonHttpResponse<FeedbackModel> readFeedback(Long _id) throws Exception {
        
        Feedback feedback =
                feedbackDAO.read(_id);
        String status =
                (feedback != null && feedback.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message =
                (feedback != null && feedback.getId() != null)
                ? "The feedback fetched successfully"
                : "Not found";
       FeedbackModel feedbackModel = new FeedbackModel(feedback.getId(), feedback.getName(), feedback.getText(),feedback.getDatestime());
        return new JsonHttpResponse(
                status
                , message
                , feedbackModel
        );
    }

      public JsonHttpResponse deleteFeedback(Long _id) {
        
        feedbackDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus
                , "The feedback deleted successfully"
        );
    }
}
