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

        private Double an_timeout = 200.0;

    public Double getAn_timeout() {
        return an_timeout;
    }

    public void setAn_timeout(Double an_timeout) {
        this.an_timeout = an_timeout;
    }

    public BlaineFieldDescriptor() {
    }

    
    
    
}
