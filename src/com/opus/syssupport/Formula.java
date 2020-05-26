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
public class Formula {

    private static final Logger LOG = Logger.getLogger(Formula.class.getName());
    
    
        private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    
        private String fieldname;

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    
        private ArrayList<FormulaResource> resources;

    public ArrayList<FormulaResource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<FormulaResource> resources) {
        this.resources = resources;
    }

    
        private FXFFieldDescriptor field;

    public FXFFieldDescriptor getField() {
        return field;
    }

    
    public void setField(FXFFieldDescriptor field) {
        this.field = field;
    }

    
    public Formula(FXFFieldDescriptor field) {
        resources = new ArrayList<>();
        this.field = field;
        this.fieldname=field.getName();
    }
    
    
    
    
    
    
    
    
}
