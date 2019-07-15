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
public class UserModel {
    
    public Long id;
    public String name;
    public String password;
    public RoleModel role;
    public String mail;
    public Boolean subscribe;

    public UserModel(Long id, String name, String password, RoleModel role, String mail, Boolean subscribe) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.role = role;
        this.subscribe = subscribe;
    }

    public UserModel(String name, String password, String mail ,RoleModel role) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.role = role;
    }

    public UserModel(Long id, String name, String password, String mail, RoleModel role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.role = role;
    }

    public UserModel(Long id, String name) {
        this.id = id;
        this.name = name;    }
}
