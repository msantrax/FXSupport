/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.opus.glyphs.FontAwesomeIcon;
import com.opus.glyphs.GlyphsBuilder;
import com.opus.syssupport.PicnoUtils;
import static com.opus.syssupport.PicnoUtils.user_name;
import com.opus.syssupport.ProfileResources;
import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.ToggleSwitch;

public class FXFBlaineDeviceController extends AnchorPane implements Initializable {

    
    private static final Logger LOG = Logger.getLogger(FXFBlaineDeviceController.class.getName());

    @FXML
    private AnchorPane blaine;

    @FXML
    private FXFCountdownTimer cdt;

    @FXML
    private Label led_fail;

    @FXML
    private Label led_charge;

    @FXML
    private Label led_final;


    @FXML
    private Label sidebar_btrun;

    @FXML
    private FXFCheckListViewNumber<String> runlist;
    
    @FXML
    private Label opmodo;
    

    
    @FXML
    void btrun_action(MouseEvent event) {
        BlaineDevice.getInstance().initRuns();
    }

  
    private FXFFieldDescriptor fxfd;
    
    private FXFFieldDescriptor analisefd;
    private FXFTextField analisefield;
    
    private FXFFieldDescriptor rsdfd;
    private FXFTextField rsdfield;
    
    
    private BlaineFieldDescriptor bfd;
    
    
    public FXFBlaineDeviceController() {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFBlaineDevice.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
      
    }

    
    // =========================================== WIDGET CONTEXT & MANAGEMENT =======================================
    private FXFControllerInterface controller;
    private WidgetContext wctx;
    private Integer focusPosition = 0;
    
    
    public void setManagement(FXFControllerInterface controller, Integer idx, WidgetContext wctx){
        this.controller = controller;
        this.focusPosition = idx;
        this.wctx = wctx;
    }
    
    public void setFocusPosition(Integer pos){
        this.focusPosition = pos;
    }
    
    public Integer getFocusPosition (){
        return focusPosition;
    }
    
    public void setFocus(boolean set){
        
        if (set){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    requestFocus();
                }
            });
        }
        else{
           setFocused(false); 
        }
    }

    
    public void activateLed (String id, boolean status, boolean blink){
        
        if (id.equals("fail")){
            if (status){
                led_fail.getStyleClass().remove("fxf-ledfail-off");
                led_fail.getStyleClass().add("fxf-ledfail-on");
            }
            else{
                led_fail.getStyleClass().remove("fxf-ledfail-on");
                led_fail.getStyleClass().add("fxf-ledfail-off");
            }
        }
        else if (id.equals("charge")){
            if (status){
                led_charge.getStyleClass().remove("fxf-ledcharge-off");
                led_charge.getStyleClass().add("fxf-ledcharge-on");
            }
            else{
                led_charge.getStyleClass().remove("fxf-ledcharge-on");
                led_charge.getStyleClass().add("fxf-ledcharge-off");
            }
        }
        else if (id.equals("final")){
            if (status){
                led_final.getStyleClass().remove("fxf-ledfinal-off");
                led_final.getStyleClass().add("fxf-ledfinal-on");
            }
            else{
                led_final.getStyleClass().remove("fxf-ledfinal-on");
                led_final.getStyleClass().add("fxf-ledfinal-off");
            }
        }
   
    }
    
    
    
    
    public void updateProfile(){
         
        if (analisefd != null){
            if (analisefd.isUse_windowrange()){
                Double target = analisefd.getRanges()[0];
                bfd.setTarget(target);
                bfd.setTargethigh(target + ((target/100) * analisefd.getRanges()[2]));
                bfd.setTargetlow(target - ((target/100) * analisefd.getRanges()[2]));
            }
            else{
                bfd.setTargethigh(analisefd.getRanges()[3]);
                bfd.setTargetlow(analisefd.getRanges()[0]);
                bfd.setTarget((analisefd.getRanges()[3] - analisefd.getRanges()[0]) / 2);
            }
        }
        
        if (bfd.getAn_timeout() < bfd.getTargethigh()){
            bfd.setAn_timeout(bfd.getTargethigh() + ( bfd.getTargethigh()/10.0));
        }
        
        if (rsdfd != null){
            if (!rsdfd.isUse_windowrange()){
                bfd.setTargetRSD(rsdfd.getRanges()[3]);
            }
        }
      
        opmodo.setText(bfd.getOpmode());
        
        
        fxfd.setCustom(bfd);
        BlaineDevice.getInstance().setBfd(bfd);
     
    }
    
    
    public void setMode ( boolean running, String message){
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (message != null && !message.isEmpty()){
                    opmodo.setText(message);
                }
                if (running){
                    sidebar_btrun.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.PAUSE, "black", 4));
                }
                else{
                    sidebar_btrun.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.PLAY, "black", 4));
                }
            }    
        });        
    }
    
    
    public void enableRun (boolean enable){
        sidebar_btrun.setDisable(!enable);
    }
    
    
    public boolean verifyRSD(){
        
        ArrayList<FXFAnaliseListItem> checked_items = runlist.getCheckedItems();
        
        if (checked_items.size() < 2) return false; 
     
        if (runlist.getRsd() < bfd.getTargetRSD()) return true;
        
        return false;
    }
    
    
    public void initProfile (FXFFieldDescriptor fxfd, FXFFieldDescriptor average, FXFFieldDescriptor rsd){
        
        this.fxfd = fxfd;
        this.analisefd = average;
        //this.analisefield = analisefd.getField(FXFTextField.class);
        this.rsdfd = rsd;
        //this.rsdfield = rsdfd.getField(FXFTextField.class);
        
        if (fxfd.getCustom() instanceof LinkedTreeMap){
            LinkedTreeMap ltm = (LinkedTreeMap)fxfd.getCustom();
            bfd = new BlaineFieldDescriptor();
            if (ltm.size() != 0){
                Gson gson = new Gson();
                JsonObject jobj = gson.toJsonTree(ltm).getAsJsonObject();
                bfd.setOpmode(jobj.get("opmode").getAsString());
                bfd.setMaxruns(jobj.get("maxruns").getAsInt());
                bfd.setSkipfirst(jobj.get("skipfirst").getAsBoolean());
                bfd.setInterrun(jobj.get("interrun").getAsDouble());
                bfd.setAn_timeout(jobj.get("an_timeout").getAsDouble());
                bfd.setAuto_timeout(jobj.get("auto_timeout").getAsDouble());
            }
        }
        else{
            bfd = (BlaineFieldDescriptor)fxfd.getCustom();
        }
        
        updateProfile();
        
    }
    
    
    
    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
        runlist.setManagement(ctrl);
        cdt.setCtrl((com.opus.syssupport.VirnaServiceProvider)ctrl);
    }
    
    public FXFCountdownTimer getCDT() {
        return cdt;
    }
    
    public FXFCheckListViewNumber<String> getRunControl() {
        return runlist;
    }
    
    
    
    public void initAnalises(){
        
        BlaineDevice bd = BlaineDevice.getInstance();
        bd.setRunning(false);
        bd.setRunsdone(false);
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                runlist.initTimeList();
                cdt.clearCounter();
                if (bfd != null){
                    opmodo.setText(bfd.getOpmode());
                }
            }
        });    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cdt = new FXFCountdownTimer();
        runlist = new FXFCheckListViewNumber<>();
        
        setTopAnchor(cdt, 7.0);
        setLeftAnchor(cdt, 130.0);
        getChildren().add(cdt);
        
        runlist.setPrefSize(210, 95);
        setTopAnchor(runlist, 190.0);
        setLeftAnchor(runlist, 80.0);
        getChildren().add(runlist);
        
        sidebar_btrun.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.PLAY, "black", 4));
        
        cdt.setPclock_mode("SECONDS");
        cdt.setSclock_mode("SEGMENT_SECONDS");
        
        
        cdt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    LOG.info(String.format("Blaine setup Context requested @ %f/%f",  e.getScreenX(), e.getScreenY()));                    
                    ContextMenu ctxm = getTimmingMenu(cdt, fxfd);
                    ctxm.show(cdt, e.getScreenX(), e.getScreenY());
                    e.consume();
                }
                else{
                    //if (desktop_context != null) desktop_context.hide();
                }
                e.consume();
            }
        });
        
        
        runlist.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    //LOG.info(String.format("Blaine mode setup requested @ %f/%f",  e.getScreenX(), e.getScreenY()));                    
                    ContextMenu ctxm = getModeMenu(runlist, fxfd);
                    ctxm.show(runlist, e.getScreenX(), e.getScreenY());
                    e.consume();
                }
                else{
                    //if (desktop_context != null) desktop_context.hide();
                }
                e.consume();
            }
        });
        
        
    }
    
    
    private HashSet<String> modes = new HashSet<>(Arrays.asList("Operação Manual", "Numero Fixo", 
            "Atingir menor desvio", "Descartar Discrepantes"));
    
    public ContextMenu getModeMenu(Node field, FXFFieldDescriptor fxfd){
        
        ContextMenu ctxm = new ContextMenu();
        
        ArrayList<MenuItem> menuitems = new ArrayList<>();
        
        ctxm.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, e -> {
            e.consume();
        });
        
        if (!PicnoUtils.sudo_enabled){
            MenuItem witem1 = new MenuItem("Não há permissão para a alteração de dados desse Desktop");
            witem1.setDisable(true);
            menuitems.add(witem1);
            MenuItem witem2 = new MenuItem("Solicite autenticação como administrador");
            witem2.setDisable(true);
            menuitems.add(witem2);
            
            ctxm.getItems().addAll(menuitems);
            return ctxm;
        }
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "FLAGACTIVITY", this.getClass(),
                                new VirnaPayload().setString("Contextmenu:An_Mode:" + user_name)
        )); 
        
        menuitems.add(FXFController.getContextMenuLabel ("Configuração da Análise " , true));
        
        
        
        ChoiceBox modetype = FXFController.addContextChoiceBox(menuitems, "Modos de calculo do resultado :", 
                bfd.getOpmode(), modes);        
        modetype.setOnAction((event) -> {
            ctxm.hide();
            bfd.setOpmode((String)modetype.getValue());
            updateProfile();
            ProfileResources pr = PicnoUtils.profile_resources;
            pr.updateProfile(controller.getProfileID());
        });
        
        
        
        ToggleSwitch mn_skipfirst = FXFController.addContextCheckBox (menuitems, "Descartar a primeira", bfd.isSkipfirst());
                mn_skipfirst.selectedProperty().addListener(ev -> {
                    ctxm.hide();
                    bfd.setSkipfirst(mn_skipfirst.isSelected());
                    ProfileResources pr = PicnoUtils.profile_resources;
                    pr.updateProfile(controller.getProfileID());
                });
        
                
        TextField  mn_maxruns = FXFController.addContextValidatedTextField (menuitems, 
            "Máximo de ensaios", "", 
            FXFController.convertFromInteger(bfd.getMaxruns(),
                "Campo maxruns do perfil não está definido, usando default",
                "5")
        );
        mn_maxruns.setOnAction((event) -> {
            ctxm.hide();
            bfd.setMaxruns(FXFController.convertToInteger(mn_maxruns.getText(), null, 3));
            ProfileResources pr = PicnoUtils.profile_resources;
            pr.updateProfile(controller.getProfileID());
        });
       
        
        ctxm.getItems().addAll(menuitems);
  
        return ctxm;
      
    }
    
    
    
    
    public ContextMenu getTimmingMenu(Node field, FXFFieldDescriptor fxfd){
    
        ContextMenu ctxm = new ContextMenu();
        
        ArrayList<MenuItem> menuitems = new ArrayList<>();
        
        ctxm.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, e -> {
            e.consume();
        });
        
        if (!PicnoUtils.sudo_enabled){
            MenuItem witem1 = new MenuItem("Não há permissão para a alteração de dados desse Desktop");
            witem1.setDisable(true);
            menuitems.add(witem1);
            MenuItem witem2 = new MenuItem("Solicite autenticação como administrador");
            witem2.setDisable(true);
            menuitems.add(witem2);
            
            ctxm.getItems().addAll(menuitems);
            return ctxm;
        }
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "FLAGACTIVITY", this.getClass(),
                                new VirnaPayload().setString("Contextmenu:An_Timming:" + user_name)
        )); 
        
        
        
        menuitems.add(FXFController.getContextMenuLabel ("Temporização das Analises " , true));
        
        
        TextField  mn_interrun = FXFController.addContextValidatedTextField (menuitems, 
            "Pausa entre Analises", "seg.", 
            FXFController.convertFromDouble(bfd.getInterrun(),
                "Campo interrun do perfil não está definido, usando default",
                "3.0")
        );
        mn_interrun.setOnAction((event) -> {
            ctxm.hide();
            bfd.setInterrun(FXFController.convertToDouble(mn_interrun.getText(), null, 3.0));
            ProfileResources pr = PicnoUtils.profile_resources;
            pr.updateProfile(controller.getProfileID());
        });
        
        TextField  mn_autotimeout = FXFController.addContextValidatedTextField (menuitems, 
            "Timeout da automação", "seg.", 
            FXFController.convertFromDouble(bfd.getAuto_timeout(),
                "Campo de timeout da automação no perfil não está definido, usando default",
                "1.5")
        );
        mn_autotimeout.setOnAction((event) -> {
            ctxm.hide();
            bfd.setAuto_timeout(FXFController.convertToDouble(mn_autotimeout.getText(), null, 1.5));
            ProfileResources pr = PicnoUtils.profile_resources;
            pr.updateProfile(controller.getProfileID());
        });
        
        TextField  mn_antimeout = FXFController.addContextValidatedTextField (menuitems, 
            "Tempo máximo de analise", "seg.", 
            FXFController.convertFromDouble(bfd.getAn_timeout(),
                "Campo de tempo máximo de analise no perfil não está definido, usando default",
                "200")
        );
        mn_antimeout.setOnAction((event) -> {
            ctxm.hide();
            bfd.setAn_timeout(FXFController.convertToDouble(mn_antimeout.getText(), null, 200.0));
            ProfileResources pr = PicnoUtils.profile_resources;
            pr.updateProfile(controller.getProfileID());
        });
        
        menuitems.add(new SeparatorMenuItem());
  
        final String deftimming = "Use campo de Média";
        ChoiceBox timmingtype = FXFController.addContextChoiceBox(menuitems, "Tempos estimados de analise :", 
                deftimming, new HashSet<String>(Arrays.asList(deftimming)));        
        timmingtype.setOnAction((event) -> {
            ctxm.hide();
            
        });
        
        MenuItem witem1 = new MenuItem(String.format("Máximo estimado : %5.2f seg.", bfd.getTargethigh()));
        witem1.setDisable(true);
        menuitems.add(witem1);
        MenuItem witem2 = new MenuItem(String.format("Minimo estimado : %5.2f seg.", bfd.getTargetlow()));
        witem2.setDisable(true);
        menuitems.add(witem2);
        
        
        ctxm.getItems().addAll(menuitems);
  
        return ctxm;
        
    }


//    @Override
//    public void updateValue(String value, boolean required) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getValue() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getSid() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setSid(String sid) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public FXFFieldDescriptor getFxfd() {
        return fxfd;
    }

    public void setFxfd(FXFFieldDescriptor fxfd) {
        this.fxfd = fxfd;
    }
    
    
    
    
}

//<FXFBlaineDeviceController fx:id="blainedevice" layoutX="800.0" layoutY="153.0" maxHeight="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="261.0" prefWidth="120.0" />