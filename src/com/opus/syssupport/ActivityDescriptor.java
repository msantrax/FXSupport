/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import com.opus.fxsupport.FXFControllerInterface;


public class ActivityDescriptor {
    
    
        private String ID;

    public String getID() {
        return ID;
    }

    public ActivityDescriptor setID(String ID) {
        this.ID = ID;
        return this;
    }

    
    
        private Class fxcontrollerclass;

    public Class getFxcontrollerclass() {
        return fxcontrollerclass;
    }

    public ActivityDescriptor setFxcontrollerclass(Class fxcontrollerclass) {
        this.fxcontrollerclass = fxcontrollerclass;
        return this;
    }

    public String getFxcontrollerName(){
        String out = fxcontrollerclass.getName().replace(ActivitiesMap.APPPACKAGE, "");
        return out; 
    }
    
    
        private Class machineclass;

    public Class getMachineclass() {
        return machineclass;
    }

    public ActivityDescriptor setMachineclass(Class machineclass) {
        this.machineclass = machineclass;
        return this;
    }
    
    
        private Class modelclass;

    public Class getModelclass() {
        return modelclass;
    }

    public ActivityDescriptor setModelclass(Class modelclass) {
        this.modelclass = modelclass;
        return this;
    }
    
    
    
    

    
        private String argument_prefix;

    public String getArgument_prefix() {
        return argument_prefix;
    }

    public ActivityDescriptor setArgument_prefix(String argument_prefix) {
        this.argument_prefix = argument_prefix;
        return this;
    }


    
    
    
    
        private String name;

    public String getName() {
        return name;
    }

    public ActivityDescriptor setName(String name) {
        this.name = name;
        return this;
    }

    
        private String label;

    public String getLabel() {
        return label;
    }

    public ActivityDescriptor setLabel(String label) {
        this.label = label;
        return this;
    }

    
    
    
        private boolean publicstates = false;

    public boolean isPublicstates() {
        return publicstates;
    }

    public ActivityDescriptor setPublicstates(boolean publicstates) {
        this.publicstates = publicstates;
        return this;
    }

    
    public ActivityDescriptor(Class fxcontrollerclass) {
        this.fxcontrollerclass = fxcontrollerclass;
    }
    
    
    public static ActivityDescriptor getDefault(){
        
        return new ActivityDescriptor (FXFControllerInterface.class)
                                        .setMachineclass(null)
                                        .setModelclass(null)
                                        .setArgument_prefix("default_descriptor")
                                        .setName("Default Descriptor")
                                        .setLabel("Default Descriptor")
                                        .setPublicstates(false);
        
    }
    
    
    
}
