/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import static com.opus.fxsupport.FXFWindowManager.stage;
import com.opus.syssupport.ActivitiesMap;
import com.opus.syssupport.ActivityDescriptor;
import com.opus.syssupport.PicnoUtils;
import com.opus.syssupport.ProfileResources;
import com.opus.syssupport.VirnaServiceProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author opus
 */
public class FXFWindowManager {

    private static final Logger LOG = Logger.getLogger(FXFWindowManager.class.getName());

    private static FXFWindowManager instance; 
    public static FXFWindowManager getInstance(){
        if (instance == null) {instance = new FXFWindowManager();}
        return instance;
    }
   
    // Application Stage
    public static Stage stage; 
     
    // Default Window
    private FXFWindowDescriptor defaultwindow;
    
    private FXFControllerInterface activefxcontroller;
    
    private ArrayList<TextFlow> notifications;
    
    
    public FXFWindowManager()  {
        
        canvasesmap = new LinkedHashMap<>();
        
        notifications = new ArrayList<>();
   
        LOG.setLevel(Level.FINE);
        instance = this;     
    }

    
    
    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
    }
    
    
    // ========================================== NOTIFICATIONS ========================================================= 
    
    public void addNotification (String source, String type,  String message, String tip){
        
        TextFlow tfa;
        
        tfa = buildNotification (source, type, message, tip);
        notifications.add(tfa);
        getHeaderBand().updateNotificationIcon(notifications.size());
        if (type.equals("SEVERE")){
            getHeaderBand().showSnack(message);
        }
        
    }
    
    
    public TextFlow buildNotification (String source, String type,  String message, String tip){
        
        TextFlow tf = new TextFlow();
        tf.getStyleClass().add("fxf-notif-text");
        double boxheight = 40.0;
        
        Color headercolor = Color.GRAY;
        String timestamp = String.format(" @ %1$td-%1$tm-%1$tY %1$tH:%1$tM:%1$tS:%1$tL", System.currentTimeMillis());
        String stype = "Notifica????o : ";
        
        if (type.toUpperCase().equals("SEVERE")){
            headercolor = Color.RED;
            stype = "Falha : ";
        }
        else if (type.toUpperCase().equals("INFO")){
            headercolor = Color.DODGERBLUE;
            stype = "Info : ";
        }
   
        Text txttype = new Text(stype); 
        txttype.setFill(headercolor); 
        txttype.setFont(Font.font("Verdana", FontWeight.BOLD, 12)); 
        tf.getChildren().add(txttype);
        
        Text header = new Text(source + timestamp + "\n"); 
        header.setFill(headercolor); 
        header.setFont(Font.font("Verdana", 12)); 
        tf.getChildren().add(header);
        
        if (tip !=null) message = message + "\n";
        Text messageline = new Text(message); 
        messageline.setFill(Color.GRAY); 
        messageline.setFont(Font.font("Verdana", 10));
        tf.getChildren().add(messageline);
        
        if (tip != null){
            Text tipline = new Text(tip); 
            tipline.setFill(Color.GREEN); 
            tipline.setFont(Font.font("Verdana", 8));
            tf.getChildren().add(tipline);
            boxheight = 50.0;
        }
        
        tf.setPrefHeight(boxheight);
        
        return tf;
    }
    
    public ArrayList<TextFlow> getNotifications(){
        return notifications;
    }
    
    
    private FXFHeaderband headerband;
    public FXFHeaderband getHeaderBand(){ 
        return headerband;
    }
    
    
    public ActivityDescriptor locateActivity (String classtype, String arg){
        
        LinkedHashMap<String, ActivityDescriptor> activitiesmap = ActivitiesMap.activitiesmap;
        
        ActivityDescriptor ad = activitiesmap.get("analise_blaine");
        if (arg == null) arg = "";
        
        for (String s : activitiesmap.keySet()){
            ad = activitiesmap.get(s);
            //String atype = (ad.getArgtype() == null) ? "": ad.getArgtype();
            if (ad.getFxcontrollerName().equals(classtype) && arg.contains(ad.getArgument_prefix())){
                return ad;
            }
        }
        return ad;
    }
    
    
    // ========================================================= CANVAS MAP ===================================================
    private LinkedHashMap<String, FXFWindowDescriptor>canvasesmap;
    
    public LinkedHashMap<String, FXFWindowDescriptor> getCanvasesmap() {
        return canvasesmap;
    }
    
    
    
    public void updateCanvasMap(){
        
        LinkedHashMap<String, FXFWindowDescriptor> canvasesmaptemp;
        String cn = "";
        canvasesmap = new LinkedHashMap<>();
        
        try {
            FXFWindowDescriptor fxwd = addWindow(null, FXCanvasController.class, null);
            LauncherConfig lc = PicnoUtils.launcherconfig;
            for (LauncherItem li : lc.getItems()){
                cn = li.getAppclass();
                Class c1 = Class.forName("pp200a." + cn);
                addWindow(PicnoUtils.appclass.getResource(cn +".fxml"), c1,  li);
            }
        } catch (Exception ex) {
            LOG.severe(String.format("Unable to load activity %s due %s", cn, ex.getClass().toString()));
        }
    }
    
    
    public FXFWindowDescriptor addWindow(URL location, Class ctrlclass, LauncherItem li) throws IOException, 
            NoSuchMethodException, InstantiationException, 
            IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        
        FXFControllerInterface fxfc;
        
        FXFWindowDescriptor descriptor = new FXFWindowDescriptor()
                .setUrl(location)
                .setControllerclass(ctrlclass);
        
        descriptor.setLauncheritem(li);
        
        if (location != null){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            descriptor.setLoader(fxmlLoader);
            
            Constructor c = ctrlclass.getConstructor(FXMLLoader.class, String.class);
            fxfc = (FXFControllerInterface)c.newInstance(fxmlLoader, li.getArgument());        
            descriptor.setFxcontroller(fxfc);
            fxmlLoader.setController(fxfc);    
        }
        else{
            Constructor c = ctrlclass.getConstructor();
            fxfc = (FXFControllerInterface)c.newInstance();        
            descriptor.setFxcontroller(fxfc);
        }
  
        if (li == null){
            getCanvasesmap().put(fxfc.getUID(), descriptor);
        }
        else{
            getCanvasesmap().put(li.getConfigid(), descriptor);
        }
        
        return descriptor;
    }
   
    
    public FXFControllerInterface getActiveRoot(){ return activefxcontroller; }
    
    
    public void test1()throws Exception{
        
        ProfileResources prs = PicnoUtils.profile_resources;
        LauncherConfig lc = PicnoUtils.launcherconfig;
        ArrayList<LauncherItem> lit = PicnoUtils.launcherconfig.getItems();
        
        
        loadWindow (lit.get(0));
        loadWindow (lit.get(1));
        loadWindow (lit.get(2));
        loadWindow (lit.get(4));
        
        LOG.info(String.format("Auto Loading launchers finalized"));
        
    }
    
    
    private void loadWindow (LauncherItem li){
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                try {
                    LOG.info(String.format("Auto Loading activity %s", li.getIconlabel()));
                    activateWindow(li.getConfigid(), li , false);
                } catch (IOException ex) {
                    Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    
    
    public void cycleActivity(){
        
        if (acvtring.isEmpty()) return;
        
        LauncherItem li = acvtring.pop();
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                try {
                    activateWindow(li.getConfigid(), li , true);
                } catch (IOException ex) {
                    Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                acvtring.addLast(li);
            }
        });
    }
    
    private ObservableList<Rectangle> list = FXCollections.<Rectangle>observableArrayList();
    public void showActivityList(){
        
        if (acvtring.isEmpty()) return;
        AnchorPane acvtlist = headerband.getAcvtList();
        
        
        if (acvtlist.isVisible()){
            acvtlist.setVisible(false);
        }
        else{
            HBox snap_box = headerband.getSnapBox();
            snap_box.getChildren().clear();
            list.clear();
            for (LauncherItem li : acvtring){
                FXFWindowDescriptor wd = getCanvasesmap().get(li.getConfigid());
                if (wd != null){
                    if (wd.getSnapshot() == null){
                        LOG.info(String.format("Doing snapshot on %s", li.getIconlabel()));
                        WritableImage image = wd.getRootpane().snapshot(new SnapshotParameters(), null);
                        Rectangle rectangle = new Rectangle(0, 0, 220, 120);
                        ImagePattern pattern = new ImagePattern(image);
                        rectangle.setFill(pattern);
                        rectangle.setUserData(li.getConfigid());
                        wd.setSnapshot(rectangle);
                        rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                String window = (String)rectangle.getUserData();
                                try {
                                    if (e.getButton().equals(MouseButton.PRIMARY)){
                                        LOG.info(String.format("Select window action on : %s", window));
                                        activateWindow(window, null, true);
                                    }
                                    else{
                                        LOG.info(String.format("Delete window action on : %s", window));
                                        deleteWindow(window);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                acvtlist.setVisible(false);
                            }
                        });
                    }
                    list.add(wd.getSnapshot());
                    snap_box.getChildren().add(wd.getSnapshot());
                }
            } 
            acvtlist.setVisible(true);
        }
    }
    
    public void deleteWindow (String id) throws Exception{
        
        FXFWindowDescriptor wd = getCanvasesmap().get(id);
        
        if (wd != null){
            if (activefxcontroller.equals(wd.getFxcontroller())){
                LOG.info(String.format("Window %s is active now", id));
                activateWindow("CANVAS", null, true);
            }
            
            LOG.info(String.format("Deleting window %s", id));
            LauncherItem lit = wd.getLauncheritem();
            acvtring.remove(lit);
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(wd.getUrl());
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            wd.setLoader(fxmlLoader);
            
            Constructor c = wd.getControllerclass().getConstructor(FXMLLoader.class, String.class);
            FXFControllerInterface fxfc = (FXFControllerInterface)c.newInstance(fxmlLoader, wd.getLauncheritem().getArgument());        
            wd.setFxcontroller(fxfc);
            fxmlLoader.setController(fxfc);  
            wd.setLoaded(false);
            
            
        }
        
        
    }
    
    
    ArrayDeque<LauncherItem> acvtring = new ArrayDeque<>();
    
    public void activateWindow(String id, LauncherItem li , boolean show) throws IOException{
        
        Pane rootpane;
        
        FXFWindowDescriptor wd = getCanvasesmap().get(id);
    
        if (wd != null){
            if (wd.isLoaded()){
                
                if (activefxcontroller.equals(wd.getFxcontroller())) return;
                
                LOG.info(String.format("===== Loaded WD id = %s", id));
                wd.getHeaderband().updateNotificationIcon(notifications.size());
                headerband = wd.getHeaderband();
                activefxcontroller = wd.getFxcontroller();
                activefxcontroller.activateModel();
                
                if (show){
                    stage.setScene(wd.getScene());
    //                stage.setFullScreen(true);
    //                stage.setFullScreenExitHint("");
                    stage.show();
                }
                
            }
            else{
                LOG.info(String.format("===== NOT Loaded WD id = %s", id));
                if (wd.getLoader() != null){
                    rootpane = (wd.getLoader().load(wd.getUrl().openStream()));
                    
                }
                else{
                    rootpane = ((Pane)wd.getFxcontroller());
                }
                wd.setRootpane(rootpane);
                wd.getFxcontroller().setAppController(ctrl);
                activefxcontroller = wd.getFxcontroller();
                activefxcontroller.activateModel();
                activefxcontroller.setLauncher(li);
                
                headerband = new FXFHeaderband();
                headerband.setAppController(ctrl);
                wd.setHeaderband(headerband);
                                
                FXFNavband navband = new FXFNavband();
                navband.setAppController(ctrl);
                
                double rootheight = 768.0 -(navband.getPrefHeight() + headerband.getPrefHeight());
                rootpane.setPrefHeight(rootheight);
                
                //wd.getFxcontroller().update();

                StackPane.setAlignment(wd.getRootpane(), Pos.CENTER_LEFT);
                StackPane.setMargin(wd.getRootpane(), new Insets(navband.getPrefHeight()-10, 0.0, 0.0, 0.0));
                
                StackPane.setAlignment(navband, Pos.BOTTOM_LEFT);
                StackPane.setAlignment(headerband, Pos.TOP_LEFT);
                StackPane canvas1 = new StackPane(wd.getRootpane(), navband, headerband);
                canvas1.setPrefSize(1366.0, 768.0);
                
                Scene scene = new Scene(canvas1);   
                scene.getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
                wd.setScene(scene);
                wd.getFxcontroller().update(scene);
                wd.setLoaded(true);
                
                if (!id.equals("CANVAS")){
                    acvtring.push(li);
                }
                
                if (show){
                    stage.setScene(scene);
                    //stage.setFullScreen(true);
                    //stage.setFullScreenExitHint("");
                    stage.show();
                }
                
            }
        }
        else{
            stage.setScene(defaultwindow.getScene());
            stage.show();
        }
        
    }
    
    
    
    
    public void updateWindow(String id){  
        FXFWindowDescriptor wd = getCanvasesmap().get(id);
        wd.getFxcontroller().update();    
    }
   
    public FXFControllerInterface getController(String id){
        
        FXFWindowDescriptor wd = getCanvasesmap().get(id);
        if (wd != null){
            return wd.getFxcontroller();
        }
        else{
            return defaultwindow.getFxcontroller();
        }
        
    }
    
    
    
    // ===================================================== TOOLS ==========================================================
    
    public void showFileChooser(){
        
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
    }
    
    
    public String showIconChooserDialog(){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFIconChooser.fxml"));
            Parent parent = fxmlLoader.load();
            FXFIconChooserController ICController = fxmlLoader.<FXFIconChooserController>getController();
            
            Scene scene = new Scene(parent); 
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
            
            Stage dlgstage = new Stage(StageStyle.TRANSPARENT);            
            dlgstage.initModality(Modality.WINDOW_MODAL);
            dlgstage.initOwner(stage);
            
            dlgstage.setScene(scene);
            ICController.setStage(dlgstage);
            
            //stage.getScene().getRoot().setEffect(new BoxBlur());
            dlgstage.showAndWait();
            
            return ICController.getResult();
            
        } catch (IOException ex) {
            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "FXFIconChooser failed";
    }
    
    
    public String showImageChooserDialog(){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFImageChooser.fxml"));
            Parent parent = fxmlLoader.load();
            FXFImageChooserController ICController = fxmlLoader.<FXFImageChooserController>getController();
            
            Scene scene = new Scene(parent); 
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
            
            Stage dlgstage = new Stage(StageStyle.TRANSPARENT);            
            dlgstage.initModality(Modality.WINDOW_MODAL);
            dlgstage.initOwner(stage);
            
            dlgstage.setScene(scene);
            ICController.setStage(dlgstage);
            
            //stage.getScene().getRoot().setEffect(new BoxBlur());
            dlgstage.showAndWait();
            
            return ICController.getResult();
            
        } catch (IOException ex) {
            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "FXFImageChooser failed";
    }
    
    
    public String showAutoCompleteEditorDialog(FXFFieldDescriptor fxfd){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFAutocompleteEditor.fxml"));
            Parent parent = fxmlLoader.load();
            FXFAutocompleteEditorController ICController = fxmlLoader.<FXFAutocompleteEditorController>getController();
            
            
            Scene scene = new Scene(parent); 
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
            
            Stage dlgstage = new Stage(StageStyle.TRANSPARENT);            
            dlgstage.initModality(Modality.WINDOW_MODAL);
            dlgstage.initOwner(stage);
            
            dlgstage.setScene(scene);
            ICController.setStage(dlgstage);
            ICController.setPayload(fxfd);
            
            //stage.getScene().getRoot().setEffect(new BoxBlur());
            dlgstage.showAndWait();
            
            return ICController.getResult();
            
        } catch (IOException ex) {
            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "FXFAutocompleteEditor failed";
    }
    
    
    
    public String showFormulaEditorDialog(FXFFieldDescriptor fxfd){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFFormulaEditor.fxml"));
            Parent parent = fxmlLoader.load();
            FXFFormulaEditorController ICController = fxmlLoader.<FXFFormulaEditorController>getController();
            
            
            Scene scene = new Scene(parent); 
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
            
            Stage dlgstage = new Stage(StageStyle.TRANSPARENT);            
            dlgstage.initModality(Modality.WINDOW_MODAL);
            dlgstage.initOwner(stage);
            
            dlgstage.setScene(scene);
            ICController.setStage(dlgstage);
            ICController.setPayload(fxfd);
            
            //stage.getScene().getRoot().setEffect(new BoxBlur());
            dlgstage.showAndWait();
            
            return ICController.getResult();
            
        } catch (IOException ex) {
            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "FXFFormulaEditor failed";
    }
    
    
    
  
    
    
}


//public String showInputDialog(String header, String defaultvalue){
//        
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFInputDialog.fxml"));
//            Parent parent = fxmlLoader.load();
//            FXFInputDialogController ICController = fxmlLoader.<FXFInputDialogController>getController();
//            
//            
//            Scene scene = new Scene(parent); 
//            scene.setFill(Color.TRANSPARENT);
//            scene.getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
//            
//            
//            Stage dlgstage = new Stage(StageStyle.TRANSPARENT);            
//            dlgstage.initModality(Modality.WINDOW_MODAL);
//            dlgstage.initOwner(stage);
//            
//            dlgstage.setScene(scene);
//            ICController.setStage(dlgstage);
//            ICController.setHeader(header);
//            ICController.setDefvalue(defaultvalue);
//            
//            //stage.getScene().getRoot().setEffect(new BoxBlur());
//            dlgstage.showAndWait();
//            
//            return "";
//            
//        } catch (IOException ex) {
//            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return "FXFInputDialog failed";
//    }
//





//    
//    public void showSnack(String message){
//        
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFSnack.fxml"));
//            Parent parent = fxmlLoader.load();
//            FXFSnackController SnackController = fxmlLoader.<FXFSnackController>getController();
//            
//            Scene scene = new Scene(parent); 
//            scene.setFill(Color.TRANSPARENT);
//            scene.getStylesheets().add("com/opus/fxsupport/fxfsupport.css");
//            
//            
//            Stage dlgstage = new Stage(StageStyle.TRANSPARENT);            
//            dlgstage.initModality(Modality.WINDOW_MODAL);
//            dlgstage.initOwner(stage);
//            
//            
//            dlgstage.setScene(scene);
//            SnackController.setStage(dlgstage);
//            SnackController.setmessage(message);
//            
//            //stage.getScene().getRoot().setEffect(new BoxBlur());
//            
//            
////            dlgstage.setMaxWidth(500.0);
////            dlgstage.setMaxHeight(100.0);
//            
//            dlgstage.showAndWait();
//            
//        } catch (IOException ex) {
//            Logger.getLogger(FXFWindowManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//     
//    }
//    
    










        
//        try {
//            addWindow (null, FXCanvasController.class);
//            defaultwindow = canvasesmap.get("CANVAS");
//            if (defaultwindow == null) {
//                LOG.severe("Failed to load default window");
//                System.exit(1);
//            }
//        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalArgumentException | 
//                        IllegalAccessException | InvocationTargetException ex) {
//            LOG.severe("Failed to load default window");
//            System.exit(1);
//        }
       


//public void initCanvas() throws Exception {
//        
//        FXFWindowDescriptor fxwd = addWindow(null, FXCanvasController.class, null);
//        FXCanvasController fxcc = (FXCanvasController)fxwd.getFxcontroller();       
//        
//        LauncherConfig lc = new LauncherConfig().getDefault();
//        
//        for (LauncherItem li : lc.getItems()){
//            String cn = li.getAppclass();
//            Class c1 = Class.forName("pp200a."+ cn);
//            addWindow(getClass().getResource(cn +".fxml"), c1,  li);
//        }
//  
//        activateWindow("CANVAS");
//        
//    }
//    



//private void notiftest(){
//        
//        TextFlow tfa;
//        
//        tfa = buildNotification ("Window manager", "SEVERE",  
//                "Falha ao carregar arquivo de wallpaper - Falha ao carregar arquivo de wallpaper", 
//                "Corrija entrada no arquivo de configura????o");
//        notifications.add(tfa);
//        
//        tfa = buildNotification ("Comm Driver", "INFO",  "Porta ttyS2 dispon??vel para uso", null);
//        notifications.add(tfa);
//        
//        for (int i = 0; i < 5; i++) {
//            tfa = buildNotification ("Teste de notifica????o", "NOTIF",  String.format("Testando numero : %d", i), null);
//            notifications.add(tfa);
//        }
//    }