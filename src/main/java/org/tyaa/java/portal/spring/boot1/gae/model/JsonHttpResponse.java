/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author yurii
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonHttpResponse<T> {
    
    public static String errorStatus = "error";
    public static String warningStatus = "warning";
    public static String createdStatus = "created";
    public static String fetchedStatus = "fetched";
    public static String updatedStatus = "updated";
    public static String deletedStatus = "deleted";
    public static String successStatus = "success";
    public static String failStatus = "fail";
    
    public String status;
    public String message;
    public T data;
    
    public JsonHttpResponse(){}

    public JsonHttpResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonHttpResponse(T data) {
        this.data = data;
    }

    public JsonHttpResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
