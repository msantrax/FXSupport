/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;



public class FXFAnaliseListItem {

        private Double value = 0.0;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

        private Integer sequence;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

        private String status = "active";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

        private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    
    
    
    
    public FXFAnaliseListItem() {
    }

    
    public FXFAnaliseListItem(Integer sequence, Double value, String status, boolean check) {
        this.sequence = sequence;
        this.value = value;
        this.status = status;
        this.check = check;
        
    }
    
    
    
    
}
