/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import java.io.Serializable;

/**
 *
 * @author yurii
 */
public class CartItem implements Serializable {
    public enum Action {
        ADD
        , NEG
        , REM
    }
    public Long id;
    public String name;
    public int count;

    public CartItem(Long id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
