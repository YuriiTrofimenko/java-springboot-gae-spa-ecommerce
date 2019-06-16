/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.controller;

import javax.servlet.http.HttpSession;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;
import org.tyaa.java.portal.spring.boot1.gae.model.UserModel;
import org.tyaa.java.portal.spring.boot1.gae.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tyaa.java.portal.spring.boot1.gae.model.Cart;

/**
 *
 * @author gachechega
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private AuthService authService;

    @GetMapping("")
    public JsonHttpResponse getAll() {
    
        return authService.readUser();
    }

    @GetMapping(value = "/{id}")
    public JsonHttpResponse get(@PathVariable("id") Long _id) throws Exception {
        
        return authService.readUser(_id);
    }
    
    /*@GetMapping(value = "/get-by-name/{name}")
    public JsonHttpResponse getByName(@PathVariable("name") String _name) throws Exception {
        
        return authorService.read(_name);
    }*/
    
    @PostMapping("/create")
    public JsonHttpResponse create(@RequestBody UserModel _user) throws Exception {
        return authService.createUser(_user);
    }
    
    /*@PostMapping("/update")
    public JsonHttpResponse update(@RequestBody Author _author) {
        return authorService.update(_author);
    }*/
    
    @DeleteMapping(value = "/delete/{id}")
    public JsonHttpResponse delete(@PathVariable("id") Long _id) {
        
        return authService.deleteUser(_id);
    }
    
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public JsonHttpResponse checkUser(Authentication authentication) {
        
        return authService.check(authentication);
    }
    
    @GetMapping("/onsignout")
    public JsonHttpResponse onsignout(HttpSession httpSession) {
            // httpSession.removeAttribute("ACCOUNT_INFO");
            Cart cart = (Cart) httpSession.getAttribute("CART");
            if (cart == null) {
                httpSession.removeAttribute("CART");
            }
            return authService.onSignOut();
    }
    
    @GetMapping("/onerror")
    public JsonHttpResponse onerror() {
            
            return authService.onError();
    }
}
