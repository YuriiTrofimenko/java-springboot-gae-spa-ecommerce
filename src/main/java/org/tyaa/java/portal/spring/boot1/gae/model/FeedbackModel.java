/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gachechega
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor @Getter @Setter
public class FeedbackModel {
    
    private Long id;
    private String name;
    private String text;
    private String datestime;

    public FeedbackModel(Long id, String name, String text, String datestime) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.datestime = datestime;
    }

    public FeedbackModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
