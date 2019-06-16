package org.tyaa.java.portal.spring.boot1.gae.controller;

import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;
import org.tyaa.java.portal.spring.boot1.gae.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyaa.java.portal.spring.boot1.gae.model.CategoryModel;
import org.tyaa.java.portal.spring.boot1.gae.service.ProductService;

/**
 *
 * @author gachechega
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public JsonHttpResponse getAll() {
    
        return productService.readCategory();
    }

    @GetMapping(value = "/{id}")
    public JsonHttpResponse get(@PathVariable("id") Long _id) throws Exception {
        
        return productService.readCategory(_id);
    }
    
    /*@GetMapping(value = "/get-by-name/{name}")
    public JsonHttpResponse getByName(@PathVariable("name") String _name) throws Exception {
        
        return authorService.read(_name);
    }*/
    
    @PostMapping("/create")
    public JsonHttpResponse create(@RequestBody CategoryModel _category) {
        return productService.createCategory(_category);
    }
    
    /*@PostMapping("/update")
    public JsonHttpResponse update(@RequestBody Author _author) {
        return authorService.update(_author);
    }*/
    
    @DeleteMapping(value = "/delete/{id}")
    public JsonHttpResponse delete(@PathVariable("id") Long _id) {
        
        return productService.deleteCategory(_id);
    }
}
