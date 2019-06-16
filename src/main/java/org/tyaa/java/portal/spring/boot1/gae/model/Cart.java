/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yurii
 */
public class Cart implements Serializable {
    private final List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
