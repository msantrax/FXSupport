/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;


public class StatusMessage implements Comparable<StatusMessage> {

        private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

        private Integer priority = 0;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

        private boolean bigmessage = false;

    public boolean isBigmessage() {
        return bigmessage;
    }

    
    public void setBigmessage(boolean bigmessage) {
        this.bigmessage = bigmessage;
    }

        private Long keepalive;

    public Long getkeepAlive() {
        return keepalive;
    }

    public void setkeepAlive(Long alive) {
        this.keepalive = alive;
    }

    
    public StatusMessage(String mes, Integer priority) {
        this.message= mes;
        this.priority = priority;
        //keepalive = System.currentTimeMillis() + (priority * 500);
    }

    public StatusMessage(String mes, Integer priority, boolean big) {
        this.message= mes;
        this.priority = priority;
        this.setBigmessage(big);
        //keepalive = System.currentTimeMillis() + (priority * 500);
    }
    
    
    
    
    @Override
    public int compareTo(StatusMessage o) {
        return this.getPriority().compareTo(o.getPriority());
    }
  
    
}
