/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import java.util.Locale;
import javafx.util.StringConverter;

/**
 *
 * @author opus
 */
public class FXFAnaliseStringConverter <T> extends StringConverter <T>{
    
    private FXFAnaliseListItem  li;
    
    @Override
    public String toString(T t) {
        
        li = (FXFAnaliseListItem)t;
        String out;
        try{
            out = String.format(Locale.US, " [%d] %5.2f (%s)", li.getSequence(), li.getValue(), li.getStatus()); 
        }
        catch (Exception ex) {
            out = " [X] Erro na conversão";
        }
        return out;
    }

    @Override
    public T fromString(String string) {
        
        return (T)li;
    }
    
}
