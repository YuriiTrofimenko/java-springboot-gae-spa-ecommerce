/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yurii
 */
public class CopyHelper {
    
    private static final Logger log =
            Logger.getLogger(CopyHelper.class.getName());

    public static <T> void copy(T _from, T _to) {

        try {
            Class<T> _clazz = (Class<T>) _from.getClass();
            Field[] fields = _clazz.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);
                field.set(_to, field.get(_from));
            }
        } catch (Exception ex) {
            log.log(Level.SEVERE, ErrorsGetter.printException(ex));
        }
        
    }
}
