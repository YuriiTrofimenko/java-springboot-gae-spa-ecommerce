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
 * @author gachechega
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor @Getter @Setter

public class SubscriptionModel {
    public Long id;
    public Boolean subscription;
    public String mail;
    public UserModel user;

    public SubscriptionModel(Long id, Boolean subscription, String mail ,UserModel user) {
        this.id = id;
        this.subscription = subscription;
        this.mail = mail;
        this.user = user;
    }

    public String getuserId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

       
}
