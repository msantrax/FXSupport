/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.glyphs.FontAwesomeIcon;
import com.opus.glyphs.GlyphsBuilder;
import com.opus.syssupport.VirnaServiceProvider;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private Label led_valve;

    @FXML
    private Label led_motor;

    @FXML
    private Label sidebar_btrun;

    @FXML
    private FXFCheckListViewNumber<String> checklist1;

    
    @FXML
    void btrun_action(MouseEvent event) {
        BlaineDevice.getInstance().initRuns();
    }

  
    
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

    
    
    
    
    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
        checklist1.setManagement(ctrl);
        cdt.setCtrl((com.opus.syssupport.VirnaServiceProvider)ctrl);
    }
    
    public FXFCountdownTimer getCDT() {
        return cdt;
    }
    
    public FXFCheckListViewNumber<String> getRunControl() {
        return checklist1;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cdt = new FXFCountdownTimer();
        checklist1 = new FXFCheckListViewNumber<>();
        
        sidebar_btrun.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.PLAY, "black", 4));
        
        cdt.setPclock_mode("SECONDS");
        cdt.setSclock_mode("SEGMENT_SECONDS");
        
        
//        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                if (e.getButton() == MouseButton.SECONDARY) {
//                    LOG.info(String.format("Blaine setup Context requested @ %f/%f",  e.getScreenX(), e.getScreenY()));                    
//                    
//                }
//                else{
//                    //if (desktop_context != null) desktop_context.hide();
//                }
//                e.consume();
//            }
//        });
        
    }
    
    
    public ContextMenu getConfigurationMenu(Control field, FXFFieldDescriptor fxfd){
    
        ContextMenu ctxm = new ContextMenu();
        
        ArrayList<MenuItem> menuitems = new ArrayList<>();
        
        ctxm.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, e -> {
            e.consume();
        });
        
        menuitems.add(FXFController.getContextMenuLabel ("Perfil do Item " , true));
        
        
        
        
        
        
        
        ctxm.getItems().addAll(menuitems);
        
        //fxfd.setCtxm(ctxm);
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
    
    
    
    
}

//<FXFBlaineDeviceController fx:id="blainedevice" layoutX="800.0" layoutY="153.0" maxHeight="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="261.0" prefWidth="120.0" />