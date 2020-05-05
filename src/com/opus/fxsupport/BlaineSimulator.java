/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author opus
 */
public class BlaineSimulator {

    private static final Logger LOG = Logger.getLogger(BlaineSimulator.class.getName());

    private static BlaineSimulator instance; 
    public static BlaineSimulator getInstance(){
        if (instance == null) {instance = new BlaineSimulator();}
        return instance;
    }
    
    private Timeline tml_charge; 
    private Timeline tml_measure; 
    
    
    public BlaineSimulator() {
       
    }
    
    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
    }
    
    
    
    public void initTimelines(){
        
        tml_charge = new Timeline(
                new KeyFrame(Duration.millis(500), e -> {
                    ctrl.processSignal(new SMTraffic(0l, 0l, 0, "CHARGEREADY", this.getClass(), 
                            new VirnaPayload().setFlag1(true)));
                })
//                new KeyFrame(Duration.millis(750), e -> {
//                    ctrl.processSignal(new SMTraffic(0l, 0l, 0, "CHARGESENSOR", this.getClass(), 
//                            new VirnaPayload().setFlag1(true)));
//                })
        );
        tml_charge.setCycleCount(1);
        
        
        tml_measure = new Timeline(
                new KeyFrame(Duration.millis(4000), e -> {
                    ctrl.processSignal(new SMTraffic(0l, 0l, 0, "MEASUREREADY", this.getClass(), 
                            new VirnaPayload().setFlag1(false)));
                })
//                new KeyFrame(Duration.millis(750), e -> {
//                    ctrl.processSignal(new SMTraffic(0l, 0l, 0, "FINALSENSOR", this.getClass(), 
//                            new VirnaPayload().setFlag1(false)));
//                })
        );
        tml_measure.setCycleCount(1);
        
        
    }
    
    
    public void triggerCharge(){
        tml_charge.playFromStart();
    }
    
    public void triggerMeasure(){
        
        
        tml_measure.playFromStart();
    }
    
    
    
    
}
