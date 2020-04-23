/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.logging.Logger;



public class ActivitiesMap {
    
    private static final Logger LOG = Logger.getLogger(ActivitiesMap.class.getName());
    
    public static LinkedHashMap<String, ActivityDescriptor> activitiesmap = new LinkedHashMap<>();
    
    public static String APPPACKAGE = "pp200a.";
    
    public static void registerActivity (String id, ActivityDescriptor ad){
        ad.setID(id);
        activitiesmap.put(id, ad);
    }
    
    
    
    public static HashSet<String> getNames(){
        
        HashSet<String> outnames = new HashSet<>();
        
        for (ActivityDescriptor ad : activitiesmap.values()){
            outnames.add(ad.getName());
        }
        
        return outnames;
    }
    
    public static ActivityDescriptor getByName(String name){
        
        for (ActivityDescriptor ad : activitiesmap.values()){
            if (ad.getName().equals(name)) return ad;
        }
        
        return ActivityDescriptor.getDefault();
    }
    
    
}
