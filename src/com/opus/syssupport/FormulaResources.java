/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import com.opus.fxsupport.FXFFieldDescriptor;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author opus
 */
public class FormulaResources {

    private static final Logger LOG = Logger.getLogger(FormulaResources.class.getName());
    
    
    
    private static FormulaResources instance; 
    public static FormulaResources getInstance(){
        if (instance == null) {instance = new FormulaResources();}
        return instance;
    }
        
    private ArrayList<Formula>formulas = new ArrayList<>();
    private Profile profile;
    
    
    
    public FormulaResources() {
        instance = this;
    }

   
    public void updateFormulas(){
        
        
        
        for (Formula form : getFormulas()){
            
            
        }
        
        
    }
    
    
    public void addFormula (FXFFieldDescriptor field){
        getFormulas().add(new Formula(field));
    }
    
    public Formula getFormula(FXFFieldDescriptor field){
        
        for (Formula form : getFormulas()){
            if (form.getField().equals(field)) return form;
        }
        return null;
    }
    
    public Formula getFormula(String fieldname){
        
        for (Formula form : getFormulas()){
            if (form.getField().getName().equals(fieldname)) return form;
        }
        return null;
    }

    public ArrayList<Formula> getFormulas() {
        return formulas;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        
    }
    
    
    
}
