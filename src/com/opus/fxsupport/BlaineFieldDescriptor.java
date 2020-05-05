/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

/**
 *
 * @author opus
 */
public class BlaineFieldDescriptor {
    
       private String opmode = "Manual";

    public String getOpmode() {
        return opmode;
    }

    public void setOpmode(String opmode) {
        this.opmode = opmode;
    }
 
        private Integer maxruns = 3;

    public Integer getMaxruns() {
        return maxruns;
    }

    public void setMaxruns(Integer maxruns) {
        this.maxruns = maxruns;
    }

        private boolean skipfirst = true;

    public boolean isSkipfirst() {
        return skipfirst;
    }

    public void setSkipfirst(boolean skipfirst) {
        this.skipfirst = skipfirst;
    }

    
    
    
    // TIMMING =======================================================
    
        private Double interrun = 3.0;

    public Double getInterrun() {
        return interrun;
    }

    public void setInterrun(Double interrun) {
        this.interrun = interrun;
    }

        private Double auto_timeout;

    public Double getAuto_timeout() {
        return auto_timeout;
    }

    public void setAuto_timeout(Double auto_timeout) {
        this.auto_timeout = auto_timeout;
    }


    
    
        private Double an_timeout = 200.0;

    public Double getAn_timeout() {
        return an_timeout;
    }

    public void setAn_timeout(Double an_timeout) {
        this.an_timeout = an_timeout;
    }

    
    
    
    
        private transient Double target = 150.0;

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

        private transient Double targetlow = 140.0;

    public Double getTargetlow() {
        return targetlow;
    }

    public void setTargetlow(Double targetlow) {
        this.targetlow = targetlow;
    }

        private transient Double targethigh = 160.0;

    public Double getTargethigh() {
        return targethigh;
    }

    public void setTargethigh(Double targethigh) {
        this.targethigh = targethigh;
    }

    
        private transient Double targetRSD = 0.8;

    public Double getTargetRSD() {
        return targetRSD;
    }

    public void setTargetRSD(Double targetRSD) {
        this.targetRSD = targetRSD;
    }

    
    
    
    
    public BlaineFieldDescriptor() {
    }

    
    
    
}
