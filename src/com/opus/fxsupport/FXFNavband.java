/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;


import com.opus.glyphs.FontAwesomeIcon;
import com.opus.glyphs.GlyphsBuilder;
import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class FXFNavband extends HBox implements FXFField{

    private static final Logger LOG = Logger.getLogger(FXFNavband.class.getName());

    private FXFControllerInterface parent;
    private FXFWidgetManager wdgtmanager;
    private WidgetContext wctx;
    
    private Integer focusPosition = 0;
    public final FXFNavband instance;
   
    private String sid= "";
    
    
    
            
    @FXML
    private HBox navband;

    @FXML
    private Label bt_back;

    @FXML
    private Label bt_home;

    @FXML
    private Label bt_list;

    @FXML
    void list_action(MouseEvent event) {
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "NAV_ACTION", this.getClass(),
                                   new VirnaPayload().setString("List")));
    }

    @FXML
    void home_action(MouseEvent event) {
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "NAV_ACTION", this.getClass(),
                                   new VirnaPayload().setString("Home")));
    }
    
    @FXML
    void back_action(MouseEvent event) {
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "NAV_ACTION", this.getClass(),
                                   new VirnaPayload().setString("Back")));
    }
    
    
    public FXFNavband() {
        
        // Load the FXML
        URL fxmlUrl = getClass().getResource("FXFNavband.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlUrl);
        loader.setRoot(this);
        loader.setController(this);   
        //getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
        
        try {
            loader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        instance = this;
        
    }
    
    @FXML
    private void initialize() {

        bt_back.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.CHEVRON_LEFT, "white", 2.0));
        bt_home.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.CIRCLE_ALT, "white", 2.0));
        bt_list.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.SHARE_SQUARE_ALT, "white", 2.0));
    }
   
    
    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
    }
    
    
    
    @Override
    public void setManagement(FXFControllerInterface controller, Integer idx, WidgetContext wctx){
        this.parent = controller;
        this.focusPosition = idx;
        this.wctx = wctx;
        this.wdgtmanager = FXFWidgetManager.getInstance();
    }
    
    @Override
    public void setFocusPosition(Integer pos){
        this.focusPosition = pos;
    }
    
    @Override
    public Integer getFocusPosition (){
        return focusPosition;
    }

    @Override
    public void setFocus(boolean set) {
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

    @Override
    public void updateValue(String value, boolean required) {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                
//            }
//        });
    }

    
    @Override
    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public String getValue() {
        return "navband";
    }

    @Override
    public ContextMenu getConfigurationMenu(Control field, FXFFieldDescriptor fxfd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
}



//(base) opus@opus:/Bascon/ASVP/Develop/JavaFX/scenebuilder/app/build/libs$ /opt/jdk-11.0.2/bin/java  --module-path /opt/javafx-sdk-11.0.2/lib/ --add-modules javafx.web,javafx.fxml,javafx.swing,javafx.media --add-opens=javafx.fxml/javafx.fxml=ALL-UNNAMED -jar ./scenebuilder-11.0.0-SNAPSHOT.jar




