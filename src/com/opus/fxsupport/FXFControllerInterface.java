/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.syssupport.VirnaServiceProvider;
import javafx.scene.Scene;

/**
 *
 * @author opus
 */
public interface FXFControllerInterface {
    
    //public void initWidgetManager(ObservableMap<String, Object> namespace);
    
    public String getUID();
   
    public void yieldFocus (FXFField field, boolean fwd, boolean tab);
    public void updateFocus (Integer pos, boolean focused);
    
    public void sendSignal (PropertyLinkDescriptor pld, String sigtype);
    public void clearCanvas();
    
    public void update();
    public void update(Scene scene);
    
    public void setAppController (VirnaServiceProvider ctrl);
    
    public SystemMenu getMenu(boolean isadm);
    
    public void updateField (String fieldname, String value, boolean required);
    public void initValidators (FXFField field, FXFFieldDescriptor fxfd);
    
    public void activateModel();
    
    public String getProfileID();
    
    
}
