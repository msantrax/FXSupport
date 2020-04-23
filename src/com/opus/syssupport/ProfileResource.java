/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import static com.opus.syssupport.PicnoUtils.loadJson;
import java.io.IOException;



public class ProfileResource {
    
    
        private String fpath;

    public String getFpath() {
        return fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    
        private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

        private String classtype;

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    
        private String label;

    public String getLabel() {
        return label;
    }

    public void setID(String label) {
        this.label = label;
    }

    
        private Profile profile;

    public <T extends Profile> T getProfile(Class<? extends Profile> profclass) throws IOException, ClassNotFoundException {
   
        if (profile == null){
            Class cl = Class.forName(classtype);
            profile = PicnoUtils.loadJson (fpath, profclass); 
        }
        return (T)profile;
    }

    public Profile getProfile() { return profile;}
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    
    
    public ProfileResource(String fpath, String name, String classtype, String label) {
        this.fpath = fpath;
        this.name = name;
        this.classtype = classtype;
        this.label = label;
    }

    

    
    
}
