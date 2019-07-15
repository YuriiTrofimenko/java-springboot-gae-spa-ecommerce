/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
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
import org.tyaa.java.portal.spring.boot1.gae.utils.ErrorsGetter;
import org.tyaa.java.portal.spring.boot1.gae.utils.Mailer;

/**
 *
 * @author gachechega
 */
@Service

public class FeedbackService {

    @Autowired
    private FeedbackObjectifyDAO feedbackDAO;

    public JsonHttpResponse createFeedback(FeedbackModel _feedbackModel, Authentication _authentication) throws MessagingException, UnsupportedEncodingException {

        JsonHttpResponse response = new JsonHttpResponse();
        if (_authentication != null && _authentication.isAuthenticated()) {
            // Если дата с клиента не пришла
            if (_feedbackModel.getDatestime() == null) {
                // Создали текущую дату
                Date date = new Date();
                // Создали форматтер
                SimpleDateFormat myformat
                        = new SimpleDateFormat("yyyy-MM-dd");
                _feedbackModel.setDatestime(myformat.format(date));
            }

            /* Sending email */
            String messageString
                    = "User " + _authentication.getName() + "added a new feedback: "
                    + _feedbackModel.getText();
            String subjectString = "New feedback";
            String fromEmailString = "gachechega@gmail.com";
            String fromNameString = "JavaPortal";
            String toNameString = "JavaPortal Admin";
            String toEmailString = "gachechega@gmail.com";
            //try {
                Mailer.sendPlainMsg(
                        messageString,
                         subjectString,
                         fromEmailString,
                         fromNameString,
                         toEmailString,
                         toNameString);
            /*} catch (UnsupportedEncodingException | MessagingException ex) {

                ErrorsGetter.printException(ex);
            }*/

            Feedback feedback
                    = new Feedback(
                            _authentication.getName(),
                            _feedbackModel.getText(),
                            _feedbackModel.getDatestime()
                    );
            feedbackDAO.create(feedback);
            response
                    = new JsonHttpResponse(
                            JsonHttpResponse.createdStatus,
                            "Feedback '" + _feedbackModel.getName() + "' created successfully"
                    );
        } else {
            response.status = JsonHttpResponse.failStatus;
            response.message = "User is a guest";
        }
        return response;
    }

    public JsonHttpResponse<List<FeedbackModel>> readFeedback() {

        List<Feedback> feedbacks = feedbackDAO.read();
        List<FeedbackModel> feedbackModels
                = feedbacks.stream()
                        .sorted((o1, o2) -> {
                            return o2.getDatestime().compareTo(o1.getDatestime()); //To change body of generated lambdas, choose Tools | Templates.
                        })
                        .map((r) -> {
                            SimpleDateFormat stringToDateFormat
                                    = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = stringToDateFormat.parse(r.getDatestime());
                            } catch (ParseException ex) {
                                Logger.getLogger(FeedbackService.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            String dateString = "-";
                            if (date != null) {
                                SimpleDateFormat dateToStringFormat
                                        = new SimpleDateFormat("dd-MM-yyyy");
                                dateString = dateToStringFormat.format(date);
                            }
                            return new FeedbackModel(r.getId(), r.getName(), r.getText(), dateString);
                        })
                        .collect(Collectors.toList());
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus,
                "The feedbacks list fetched successfully",
                feedbackModels
        );
    }

    public JsonHttpResponse<FeedbackModel> readFeedback(Long _id) throws Exception {

        Feedback feedback
                = feedbackDAO.read(_id);
        String status
                = (feedback != null && feedback.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message
                = (feedback != null && feedback.getId() != null)
                ? "The feedback fetched successfully"
                : "Not found";
        FeedbackModel feedbackModel = new FeedbackModel(feedback.getId(), feedback.getName(), feedback.getText(), feedback.getDatestime());
        return new JsonHttpResponse(
                status,
                message,
                feedbackModel
        );
    }

    public JsonHttpResponse deleteFeedback(Long _id) {

        feedbackDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus,
                "The feedback deleted successfully"
        );
    }
}
