/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyaa.java.portal.spring.boot1.gae.model.CategoryModel;
import org.tyaa.java.portal.spring.boot1.gae.model.FeedbackModel;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;
import org.tyaa.java.portal.spring.boot1.gae.service.FeedbackService;
import org.tyaa.java.portal.spring.boot1.gae.service.ProductService;

/**
 *
 * @author gachechega
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("")
    public JsonHttpResponse getAll() {
    
        return feedbackService.readFeedback();
    }

    @GetMapping(value = "/{id}")
    public JsonHttpResponse get(@PathVariable("id") Long _id) throws Exception {
        
        return feedbackService.readFeedback(_id);
    }
    
    /*@GetMapping(value = "/get-by-name/{name}")
    public JsonHttpResponse getByName(@PathVariable("name") String _name) throws Exception {
        
        return authorService.read(_name);
    }*/
    
    @PostMapping("/create")
    public JsonHttpResponse create(@RequestBody FeedbackModel _feedback, Authentication _authentication) {
        return feedbackService.createFeedback(_feedback, _authentication);
    }
    
    /*@PostMapping("/update")
    public JsonHttpResponse update(@RequestBody Author _author) {
        return authorService.update(_author);
    }*/
    
    @DeleteMapping(value = "/delete/{id}")
    public JsonHttpResponse delete(@PathVariable("id") Long _id) {
        
        return feedbackService.deleteFeedback(_id);
    }
    
}
