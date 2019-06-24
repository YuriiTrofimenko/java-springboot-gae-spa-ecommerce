/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gachechega
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Feedback {
    
    @Id
    private Long id;
    @Index
    private String name;
    private String text;
    @Index
    private String datestime;

    public Feedback(Long id, String name, String text, String datestime) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.datestime = datestime;
    }

    public Feedback(String name, String text, String datestime) {
        this.name = name;
        this.text = text;
        this.datestime = datestime;
    }
}
