/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author yurii
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor @Getter @Setter
public class ProductFilter {
    
    public enum OrderBy {
        sortPriceDesc
        , sortPriceAsc
    }

    private Long[] categories;
    private OrderBy sort;
    
    public ProductFilter(Long[] categories, OrderBy sort) {
        this.categories = categories;
        this.sort = sort;
    }
}
