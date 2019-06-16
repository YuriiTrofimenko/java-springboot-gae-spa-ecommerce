/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor @Getter @Setter
public class ProductModel {
    
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String image;
    // Choose:
    // - category id for POST
    private Long categoryId;
    // - or category model for GET
    private CategoryModel category;

    public ProductModel(String title, String description, BigDecimal price, int quantity, String image, Long categoryId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.categoryId = categoryId;
    }
    
    public ProductModel(String title, String description, BigDecimal price, int quantity, String image, CategoryModel category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category = category;
    }
    
    public ProductModel(Long id, String title, String description, BigDecimal price, int quantity, String image, CategoryModel category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category = category;
        this.id = id;
    }
}
