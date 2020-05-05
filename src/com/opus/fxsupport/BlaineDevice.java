/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import static com.opus.fxsupport.BlaineDevice.timestamp_format;
import com.opus.fxsupport.FXFControllerInterface;
import com.opus.fxsupport.FXFCountdownTimer;
import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import com.opus.syssupport.smstate;
import java.time.Duration;
import java.util.ArrayList;
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
      
    
    private Timer timer;
    private long timer_tick = 100L;
    
    private long last_tick;
    private Long init_ts;
    private Long end_ts;
    
    //private TimeStringConverter tsc;
    public static final String timestamp_format = "%1$tM:%1$tS:%1$tL";
    public static final String seconds_clock_format = "%1$tS:%1$tL";
    
    
    protected BlaineFieldDescriptor bfd;
    private BlaineSimulator bsim;
    
    
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
        bsim = BlaineSimulator.getInstance();
        bsim.setAppController(ctrl);
        bsim.initTimelines();
    }
    
    public void setFXController (FXFControllerInterface anct){
        this.anct = anct;
    }
   
    public void updateProfile(){
        FXFBlaineDeviceController bdc = anct.getBlaineDevice();
        bdc.updateProfile();
    }
    
    
    public void initRuns(){
       ctrl.processSignal(new SMTraffic(0l, 0l, 0, "INITRUNS", this.getClass(),
                                   new VirnaPayload()));
    }
    
    protected Boolean running = false;
    protected Integer run_number = 0;
    private Integer max_runs = 2;
    protected boolean runsdone = false;
    private Random rand = new Random();
    
    
    //===========
    @smstate (state = "RESETBLAINE")
    public boolean st_resetBlaine(SMTraffic smm){
        log.info(String.format("Doing reset @ blaine  ==========================================================="));
        FXFController caller = (FXFController)smm.getPayload().vobject;
        if (caller == anct){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    anct.getBlaineDevice().activateLed("charge", false, true);
                    anct.getBlaineDevice().activateLed("fail", false, true);
                    anct.getBlaineDevice().activateLed("final", false, true);
                }
            });
            anct.getRunControl().initTimeList();
            anct.getRunControl().setSkipfirst(bfd.isSkipfirst());
        }
        return true;
    }
    
    
    @smstate (state = "DORUN")
    public boolean st_doRun(SMTraffic smm){
        log.info(String.format("Doing new run   ==========================================================="));
        if (bfd.getOpmode().equals("Operação Manual") || getRun_number() < max_runs){
            ctrl.processSignal(new SMTraffic(0l, 0l, 0, "CALLINTERRUN", this.getClass(), new VirnaPayload()));
            setRun_number((Integer) (getRun_number() + 1));
        }
        return true;
    }
    
    @smstate (state = "UPDATERUN")
    public boolean st_updateRun(SMTraffic smm){
        log.info(String.format("Updating run "));
  
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anct.getRunControl().addEntry(160 + ((rand.nextDouble()-0.5)*10), "Normal");
            }
        });
        
        return true;
    }
    
    
    @smstate (state = "ENDRUN")
    public boolean st_endRun(SMTraffic smm){
        log.info(String.format("ENDING -  run"));
  
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATETIME", this.getClass(), smm.getPayload()));
        FXFBlaineDeviceController bdv = anct.getBlaineDevice();
        
        if (bfd.getOpmode().equals("Operação Manual")){
            bdv.setMode(false, "Op. manual completada");
            setRunsdone(true);
            return true;
        }
        else if (bfd.getOpmode().equals("Atingir menor desvio") && bdv.verifyRSD()){
            bdv.setMode(false, "Menor desvio foi encontrado");
            setRunsdone(true);
            return true;
        }
        
        if (getRun_number() < max_runs){
            ctrl.processSignal(new SMTraffic(0l, 0l, 0, "DORUN", this.getClass(), new VirnaPayload()));
        }
        else{
            bdv.setMode(false, "Atingido max. de analises");
            setRunsdone(true);
            return true;
        }
            
        return true;
    }
    
    
    @smstate (state = "CALLINTERRUN")
    public boolean st_callInterrun(SMTraffic smm){
        log.info(String.format("Calling interrun"));
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXFCountdownTimer cdt = anct.getCDT();
                cdt.setOverevent("CHARGEFAILED");
                
                cdt.clearBars()
                .pushBar(Math.round(bfd.getInterrun()*1000l), Color.DODGERBLUE, "Pausa entre análises", "NULLEVENT", "NULLEVENT")
                .pushBar(Math.round(bfd.getAuto_timeout()*1000l), Color.GREEN, "Aguard. automação", "ENDINTERRUN", "CHARGEFAILED")
                .updateBars();
               
                cdt.triggerTimer();
            }
        });
        return true;
    }
    
    
    @smstate (state = "ENDINTERRUN")
    public boolean st_endInterrun(SMTraffic smm){
        log.info(String.format("Ending interrun"));
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "INITCHARGE", this.getClass(), new VirnaPayload()));
        return true;
    }
    
    
    @smstate (state = "INITCHARGE")
    public boolean st_initCharge(SMTraffic smm){
        log.info(String.format("Charge INIT"));
        bsim.triggerCharge();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anct.getBlaineDevice().activateLed("charge", true, true);
            }
        });
        
        //ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATERUN", this.getClass(), new VirnaPayload()));
        return true;
    }
    
    
    @smstate (state = "CHARGEFAILED")
    public boolean st_chargeFailed(SMTraffic smm){
        log.info(String.format("Chage Failed"));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anct.getBlaineDevice().activateLed("charge", false, true);
                anct.getBlaineDevice().activateLed("fail", true, true);
            }
        });
        FXFBlaineDeviceController bdv = anct.getBlaineDevice();
        bdv.setMode(true, "");
        //ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATERUN", this.getClass(), new VirnaPayload()));
        return true;
    }
    
    
    @smstate (state = "CHARGEREADY")
    public boolean st_chargeReady(SMTraffic smm){
        log.info(String.format("Charge is ready"));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anct.getBlaineDevice().activateLed("charge", false, true);
            }
        });
        
        FXFCountdownTimer cdt = anct.getCDT();
        cdt.clearCounter();
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "INITMEASURE", this.getClass(), new VirnaPayload()));
        return true;
        
    }
    
    @smstate (state = "INITMEASURE")
    public boolean st_initMeasure(SMTraffic smm){
        log.info(String.format("Measure INIT"));
        bsim.triggerMeasure();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXFCountdownTimer cdt = anct.getCDT();
                cdt.setOverevent("MEASUREFAILED");
                cdt.clearBars()
                .pushBar(Math.round(bfd.getTargetlow()*1000l), Color.GOLD, "Tempo < ideal", "NULLEVENT", "NULLEVENT")
                .pushBar(Math.round(bfd.getTargethigh()*1000l), Color.GREEN, "Tempo adequado", "NULLEVENT", "NULLEVENT")
                .pushBar(Math.round(bfd.getAn_timeout()*1000l), Color.RED, "Tempo > ideal", "NULLEVENT", "NULLEVENT")        
                .updateBars();
                anct.getBlaineDevice().activateLed("final", true, true);
                cdt.triggerTimer();
            }
        });
        //ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATERUN", this.getClass(), new VirnaPayload()));
        return true;
    }
    
    @smstate (state = "MEASUREREADY")
    public boolean st_measureReady(SMTraffic smm){
        log.info(String.format("Measure is ready"));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anct.getBlaineDevice().activateLed("final", false, true);
            }
        });
        
        FXFCountdownTimer cdt = anct.getCDT();
        cdt.clearCounter();
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATERUN", this.getClass(), new VirnaPayload()));
        return true;
        
    }
    
    
    @smstate (state = "INITRUNS")
    public boolean st_initRuns(SMTraffic smm){
        log.info(String.format("Init runs"));
        FXFBlaineDeviceController bdv = anct.getBlaineDevice();
        
        if (isRunsdone()){
            max_runs++;
        }
        else{
            setRun_number((Integer) 0);
            max_runs = bfd.getMaxruns();
        }
      
        bdv.setMode(true, "");
        anct.getRunControl().setSkipfirst(bfd.isSkipfirst());
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "DORUN", this.getClass(), new VirnaPayload()));
        
        return true;
    }
    
    
    
    
    
    public BlaineFieldDescriptor getBfd() {
        return bfd;
    }

    public void setBfd(BlaineFieldDescriptor bfd) {
        this.bfd = bfd;
    }
    
    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Integer getRun_number() {
        return run_number;
    }

    public void setRun_number(Integer run_number) {
        this.run_number = run_number;
    }

    public boolean isRunsdone() {
        return runsdone;
    }

    public void setRunsdone(boolean runsdone) {
        this.runsdone = runsdone;
    }

    
}



//public void triggerTimer(){
//
//        if (getRunning()) {
//            timer.stop();
//            setRunning((Boolean) false);
//        }
//        else{
//            timer.restart();
//            init_ts = System.currentTimeMillis();
//            log.info(String.format("Timer started @ "+ timestamp_format, init_ts));
//            setRunning((Boolean) true);
//        }
//    }
//    
//    
//    private void createTimer(){
//        timer = FxTimer.createPeriodic(Duration.ofMillis(timer_tick), () -> {
//            //LOG.info(String.format("Timer is running @ %d", System.currentTimeMillis()));
//            tickTimer();
//        });
//    }
//    
//    
//    private void tickTimer(){
//           
//       
//    }