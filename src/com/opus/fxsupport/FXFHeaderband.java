/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;


import com.opus.glyphs.FontAwesomeIcon;
import com.opus.glyphs.GlyphIcon;
import com.opus.glyphs.GlyphsBuilder;
import com.opus.glyphs.MaterialDesignIcon;
import com.opus.syssupport.PicnoUtils;
import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;




public class FXFHeaderband extends AnchorPane implements FXFField{

    private static final Logger LOG = Logger.getLogger(FXFHeaderband.class.getName());

    private FXFControllerInterface controller;
    private FXFWidgetManager wdgtmanager;
    private WidgetContext wctx;
    
    private Integer focusPosition = 0;
    public final FXFHeaderband instance;
   
    private String sid= "";
    
    private AnchorPane contextmenu;
    
    
    @FXML
    private Label bt_menu;
    
    @FXML
    private Label about;

    @FXML
    private HBox devicebox;

    @FXML
    private Label notifications;

    @FXML
    private AnchorPane notificationbox;
    
    @FXML
    private VBox notificationlist;
    
    @FXML
    private Label status;
    
    
    @FXML
    private AnchorPane menubox;

    
    @FXML
    private Text userid;
    @FXML
    private Label avatar;
    @FXML
    private Text pass_label;
    @FXML
    private PasswordField passfield;
    @FXML
    void avatar_action(MouseEvent event) {
        if (event.getClickCount() > 1){
            //LOG.info(String.format("Avatar event : %s",event.toString()));
            if (PicnoUtils.sudo_enabled){
                PicnoUtils.enableSudo("");
            }
            else{
                passfield.setText("");
                userid.setVisible(false);
                pass_label.setVisible(true);
                passfield.setVisible(true);
            }
        }
    }

    @FXML
    void pass_action(ActionEvent event) {
        LOG.info(String.format("Password event : %s", passfield.getText()));
        
        if (passfield.getText().equals("A1992")){
            pass_label.setText("Confirme a senha :");
            passfield.setText("");
        }
        else{
            PicnoUtils.enableSudo(passfield.getText());
            userid.setVisible(true);
            pass_label.setVisible(false);
            passfield.setVisible(false);
        }
        
    }
    
    
    @FXML
    void menu_action(MouseEvent event) {

    }
    

    @FXML
    private Text logintime;

        
    @FXML
    void about_action(MouseEvent event) {
        LOG.info(String.format(String.format("About requested")));
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "ADD_NOTIFICATION", this.getClass(),
                           new VirnaPayload().setString(
                                "Headerband&" + "INFO&" +
                                String.format("Test num : %d&", System.currentTimeMillis()) +
                                "void"        
                           )
        ));
    }
    
    public void updateAvatar (boolean su){
        
        if (su){
            //LOG.info("Setting avatar to SU");
            avatar.setStyle("-fx-effect: dropshadow(three-pass-box, red, 10.0, 0, 2.0, 2.0);");
        }
        else{
            //LOG.info("Setting avatar to Pawn");
            avatar.setStyle("-fx-effect: dropshadow(three-pass-box, -focused-shadow-color, 10.0, 0, 2.0, 2.0);");
        }    
    }
    
    public FXFHeaderband() {
        
        // Load the FXML
        URL fxmlUrl = getClass().getResource("FXFHeaderband.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlUrl);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        instance = this;
        
    }
    
    public void hideMenubox() { 
        if (menubox != null){
            menubox.setVisible(false);
        }
    }
    
    
    @FXML
    private void initialize() {
        
        bt_menu.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.LIST_UL, "white", 2));  
        notifications.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.BELL, "skyblue", 1.2));
        
        GlyphIcon avt = GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.USER, "black", 4);
        avatar.setGraphic(avt);
        
        
        // Notifications Services =====================================================================================
        notifications.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                //LOG.info(String.format("Notifications entered"));
                if (!notificationbox.isVisible() && buildNotificationList()){
                    notificationbox.setVisible(true);
                }
            }  
        });   
        
        notificationbox.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                //LOG.info(String.format("Notifications box exit"));
                if (notificationbox.isVisible()){
                    notificationbox.setVisible(false);
                    clearNotificationList();
                }
            }  
        });  
        
        updateNotificationIcon();
        notificationbox.setVisible(false);
       
        
        
        // Menu Services ==================================================================================
        bt_menu.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                //LOG.info(String.format("Menu entered"));
                if (!menubox.isVisible()){
                    loadMenu();
                    menubox.requestLayout();
                    menubox.setVisible(true);
                }
            }  
        });   
        
        menubox.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                //LOG.info(String.format("Menu box exit"));
                if (menubox.isVisible()){
                    if (ctxm != null && !ctxm.isKeepvisual()){
                        menubox.setVisible(false);
                    }
                }
            }  
        });  
       
        menubox.setVisible(false);
      
        
        // PUGINS ==============================================================================
        GlyphIcon gi = GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.WIFI, "white", 1.5);
        Tooltip.install(gi, new Tooltip("WIFI : 192.168.15.5 \nAccess point"));
        devicebox.getChildren().add(gi);
        
        GlyphIcon gi1 = GlyphsBuilder.getMaterialGlyph(MaterialDesignIcon.THERMOMETER, "white", 1.9);
        Tooltip.install(gi1, new Tooltip("Temperatura = 22.6 C"));
        devicebox.getChildren().add(gi1);
    
    }
    
    
    public void updateStatus(String message){
        
         Platform.runLater(new Runnable() {
              @Override
              public void run() {
                try {
                   status.setText(message); 
                } catch (Exception ex) {
                    LOG.severe("Failed to run update staus later...");
                }
              }    
        });  
    }
    
    
    private SystemMenu ctxm;
    private void loadMenu(){
    
        FXFWindowManager wm = FXFWindowManager.getInstance();
        FXFControllerInterface rootctrl = wm.getActiveRoot();
        if (ctxm != null){
            updateAvatar(PicnoUtils.sudo_enabled);
            menubox.getChildren().remove(ctxm);
        }        
        
        ctxm = rootctrl.getMenu(false);
        menubox.setTopAnchor(ctxm, 120.0);
        menubox.setLeftAnchor(ctxm, 5.0);
        menubox.getChildren().add(ctxm);
  
        
        //LOG.info(String.format("Menu Loaded..."));
        
    }
    
    
    private boolean buildNotificationList(){
        
        FXFWindowManager wm = FXFWindowManager.getInstance();
        ArrayList<TextFlow> notifs = wm.getNotifications();
        if (notifs.isEmpty()) return false;
        
        double boxheight = 0.0;
        for (TextFlow tf : notifs){
            HBox hbitem = getNotifItem(tf);
            boxheight += tf.getPrefHeight();
            notificationlist.getChildren().add(hbitem);
        }
       
        //LOG.info(String.format("Boxheight = %f", boxheight));
        
        //double boxsize = notifs.size()*50;
        double boxsize = boxheight;
        if (boxsize > 200) boxsize = 200;
        notificationlist.setPrefHeight(boxsize);
        
        
        notificationbox.setPrefHeight(notificationlist.getPrefHeight()+ 25);
        notificationbox.setPrefWidth(notificationlist.getPrefWidth()+ 25);
        
        return true;
    }
    
    private void clearNotificationList(){
        notificationlist.getChildren().clear();
    }
    
    
    public void updateNotificationIcon(int num){
        if (num == 0){
            notifications.setVisible(false);
        }
        else{
            notifications.setText(String.valueOf(num));
            notifications.setVisible(true);
        }
    }
    
    
    public void updateNotificationIcon(){
        FXFWindowManager wm = FXFWindowManager.getInstance();
        ArrayList<TextFlow> notifs = wm.getNotifications();
        if (notifs.isEmpty()){
            notifications.setVisible(false);
        }
        else{
            notifications.setText(String.valueOf(notifs.size()));
            notifications.setVisible(true);
        }
    }
    
    
    
    private HBox getNotifItem (TextFlow tf){
        
        HBox line = new HBox();
        
        Label closeicon = new Label();
        closeicon.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.CLOSE, "lightgray", 2));
        closeicon.setGraphicTextGap(0);
        closeicon.setAlignment(Pos.CENTER);
        closeicon.setPrefSize(40.0, 40.0);
        closeicon.getStyleClass().add("fxf-notif-icon");
        closeicon.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                LOG.info(String.format(String.format("Notifications erase requested on tf %d", tf.hashCode())));
                ctrl.processSignal(new SMTraffic(0l, 0l, 0, "REMOVE_NOTIFICATION", this.getClass(),
                                   new VirnaPayload().setObject(tf)));
                notificationbox.setVisible(false);
                clearNotificationList();
            }  
        });  
        
        line.getChildren().addAll(closeicon, tf);
        
        return line;        
    }
    
    
    
    @Override
    public void setManagement(FXFControllerInterface controller, Integer idx, WidgetContext wctx){
        this.controller = controller;
        this.focusPosition = idx;
        this.wctx = wctx;
        this.wdgtmanager = FXFWidgetManager.getInstance();
    }
    
    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
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




//notifications.setOnMouseClicked(new EventHandler<MouseEvent>(){
//            @Override 
//            public void handle(MouseEvent event) {
//                LOG.info(String.format("Notification Icon activated"));
//                if (notificationbox.isVisible()){
//                    notificationbox.setVisible(false);
//                }
//                else{
//                    notificationbox.setVisible(true);
//                }
//            }  
//        });        
//        notificationbox.setVisible(false);
//        