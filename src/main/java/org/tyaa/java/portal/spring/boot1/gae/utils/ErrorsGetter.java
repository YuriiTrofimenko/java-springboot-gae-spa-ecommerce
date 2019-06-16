/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.utils;

/**
 *
 * @author yurii
 */
public class ErrorsGetter {

    public static String printException(Exception _ex) {

        String errorString = "";
        if (_ex.getMessage() == null) {

            String errorTrace = "";
            for (StackTraceElement el : _ex.getStackTrace()) {
                errorTrace += el.toString();
            }
            if (errorTrace.equals("")) {
            } else {
                errorString = errorTrace;
            }
        } else {

            Integer lineNumber = 0;
            String className = "";
            try {
                lineNumber = _ex.getStackTrace()[0].getLineNumber();
                className = _ex.getStackTrace()[0].getClassName();
            } catch (Exception ex) {
                try {
                    lineNumber = _ex.getCause().getStackTrace()[0].getLineNumber();
                    className = _ex.getCause().getStackTrace()[0].getClassName();
                } catch (Exception e) {
                }
            }
            errorString = _ex.getMessage() + "line: " + lineNumber + "; class: " + className;
        }
        return errorString;
    }
}
