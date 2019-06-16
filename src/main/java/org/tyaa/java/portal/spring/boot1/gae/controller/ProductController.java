/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tyaa.java.portal.spring.boot1.gae.model.Cart;
import org.tyaa.java.portal.spring.boot1.gae.model.CartItem;
import org.tyaa.java.portal.spring.boot1.gae.model.ProductFilter;
import org.tyaa.java.portal.spring.boot1.gae.model.ProductModel;
import org.tyaa.java.portal.spring.boot1.gae.service.ProductService;

/**
 *
 * @author gachechega
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public JsonHttpResponse getAll() {
    
        return productService.readProduct();
    }

    @GetMapping(value = "/{id}")
    public JsonHttpResponse get(@PathVariable("id") Long _id) throws Exception {
        
        return productService.readProduct(_id);
    }
    
    /*@GetMapping(value = "/get-by-name/{name}")
    public JsonHttpResponse getByName(@PathVariable("name") String _name) throws Exception {
        
        return authorService.read(_name);
    }*/
    
    @PostMapping("/filtered")
    public JsonHttpResponse getFiltered(@RequestBody ProductFilter filter) {
        
        return productService.getFiltered(filter);
    }
    
    @PostMapping("/create")
    public JsonHttpResponse create(@RequestBody ProductModel _product) throws Exception {
        return productService.createProduct(_product);
    }
    
    /*@PostMapping("/update")
    public JsonHttpResponse update(@RequestBody Author _author) {
        return authorService.update(_author);
    }*/
    
    @DeleteMapping(value = "/delete/{id}")
    public JsonHttpResponse delete(@PathVariable("id") Long _id) {
        
        return productService.deleteProduct(_id);
    }
    
    /* Cart Actions */
        
    @GetMapping("/cart")
    public JsonHttpResponse getCartItems(HttpSession httpSession) {
        Cart cart = (Cart) httpSession.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
        }
        return productService.getCartItems(cart);
    }

    @PostMapping("/cart/add/{id}")
    public JsonHttpResponse addCartItemCount(@PathVariable("id") Long id, HttpSession httpSession) throws InstantiationException, IllegalAccessException {
        Cart cart = (Cart) httpSession.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
        }
        JsonHttpResponse response =
                productService.changeCartItemCount(
                cart
                , id
                , CartItem.Action.ADD
        );
        httpSession.setAttribute("CART", cart);
        return response;
    }

    @PostMapping("/cart/neg/{id}")
    public JsonHttpResponse negCartItemCount(@PathVariable("id") Long id, HttpSession httpSession) throws InstantiationException, IllegalAccessException {
        Cart cart = (Cart) httpSession.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
        }
        JsonHttpResponse response =
                productService.changeCartItemCount(
                cart
                , id
                , CartItem.Action.NEG
            );
        httpSession.setAttribute("CART", cart);
        return response;
    }

    @RequestMapping(value = "/cart/delete/{id}", method = RequestMethod.DELETE)
    public JsonHttpResponse deleteCartItem(@PathVariable("id") Long id, HttpSession httpSession) throws InstantiationException, IllegalAccessException {
        Cart cart = (Cart) httpSession.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
        }
        JsonHttpResponse response =
                productService.changeCartItemCount(
                cart
                , id
                , CartItem.Action.REM
        );
        httpSession.setAttribute("CART", cart);
        return response;
    }
}
