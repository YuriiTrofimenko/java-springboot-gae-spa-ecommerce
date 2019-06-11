/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

/**
 *
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ProductModel {
    
    public Long id;
    public String name;
    public CategoryModel category;

    public ProductModel(String name, CategoryModel category) {
        this.name = name;
        this.category = category;
    }

    public ProductModel(Long id, String name, CategoryModel category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
