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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;




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
    private AnchorPane snack;

    @FXML
    private Text snacktext;

    @FXML
    private AnchorPane inputdialog;
    
    
    @FXML
    private AnchorPane menubox;

    @FXML
    private AnchorPane acvtlist;

    @FXML
    private HBox snap_box; 
    
    @FXML
    private ScrollPane acvt_scroll;
    
    
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
        
        
        
        
        
        
//        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "ADD_NOTIFICATION", this.getClass(),
//                           new VirnaPayload().setString(
//                                "Headerband&" + "INFO&" +
//                                String.format("Test num : %d&", System.currentTimeMillis()) +
//                                "void"        
//                           )
//        ));
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "TESTCALC", this.getClass(),
                           new VirnaPayload()
        ));


        
//        SimpleStringProperty result = showQuestionDialog("Controle das Análises", 
//                new DialogMessageBuilder()
//                        //.setHeight(400.0)
//                        .enableButton("ok", "Armazenar", "armazenar", true)
//                        .enableButton("cancel", "Descartar", "descartar", true)
//                        .enableButton("aux", "Cancelar", "cancelar", true)
//                        .add("Essa ultima análise ainda não foi armazenada.\n", "-fx-font-size: 16px;")
//                        .add("Você poderá tomar as seguintes atitudes.\n", "")
//                        .addSpacer(0)
//                        .add("\t\u2022 Descartar os resultados e iniciar nova análise.\n", "")
//                        .add("\t\u2022 Armazenar essa análise e iniciar uma nova.\n", "")
//                        .add("\t\u2022 Cancelar a operação.", "")
//   
//        );
        
//        SimpleStringProperty result = showListDialog("Escolha o arquivo a carregar", 
//                new FXFListDialogBuilder()
//                        .enableButton("cancel", "Cancelar", "cancel", true)
//                        .addFiles("/home/acp/PP200/Export", "absolute", "")
//        );
//        
        
//        FXFListDialogBuilder ldb = new FXFListDialogBuilder();
//        try {
//            ArrayList<String> files = PicnoUtils.scanDir("/home/acp/PP200/Export", "");
//            
//            LOG.info("Files loaded");
//        } catch (IOException ex) {
//            Logger.getLogger(FXFHeaderband.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        if (result != null){
//            result.addListener(new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue <? extends String> prop, String ov, String nv) {
//                    LOG.info(String.format("Valor : %s ", nv));
//                    hideInputDialog();
//                }
//            });
//        }




        //System.exit(0);
        
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
       
      
        snap_box.setOnScroll(event -> {
            if(event.getDeltaX() == 0 && event.getDeltaY() != 0) {
                acvt_scroll.setHvalue(acvt_scroll.getHvalue() - (event.getDeltaY() * 2) / this.snap_box.getWidth());
            }
        });
        
        
        
        acvtlist.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                //LOG.info(String.format("Notifications box exit"));
                if (acvtlist.isVisible()){
                    acvtlist.setVisible(false);
                }
            }  
        });  
        
        
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
    
    public AnchorPane getAcvtList() { return acvtlist;}
    public HBox getSnapBox(){ return snap_box;}
    
    
    
    public void showSnack(String message) {
        
        snack.setVisible(true);
        
        snacktext.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(2500),
            ae -> snack.setVisible(false)
        ));
        timeline.play();
        
    }
    
    public Parent currentdlgpane;
    public AnchorPane getInputDialog() { return inputdialog;}
    
    public void hideInputDialog(){    
        inputdialog.getChildren().remove(currentdlgpane);
        inputdialog.setVisible(false);
    }
    
    
    public SimpleStringProperty showInputDialog(String header, String value){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFInputDialog.fxml"));
            currentdlgpane = fxmlLoader.load();
            FXFInputDialogController dlgc = fxmlLoader.<FXFInputDialogController>getController();
            
            dlgc.setHeader(header);
            dlgc.setDefvalue(value);
            
            inputdialog.getChildren().add(currentdlgpane);
            inputdialog.setVisible(true);
            return dlgc.result;
      
        } catch (IOException ex) {
            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return null;
    }
    
    public SimpleStringProperty showQuestionDialog(String header, DialogMessageBuilder dmb){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFQuestionDialog.fxml"));
            currentdlgpane = fxmlLoader.load();
            FXFQuestionDialogController dlgc = fxmlLoader.<FXFQuestionDialogController>getController();
            
            dlgc.setHeader(header);
            dlgc.setStatus(dmb);
            AnchorPane ap = (AnchorPane)currentdlgpane;
            ap.setPrefHeight(dlgc.getDlg_height());
            
            inputdialog.getChildren().add(currentdlgpane);
            inputdialog.setVisible(true);
            //inputdialog.setPrefHeight(400);
            return dlgc.result;
      
        } catch (IOException ex) {
            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return null;
    }
    
    public SimpleStringProperty showListDialog(String header, FXFListDialogBuilder dmb){
        
        
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFListDialog.fxml"));
            currentdlgpane = fxmlLoader.load();
            FXFListDialogController dlgc = fxmlLoader.<FXFListDialogController>getController();
            
            dlgc.setHeader(header);
            dlgc.setStatus(dmb);
            AnchorPane ap = (AnchorPane)currentdlgpane;
            ap.setPrefHeight(dlgc.getDlg_height());
            
            inputdialog.getChildren().add(currentdlgpane);
            inputdialog.setVisible(true);
            //inputdialog.setPrefHeight(400);
            return dlgc.result;
      
        } catch (IOException ex) {
            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return null;
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