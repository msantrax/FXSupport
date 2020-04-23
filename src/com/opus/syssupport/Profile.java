/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import com.opus.fxsupport.FXFFieldDescriptor;
import java.util.ArrayList;


public class Profile extends JsonHeader{
    
    protected String label;
    
    
    protected ArrayList<String>report_template;
    protected String csv_file;
    
    protected ArrayList<FXFFieldDescriptor> descriptors ;
   
    
    private static Profile instance; 
    public static Profile getInstance(){
        if (instance == null) {instance = new Profile();}
        return instance;
    }
    
    
    public Profile() {
        descriptors = new ArrayList<>();
       
        classtype="com.opus.syssupport.Profile";
        csv_file="default.csv";
        report_template=new ArrayList<String>();
        report_template.add("pdf1.pdf");
        label="Profile";
   
    }
    
    
    public ArrayList<FXFFieldDescriptor> getDescriptors() { return descriptors;} 
   
    
    public FXFFieldDescriptor getDescriptor(String id){
        
        for (FXFFieldDescriptor fd : descriptors){
            if (fd.getName().equals(id)) return fd;
        }
        return null;
    }

    
    public ArrayList<String> getReport_template() {
        return report_template;
    }

    public void setReport_template(ArrayList<String> report_template) {
        this.report_template = report_template;
    }
    
    public String getCsv_file() {
        return csv_file;
    }

    public void setCsv_file(String csv_file) {
        this.csv_file = csv_file;
    }
    
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
    public void createDefault(){
        
        descriptors.clear();
        
    }
    
    
}
