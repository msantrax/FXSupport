/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import java.util.LinkedHashMap;
import com.opus.fxsupport.FXFControllerInterface;
import com.opus.fxsupport.FXFField;
import com.opus.fxsupport.PropertyLinkDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import javafx.scene.control.Control;

/**
 *
 * @author opus
 */
public class ActivityMachine {

    private static final Logger log = Logger.getLogger(ActivityMachine.class.getName());
    
    protected LinkedHashMap<String, String> profile_map = new LinkedHashMap<>();
    protected LinkedHashMap<String, ActivityModel> models;

    protected FXFControllerInterface anct;
    
   
    public LinkedHashMap<String, ActivityModel> getModels() {
        return models;
    }
    
    public void addModel (String id, ActivityModel am){
        models.put(id, am);
    }
    
    
    public void activateModel (String id){  }
    
    
    public void mapProperties(String key, Object bean){
     
        Class<?> c = bean.getClass();     
        propertylink plink;
       
        PropertyLinkDescriptor linkdesc;
        ActivityModel am = models.get(key);
       
        for (Method mt : c.getDeclaredMethods() ){
            plink = mt.getAnnotation(propertylink.class);
            if (plink != null){
   
//                log.info(String.format("Property %s has link = %s  -- input=%s  -- callback=%s", 
//                        plink.propname(),
//                        plink.plink(), 
//                        plink.input(),
//                        plink.callstate()));
    
                linkdesc = new PropertyLinkDescriptor().setClazz(c)
                                                .setInstance(bean)
                                                .setMethod(mt)
                                                .setPropname(plink.propname())
                                                .setInput(plink.input())
                                                .setPlink(plink.plink())
                                                .setCallstate(plink.callstate())
                                                ;
                
                am.getProplink_uimap().put(plink.plink(), linkdesc);
                am.getProplink_modelmap().put(plink.propname(), linkdesc);
            }
        }
        log.info("Prop map done !");
        
    }
    
    public PropertyLinkDescriptor getLinkDescriptor(FXFField field, LinkedHashMap<String,PropertyLinkDescriptor> descriptor) {
        
        //for (PropertyLinkDescriptor pld : ana_proplink_uimap.values()){
        for (PropertyLinkDescriptor pld : descriptor.values()){
            FXFField dfield = pld.getFxfield();
            if (dfield != null && dfield.equals(field)) return pld;
        } 
        return null;
    }
    
    
}
