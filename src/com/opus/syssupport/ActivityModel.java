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
  
    protected LinkedHashMap<String,PropertyLinkDescriptor>proplink_uimap;
    protected LinkedHashMap<String,PropertyLinkDescriptor>proplink_modelmap;
    
    
    
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
    
    public LinkedHashMap<String,PropertyLinkDescriptor> getProplink_uimap() {
        return proplink_uimap;
    }

    public void setProplink_uimap(LinkedHashMap<String,PropertyLinkDescriptor> proplink_uimap) {
        this.proplink_uimap = proplink_uimap;
    }

    public LinkedHashMap<String,PropertyLinkDescriptor> getProplink_modelmap() {
        return proplink_modelmap;
    }

    public void setProplink_modelmap(LinkedHashMap<String,PropertyLinkDescriptor> proplink_modelmap) {
        this.proplink_modelmap = proplink_modelmap;
    }
    
    
    
    
}
