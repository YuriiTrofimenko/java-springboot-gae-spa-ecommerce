/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.tyaa.java.portal.spring.boot1.gae.entity.User;
import java.util.List;
import lombok.NoArgsConstructor;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;

/**
 *
 * @author gachechega
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CategoryModel {
    
    public Long id;
    public String name;
    public List<Product> products;

    public CategoryModel(String name) {
        this.name = name;
    }

    public CategoryModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
