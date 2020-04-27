/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.fxsupport.FXFControllerInterface;
import com.opus.fxsupport.FXFCountdownTimer;
import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import com.opus.syssupport.smstate;
import java.time.Duration;
import java.util.Random;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import org.reactfx.util.FxTimer;
import org.reactfx.util.Timer;

/**
 *
 * @author opus
 */
public class BlaineDevice {

    private static final Logger log = Logger.getLogger(BlaineDevice.class.getName());
   
    private FXFControllerInterface anct;
    
     
    private Boolean running = false;
    private Timer timer;
    private long timer_tick = 100L;
    
    private long last_tick;
    private Long init_ts;
    private Long end_ts;
    
    //private TimeStringConverter tsc;
    public static final String timestamp_format = "%1$tM:%1$tS:%1$tL";
    public static final String seconds_clock_format = "%1$tS:%1$tL";
    
    
    private static BlaineDevice instance; 
    public static BlaineDevice getInstance(){
        if (instance == null) {instance = new BlaineDevice();}
        return instance;
    }
    
    
    public BlaineDevice() {
      
    }
    
    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
    }
    
    public void setFXController (FXFControllerInterface anct){
        this.anct = anct;
    }
    
    
    public void initRuns(){
       ctrl.processSignal(new SMTraffic(0l, 0l, 0, "INITRUNS", this.getClass(),
                                   new VirnaPayload()));
    }
    
    
    private Integer max_runs = 3;
    private Integer run_number = 0;
    private Random rand = new Random();
    
    
    //===========
    @smstate (state = "RESETBLAINE")
    public boolean st_resetBlaine(SMTraffic smm){
        log.info(String.format("Doing reset @ blaine  ==========================================================="));
        FXFController caller = (FXFController)smm.getPayload().vobject;
        if (caller == anct){
            anct.getRunControl().initTimeList();
        }
        return true;
    }
    
    
    
    
    @smstate (state = "DORUN")
    public boolean st_doRun(SMTraffic smm){
        log.info(String.format("Doing new run   ==========================================================="));
        if (run_number < max_runs){
            ctrl.processSignal(new SMTraffic(0l, 0l, 0, "CALLINTERRUN", this.getClass(), new VirnaPayload()));
            run_number++;
        }
        return true;
    }
    
    
    @smstate (state = "UPDATERUN")
    public boolean st_updateRun(SMTraffic smm){
        log.info(String.format("ENDING -  run"));
  
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anct.getRunControl().addEntry(160 + ((rand.nextDouble()-0.5)*2), "Normal");
            }
        });
        
        return true;
    }
    
    
    
    @smstate (state = "ENDRUN")
    public boolean st_endRun(SMTraffic smm){
        log.info(String.format("ENDING -  run"));
  
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATETIME", this.getClass(), smm.getPayload()));
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "DORUN", this.getClass(), new VirnaPayload()));
   
        return true;
    }
    
    
    @smstate (state = "CALLINTERRUN")
    public boolean st_callInterrun(SMTraffic smm){
        log.info(String.format("Calling interrun"));
        
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXFCountdownTimer cdt = anct.getCDT();
                cdt.setOverevent("ENDINTERRUN");
                
                cdt.clearBars()
                .pushBar(1000, Color.DODGERBLUE, "Barra Azul", "NULLEVENT", "ENDINTERRUN")
                .pushBar(500, Color.GREEN, "Barra Verde", "NULLEVENT", "CHARGETIMEOUT")
                .updateBars();
               
                cdt.triggerTimer();
            }
        });
        return true;
    }
    
    
    @smstate (state = "ENDINTERRUN")
    public boolean st_endInterrun(SMTraffic smm){
        log.info(String.format("Ending interrun"));
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATERUN", this.getClass(), new VirnaPayload()));
        return true;
    }
    
    
    @smstate (state = "INITRUNS")
    public boolean st_initRuns(SMTraffic smm){
        log.info(String.format("Init runs"));
        
        run_number = 0;
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "DORUN", this.getClass(), new VirnaPayload()));
        
        return true;
    }
    
    
    
    
    public void triggerTimer(){

        if (running) {
            timer.stop();
            running = false;
        }
        else{
            timer.restart();
            init_ts = System.currentTimeMillis();
            log.info(String.format("Timer started @ "+ timestamp_format, init_ts));
            running = true;
        }
    }
    
    
    private void createTimer(){
        timer = FxTimer.createPeriodic(Duration.ofMillis(timer_tick), () -> {
            //LOG.info(String.format("Timer is running @ %d", System.currentTimeMillis()));
            tickTimer();
        });
    }
    
    
    private void tickTimer(){
           
       
    }
    
    
    
}
