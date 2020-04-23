/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import java.util.logging.Logger;
import com.opus.fxsupport.FXFControllerInterface;
import com.opus.fxsupport.PropertyLinkDescriptor;
import java.util.LinkedHashMap;

/**
 *
 * @author opus
 */
public class ActivityModel {

    private static final Logger LOG = Logger.getLogger(ActivityModel.class.getName());
    
    protected ActivityMachine machine;
    protected VirnaServiceProvider appctrl;
    protected FXFControllerInterface fxctrl;
    
    protected Profile profile;
  
    protected LinkedHashMap<String,PropertyLinkDescriptor>ana_proplink_uimap;
    protected LinkedHashMap<String,PropertyLinkDescriptor>ana_proplink_modelmap;
    
    
    
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public String getCallsign(){
        if (profile != null) {
            return profile.getArgument();
        }
        return "not_loaded";
    }

    public ActivityMachine getMachine() {
        return machine;
    }

    public void setMachine(ActivityMachine machine) {
        this.machine = machine;
    }

    public VirnaServiceProvider getAppCtrl() {
        return appctrl;
    }

    public void setAppCtrl(VirnaServiceProvider ctrl) {
        this.appctrl = ctrl;
    }

    public FXFControllerInterface getFXCtrl() {
        return fxctrl;
    }

    public void setFXCtrl(FXFControllerInterface fxctrl) {
        this.fxctrl = fxctrl;
    }
    
    public LinkedHashMap<String,PropertyLinkDescriptor> getAna_proplink_uimap() {
        return ana_proplink_uimap;
    }

    public void setAna_proplink_uimap(LinkedHashMap<String,PropertyLinkDescriptor> ana_proplink_uimap) {
        this.ana_proplink_uimap = ana_proplink_uimap;
    }

    public LinkedHashMap<String,PropertyLinkDescriptor> getAna_proplink_modelmap() {
        return ana_proplink_modelmap;
    }

    public void setAna_proplink_modelmap(LinkedHashMap<String,PropertyLinkDescriptor> ana_proplink_modelmap) {
        this.ana_proplink_modelmap = ana_proplink_modelmap;
    }
    
    
    
    
}
