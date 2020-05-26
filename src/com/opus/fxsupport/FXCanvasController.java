/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.syssupport.ActivityDescriptor;
import com.opus.glyphs.FontAwesomeIcon;
import com.opus.glyphs.GlyphIcon;
import com.opus.glyphs.GlyphsBuilder;
import com.opus.syssupport.ActivitiesMap;
import com.opus.syssupport.ImageResources;
import com.opus.syssupport.PicnoUtils;
import static com.opus.syssupport.PicnoUtils.user_name;
import com.opus.syssupport.ProfileResource;
import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author opus
 */
public class FXCanvasController extends AnchorPane implements com.opus.fxsupport.FXFControllerInterface {

    private static final Logger LOG = Logger.getLogger(FXCanvasController.class.getName());
    
    private static final String UID = "CANVAS";

    private Scene scene;
    private FXMLLoader fxmlLoader;
    private FXFWindowManager wm;
    private LinkedHashMap<String,FXFWindowDescriptor> cvmap;
    
    
    private Double iconsizew = 120.0;
    private Double iconsizeh = 120.0;
    
    private Double prefheight;
    private Double prefwidth = 1366.0;
    
    
    public FXCanvasController() {
        wm = FXFWindowManager.getInstance();
        initDeskModes();
    }
    
    public FXCanvasController(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }
    
    
    public void activateModel(){
        //machine.activateModel(profile.getArgument());
    }
    
    
    @Override
    public String getUID() {
        return UID;
    }

    // Application controller link 
    private VirnaServiceProvider ctrl;
    public void setAppController (VirnaServiceProvider ctrl){
        this.ctrl = ctrl;
    }
    
    private ChoiceBox<String> desktoplist;
    private Label bt_logout;
    private SystemMenu menupane;
    
    
    
    // System menu Builder ===========================================================================================
    
    private void drawSystemMenuSeparator(Double ypos){
        Line sep = new Line();
        sep.setEndX(100.0);
        sep.setStartX(-100.0);
        menupane.setTopAnchor(sep, ypos);
        menupane.setLeftAnchor(sep, 0.0);
        menupane.getChildren().add(sep);
    }
    
    
    @Override
    public SystemMenu getMenu(boolean isadm){
         
        menupane = new SystemMenu();
        
        drawSystemMenuSeparator (0.0);
 
        Text t1 = new Text("Desktops :");
        menupane.setTopAnchor(t1, 10.0);
        menupane.setLeftAnchor(t1, 15.0);
        menupane.getChildren().add(t1);
         
        desktoplist = new ChoiceBox<>();
        ObservableList<String> desktopslist = FXCollections.<String>observableArrayList(PicnoUtils.launchers.keySet());
        desktoplist.getItems().addAll(desktopslist);
        desktoplist.setValue(PicnoUtils.launcherconfig.getArgument());
        
        desktoplist.setOnShowing(new EventHandler<Event>(){
            @Override 
            public void handle(Event event) {
                //LOG.info(String.format("Desklist showing"));
                menupane.setKeepvisual(true);
            } 
        });
        
        desktoplist.setOnHiding(new EventHandler<Event>(){
            @Override 
            public void handle(Event event) {
                //LOG.info(String.format("Desklist hiding"));
                menupane.setKeepvisual(false);
            } 
        });
        
        desktoplist.getSelectionModel()
        .selectedItemProperty()
        .addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s1, String  s2) {
                //LOG.info(String.format("Desklist changed from %s to %s", s1 , s2));
                FXFWindowManager wm = FXFWindowManager.getInstance();
                FXFHeaderband hb = wm.getHeaderBand();
                hb.hideMenubox();
                LauncherConfig lc1 = PicnoUtils.launcherconfig;
                PicnoUtils.loadLauncher(s2);
                wm.updateCanvasMap();
                update();   
            }
        });
        
        
        menupane.setTopAnchor(desktoplist, 40.0);
        menupane.setLeftAnchor(desktoplist, 15.0);
        menupane.getChildren().add(desktoplist);
 
        drawSystemMenuSeparator (80.0);
        
        Label bt_logout = new Label("Shutdown");
        bt_logout.setGraphicTextGap(15.0);
        bt_logout.setGraphic(GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.POWER_OFF, "black", 2));
        bt_logout.getStyleClass().add("fxf-shutdownbutton");
        
        bt_logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                System.exit(0);
            } 
        });
        
        menupane.setTopAnchor(bt_logout, 100.0);
        menupane.setLeftAnchor(bt_logout, 25.0);
        menupane.getChildren().add(bt_logout);
       
        return menupane;

    }
    
    
    
    
    private MenuItem getContextMenuLabel (String mes, boolean title){
        
        MenuItem labelitem = new MenuItem(mes);
        labelitem.setDisable(true);
        
        if (title){
            labelitem.getStyleClass().add("context-menu-title");
        }
        else{
            labelitem.getStyleClass().add("context-menu-label");
        }
        return labelitem;
    }
    
    private TextField addContextTextField (ArrayList<MenuItem> menuitems, String label, String value){
        
       menuitems.add(getContextMenuLabel (label, false)); 
       
       TextField tf = new TextField(value);
       tf.getStyleClass().add("fxf-text-field");
       CustomMenuItem cmi = new CustomMenuItem(tf, false);
       menuitems.add(cmi);
       
       return tf;
    }
    
    private ChoiceBox addContextChoiceBox (ArrayList<MenuItem> menuitems, String label, String setv,
            Set<String> values){
        
       menuitems.add(getContextMenuLabel (label, false)); 
       
       ChoiceBox chbx = new ChoiceBox<>();
       ObservableList<String> activitylist = FXCollections.<String>observableArrayList(values);
       chbx.getItems().addAll(activitylist);
       chbx.setValue(setv);
       
       CustomMenuItem cmi = new CustomMenuItem(chbx, false);
       menuitems.add(cmi);
       
       return chbx;
    }
    
    private CheckBox addContextCheckBox (ArrayList<MenuItem> menuitems, String label, boolean setv){
        
        CheckBox cbox = new CheckBox(label);
        cbox.setSelected(setv);
        CustomMenuItem cmi = new CustomMenuItem(cbox, false);
        menuitems.add(cmi);
        
        return cbox;
    }
    
    
    
    private LinkedHashMap<String,String> deskmodes;
    private String deskmode;
    
    private void initDeskModes(){
        
        deskmodes = new LinkedHashMap<>();
        
        deskmodes.put("CENTER", "Centralizado");
        deskmodes.put("RIGHT", "A Direita");
        deskmodes.put("LEFT", "A Esquerda");
        deskmodes.put("CLEAN", "Simples");
    }
    
    
    private String getDeskMode(String label){
        
        deskmode = "CENTER";
        final String out;
        deskmodes.forEach((key, value) -> {
            if (value.equals(label)){
                deskmode = key;
            }
        });
        return deskmode;
    }
    
    
    private ContextMenu getDesktopContextMenu(){
        
        ContextMenu dsk_ctxm = new ContextMenu();
        LauncherConfig lc = PicnoUtils.launcherconfig;
        
        //dsk_ctxm.getStyleClass().add("context-menu-dark");
        
        ArrayList<MenuItem> dsk_menuitems = new ArrayList<>();
        dsk_ctxm.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, e -> {
            e.consume();
        });
        
        dsk_menuitems.add(getContextMenuLabel ("Config do Desktop", true));
        dsk_menuitems.add(new SeparatorMenuItem());
        
        
        if (!PicnoUtils.sudo_enabled){
            MenuItem witem1 = new MenuItem("Não há permissão para a alteração de dados desse Desktop");
            witem1.setDisable(true);
            dsk_menuitems.add(witem1);
            MenuItem witem2 = new MenuItem("Solicite autenticação como administrador");
            witem2.setDisable(true);
            dsk_menuitems.add(witem2);
            
            dsk_ctxm.getItems().addAll(dsk_menuitems);
            return dsk_ctxm;
        }
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "FLAGACTIVITY", this.getClass(),
                                new VirnaPayload().setString("Contextmenu:"+lc.getLabel() + ":" + user_name)
        )); 
        
        
        TextField tf_label = addContextTextField (dsk_menuitems, "Identificação do Desktop", lc.getArgument());
        tf_label.setOnAction((event) -> {
            //LOG.info(String.format("ti_name received event : %s", event.toString()));
            dsk_ctxm.hide();
            PicnoUtils.launchers.put(tf_label.getText(), PicnoUtils.launchers.get(lc.getArgument()));
            PicnoUtils.launchers.remove(lc.getArgument());
            lc.setArgument(tf_label.getText());
            PicnoUtils.saveLauncher(null);
            LinkedHashMap<String,String> launchers= PicnoUtils.launchers;
            this.update();
        });
        
        
        ChoiceBox dsk_type = addContextChoiceBox(dsk_menuitems, "Tipo de Desktop :", 
                deskmodes.get(lc.getGridposition()), new HashSet<String>(deskmodes.values()));        
        dsk_type.setOnAction((event) -> {
            lc.setGridposition(getDeskMode((String)dsk_type.getValue()));
            PicnoUtils.saveLauncher(null);
            dsk_ctxm.hide();
            update();
        });
        
        dsk_menuitems.add(new SeparatorMenuItem());
        MenuItem mn_wall = new MenuItem("Papel de Parede ...");
        mn_wall.setOnAction((event) -> {
            FXFWindowManager wm = FXFWindowManager.getInstance();
            String result = wm.showImageChooserDialog();
            if(result != null && !result.equals("cancel")){
                Image img;
                //LOG.info(String.format("Dialog returned : %s", result));
                lc.setWallpaper(result);
                PicnoUtils.saveLauncher(null);
            }
            update();
        });
        dsk_menuitems.add(mn_wall);
        
        
        CheckBox cbox = addContextCheckBox(dsk_menuitems, "Fundo escuro", lc.isDarkwallpaper());
        cbox.setOnAction((event) -> {
            lc.setDarkwallpaper(cbox.isSelected());
            PicnoUtils.saveLauncher(null);
            dsk_ctxm.hide();
            
            ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATESTATUS", this.getClass(),
                                new VirnaPayload().setObject(
                                new StatusMessage("Background type changed", 8000))
            ));
            
            update();
        });
        
        
        MenuItem mn_lchclone = new MenuItem("Clonar Desktop");
        mn_lchclone.setOnAction((event) -> {
            String newname = "(Copia) "+ lc.getArgument();
            lc.setArgument(newname);
            String outpath = PicnoUtils.saveNewLauncher(lc);
            if (!outpath.isEmpty()){
                PicnoUtils.launchers.put(newname, outpath);
            }
            dsk_ctxm.hide();
        });
        dsk_menuitems.add(mn_lchclone);
        
                
        MenuItem mn_lchremove = new MenuItem("Remover Desktop");
        mn_lchremove.setOnAction((event) -> {
            //FXFWindowManager wm = FXFWindowManager.getInstance();
            dsk_ctxm.hide();
            
            String [] lk = (String[])PicnoUtils.launchers.keySet().toArray();
            String lc_path = PicnoUtils.launchers.get(lk[0]);
            
            
            PicnoUtils.launchers.remove(lc.getArgument());
            
//            PicnoUtils.launcherconfig.getItems().remove(li);
//            PicnoUtils.saveLauncher(li);
//            wm.updateCanvasMap();
            
            //update(); 
        });
        dsk_menuitems.add(mn_lchremove);
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "UPDATESTATUS", this.getClass(),
                                new VirnaPayload().setObject(
                                new StatusMessage("Desktop context was called", 0))
        ));
        
        dsk_ctxm.getItems().addAll(dsk_menuitems);
        return dsk_ctxm;
        
    }
    
    
    
    private ContextMenu getActivityContextMenu(Rectangle rec){
        
        LauncherConfig lc = PicnoUtils.launcherconfig;
        LauncherItem li = (LauncherItem)rec.getUserData();
        ActivityDescriptor adold = wm.locateActivity (li.getAppclass(), li.getArgument()); 
        
        ContextMenu ctxm = new ContextMenu();
        SeparatorMenuItem smi = new SeparatorMenuItem();
        
        ArrayList<MenuItem> menuitems = new ArrayList<>();
        ctxm.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, e -> {
            e.consume();
        });
        
        menuitems.add(getContextMenuLabel ("Config da Atividade", true));
        menuitems.add(smi);
        
        if (!PicnoUtils.sudo_enabled){
            MenuItem witem1 = new MenuItem("Não há permissão para a alteração de dados desse lançador");
            witem1.setDisable(true);
            menuitems.add(witem1);
            MenuItem witem2 = new MenuItem("Solicite autenticação como administrador");
            witem2.setDisable(true);
            menuitems.add(witem2);
            
            ctxm.getItems().addAll(menuitems);
            return ctxm;
        }
        
        ctrl.processSignal(new SMTraffic(0l, 0l, 0, "FLAGACTIVITY", this.getClass(),
                                new VirnaPayload().setString("Contextmenu:"+li.getIconlabel() + ":" + user_name)
        )); 
        
        
        
        TextField tf_label = addContextTextField (menuitems, "Etiqueta de Descrição :", li.getIconlabel());
        tf_label.setOnAction((event) -> {
            LOG.info(String.format("ti_name received event : %s", event.toString()));
            li.setIconlabel(tf_label.getText());
            PicnoUtils.saveLauncher(li);
            this.update();
            
        });
        
        
        ChoiceBox acvt_type = addContextChoiceBox(menuitems, "Tipo da Atividade :", adold.getName(), ActivitiesMap.getNames());        
        acvt_type.setOnAction((event) -> {
            ctxm.hide();
            ActivityDescriptor ad = ActivitiesMap.getByName((String)acvt_type.getValue());
            if (adold != ad){    
                li.setAppclass(ad.getFxcontrollerName());
                li.setArgument(ad.getArgument_prefix()+":default");
                li.setIconlabel(ad.getLabel());
                li.setIconlabelcolor(null);
                li.setConfigid(PicnoUtils.getSUID());
                PicnoUtils.saveLauncher(li);
                this.update();
            }
        });
        
 
        if (li.getArgument() != null){    
            
            ProfileResource oldprof = PicnoUtils.profile_resources.getResource(li.getArgument());
            String label = oldprof.getLabel();
            
            ChoiceBox cb_profiles = addContextChoiceBox(menuitems, "Perfil de Análise :", 
                        label, PicnoUtils.profile_resources.getResourcesList(li.getArgument()));
            
            cb_profiles.setOnAction((event) -> {
                ctxm.hide();
                if (label != (String)cb_profiles.getValue()){
                    ProfileResource pd = PicnoUtils.profile_resources.getByLabel((String)cb_profiles.getValue());
                    if (pd != null){
                        li.setArgument(pd.getName());
                        PicnoUtils.saveLauncher(li);
                        this.update();
                    }     
                }
            });
        }
        
        menuitems.add(new SeparatorMenuItem());
        
        MenuItem mn_changeicon = new MenuItem("Mudar Icon ...");
        mn_changeicon.setOnAction((event) -> {
            FXFWindowManager wm = FXFWindowManager.getInstance();
            String result = wm.showIconChooserDialog();
            if(result != null && !result.equals("cancel")){
                Image img;
                LOG.info(String.format("Dialog returned : %s", result));
                if (result.contains("glyph:")){

                }
                else {
                    img = PicnoUtils.image_resources.getImage(result, ImageResources.IMAGECLASS.FULLPATH);
                    ImagePattern pattern = new ImagePattern(img);
                    rec.setFill(pattern);
                }
                //LauncherItem li = (LauncherItem)rec.getUserData();
                li.setIconpath(result);
                PicnoUtils.saveLauncher(li);
            }
        });
        menuitems.add(mn_changeicon);
        
        MenuItem mn_lchclone = new MenuItem("Clonar Lançador");
        mn_lchclone.setOnAction((event) -> {
            FXFWindowManager wm = FXFWindowManager.getInstance();
            LauncherItem newli = li.clone();
            PicnoUtils.launcherconfig.getItems().add(newli);
            PicnoUtils.saveLauncher(li);
            wm.updateCanvasMap();
            ctxm.hide();
            update(); 
        });
        menuitems.add(mn_lchclone);
        
        MenuItem mn_lchremove = new MenuItem("Remover Lançador");
        mn_lchremove.setOnAction((event) -> {
            FXFWindowManager wm = FXFWindowManager.getInstance();
            PicnoUtils.launcherconfig.getItems().remove(li);
            PicnoUtils.saveLauncher(li);
            wm.updateCanvasMap();
            ctxm.hide();
            update(); 
        });
        menuitems.add(mn_lchremove);
        
        MenuItem mn_lchdefaults = new MenuItem("Adicionar Faltantes");
        mn_lchdefaults.setOnAction((event) -> {
            FXFWindowManager wm = FXFWindowManager.getInstance();
            
            PicnoUtils.saveLauncher(li);
            wm.updateCanvasMap();
            ctxm.hide();
            update(); 
        });
        menuitems.add(mn_lchdefaults);
        
        ctxm.getItems().addAll(menuitems);
        return ctxm;
    }
    
    
    private ContextMenu desktop_context;
    
    @Override
    public void update(Scene scene){

        
        this.scene = scene;
        
        prefheight = getPrefHeight();
        cvmap = wm.getCanvasesmap();
       
        iconsizew = 100.0;
        iconsizeh = 80.0;

        int colnum = 3;
        int rownum = 4;
        
        LauncherConfig lc = PicnoUtils.launcherconfig;
        
        getChildren().clear();
        
        // Set wallpaper
        String wallpaper = lc.getWallpaper();
        if (wallpaper != null){
            try{
            //Image img = new Image(wallpaper,prefwidth,prefheight,false,true);
            
            Image img = PicnoUtils.image_resources.getImage(wallpaper, prefwidth, prefheight);
           
            BackgroundImage bi= new BackgroundImage(img,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                                                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            setBackground(new Background(bi));
            }
            catch (IllegalArgumentException ex){
                LOG.severe(String.format("Failed to load wallpaper @ %s", wallpaper));
                ctrl.processSignal(new SMTraffic(0l, 0l, 0, "ADD_NOTIFICATION", this.getClass(),
                           new VirnaPayload().setString(
                                   "Canvas Controller&" + "SEVERE&" +
                                   String.format("Arquivo de pano de fundo %s não foi encontrado...&", wallpaper) +
                                   "Estou utilizando uma cor sólida então, corrija a entrada no config"        
                           )));
                           
                setStyle("-fx-background-color: GHOSTWHITE");
                lc.setDarkwallpaper(false);
            }
        }
        else if (wallpaper.contains("fill")){
            setStyle(wallpaper);
        }
        else{
            setStyle("-fx-fill: GHOSTWHITE");
        }
        
        
        
        
        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    //LOG.info(String.format("Desktop Context requested @ %f/%f",  e.getScreenX(), e.getScreenY()));                    
                    if ((desktop_context == null) || !desktop_context.isShowing()){
                        desktop_context = getDesktopContextMenu();
                        AnchorPane ap = (AnchorPane)e.getSource();
                        desktop_context.show( ap , e.getScreenX(), e.getScreenY());
                        //LOG.info(String.format("Desktop menu activated"));
                    }
                }
                else{
                    if (desktop_context != null) desktop_context.hide();
                }
                e.consume();
            }
        });
        
        
        // Gridcells config
        ColumnConstraints cclarge = new ColumnConstraints(200);
        cclarge.setHalignment(HPos.CENTER);
        
        ColumnConstraints ccmedium = new ColumnConstraints(160);
        ccmedium.setHalignment(HPos.CENTER);
        
        ColumnConstraints ccsmall = new ColumnConstraints(100);
        ccsmall.setHalignment(HPos.CENTER);
        
        ColumnConstraints ccbutton = new ColumnConstraints(300);
        ccbutton.setHalignment(HPos.CENTER);
        
        ColumnConstraints ccgridcol;
        
        
        RowConstraints rclarge = new RowConstraints(160);
        rclarge.setValignment(VPos.CENTER);
        
        RowConstraints rcmedium = new RowConstraints(100);
        rcmedium.setValignment(VPos.CENTER);
        
        RowConstraints rcsmall = new RowConstraints(36);
        rcsmall.setValignment(VPos.CENTER);
        
        RowConstraints rclabellarge = new RowConstraints(25);
        rclabellarge.setValignment(VPos.CENTER);       
        
        RowConstraints rclabelsmall = new RowConstraints(18);
        rclabelsmall.setValignment(VPos.CENTER); 
        
        RowConstraints rcbuttonsmall = new RowConstraints(42);
        rcbuttonsmall.setValignment(VPos.CENTER);
        
        RowConstraints rcbuttonlarge = new RowConstraints(80);
        rcbuttonlarge.setValignment(VPos.CENTER);
        
        RowConstraints rcgridrow;
        RowConstraints rclabel = rclabellarge;
        
     
        GridPane gp = new GridPane(); 
        //gp.setGridLinesVisible(true);
        
        
        int iconum = cvmap.size() -1;
        
        
        if (lc.getGridposition().equals("RIGHT")){
            if (iconum <=6){
                colnum = 3;
                rownum = 2;
                
                ccgridcol = cclarge;
                rcgridrow = rclarge;

                iconsizew = 150.0;
                iconsizeh = 100.0;

                double topa = ((prefheight /2) - 185) - 50;
                setTopAnchor(gp, topa);
                setRightAnchor(gp, 50.0);
            }
            else{
                
                colnum = 3;
                rownum = 4;
                
                ccgridcol = ccmedium;
                rcgridrow = rcmedium;

                iconsizew = 100.0;
                iconsizeh = 80.0;
                
                double topa = ((prefheight /2) - 250) - 50;
                setTopAnchor(gp, topa);
                setRightAnchor(gp, 50.0);
            }
        }
        
        else if (lc.getGridposition().equals("LEFT")){
            
            colnum = 1;
            rownum = 12;

            ccgridcol = ccsmall;
            rcgridrow = rcsmall;
            rclabel = rclabelsmall;

            iconsizew = 60.0;
            iconsizeh = 28.0;

            //double topa = ((prefheight /2) - 250) - 50;
            setTopAnchor(gp, 20.0);
            setLeftAnchor(gp, 50.0);
            
        }
        
        else if (lc.getGridposition().equals("CLEAN")){
            
            if (iconum <=6){
                colnum = 1;
                rownum = 6;
                
                ccgridcol = ccbutton;
                rcgridrow = rcbuttonlarge;

                iconsizew = 280.0;
                iconsizeh = 54.0;

                double topa = ((prefheight /2) - 240) - 50;
                setTopAnchor(gp, topa);
                setRightAnchor(gp, 120.0);
            }
            else{
                colnum = 1;
                rownum = 12;
                
                ccgridcol = ccbutton;
                rcgridrow = rcbuttonsmall;

                iconsizew = 280.0;
                iconsizeh = 32.0;
                
                //double topa = ((prefheight /2) - 187);
                setTopAnchor(gp, 50.0);
                //double ra = ((prefwidth /2) - 320);
                setRightAnchor(gp, 120.0);
            }
        }
        else{
            if (iconum <=6){
                colnum = 3;
                rownum = 2;
                
                ccgridcol = cclarge;
                rcgridrow = rclarge;

                iconsizew = 150.0;
                iconsizeh = 100.0;

                double topa = ((prefheight /2) - 185);
                setTopAnchor(gp, topa);
                double ra = ((prefwidth /2) - 300);
                setRightAnchor(gp, ra);
            }
            else{
                colnum = 4;
                rownum = 3;
                
                ccgridcol = ccmedium;
                rcgridrow = rcmedium;

                iconsizew = 100.0;
                iconsizeh = 80.0;
                
                double topa = ((prefheight /2) - 187);
                setTopAnchor(gp, topa);
                double ra = ((prefwidth /2) - 320);
                setRightAnchor(gp, ra);
            }
        }
        
 
        for (int i = 0; i < colnum; i++) {
            gp.getColumnConstraints().add(ccgridcol);
        }
 
        for (int i = 0; i < rownum; i++) {
            gp.getRowConstraints().add(rcgridrow);
            if (!lc.getGridposition().equals("CLEAN")){
                gp.getRowConstraints().add(rclabel);
            }
        }
       
        getChildren().add(gp);
        
        
        int lrow = 0;
        int lcol = 0;
        
        
        if (lc.getGridposition().equals("CLEAN")){
            for (String sli : cvmap.keySet()){
                if (!sli.equals("CANVAS")){
                    LauncherItem li = cvmap.get(sli).getLauncheritem();
                    gp.add(buildCleanButton(li), lcol, lrow);
                    lrow++;
                }
            }    
        }
        else{
            for (String sli : cvmap.keySet()){
                if (!sli.equals("CANVAS")){
                    LauncherItem li = cvmap.get(sli).getLauncheritem();

                    gp.add(buildLaunchIcon2(li), lcol, lrow);
                    //gp.add(buildGlyph(li.getConfigid()), lcol, lrow);

                    Text txt = new Text(li.getIconlabel());
                    txt.wrappingWidthProperty().set(150);
                    txt.setTextAlignment(TextAlignment.CENTER);
                    txt.getStyleClass().add("fxf-launchicon-text");

                    String lic = li.getIconlabelcolor();
                    
                    if (lic == null){
                        if(lc.isDarkwallpaper()){
                            lic = "white";
                        }
                        else{
                            lic = "black";
                        }
                    }
                    txt.setStyle("-fx-fill: " + lic);

                    gp.add(txt, lcol, lrow+1);
                    lcol++;
                    if (lcol >= colnum){
                        lrow = lrow+2;
                        lcol = 0;
                    }
                }
            }
        }
       
        
        Text acplogo = new Text("acpinstruments.com.br");
        if (lc.isDarkwallpaper()){
            acplogo.setStyle("-launchicon-color : skyblue");
        }
        else{
            acplogo.setStyle("-launchicon-color : dodgerblue");
        }
        acplogo.getStyleClass().add("fxf-acplogo");
        setBottomAnchor(acplogo,50.0);
        setRightAnchor(acplogo, 60.0);
        getChildren().add(acplogo);
        
    }
    
    
    
//    private GlyphIcon buildGlyph(String configid){
//        
//        GlyphIcon gi = GlyphsBuilder.getAwesomeGlyph(FontAwesomeIcon.SEARCH, "black", 6);
//        //gi.getStyleClass().add("fxf-lauchicon");
//        gi.setOnMouseClicked(new EventHandler<MouseEvent>(){
//            @Override 
//            public void handle(MouseEvent event) {
//                LOG.info(String.format("Launch Icon %s called ...", configid));
//                try {
//                    wm.activateWindow(configid, li);
//                } catch (IOException ex) {
//                    Logger.getLogger(FXCanvasController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }  
//        });
//        return gi; 
//    }
    
    private Label buildCleanButton (LauncherItem li){
        
        Label txt = new Label(li.getIconlabel());
        txt.setPrefSize(iconsizew, iconsizeh);
        txt.setAlignment(Pos.CENTER);
        txt.getStyleClass().add("fxf-lauchbutton");
        txt.setStyle("-fx-background-color : "+ li.getButtonbkgcolor() + ";" +
                     "-fx-text-fill: " + li.getButtonlabelcolor() + ";");
      
        txt.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent event) {
                LOG.info(String.format("Launch Icon %s called ...", li.getConfigid()));
                try {
                    wm.activateWindow(li.getConfigid(), li, true);
                } catch (IOException ex) {
                    Logger.getLogger(FXCanvasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        });
        
        return txt;
    }
    
    
    private Rectangle buildLaunchIcon2 (LauncherItem li){
        
        Image img;
        
        LauncherConfig lc = PicnoUtils.launcherconfig;
        
        Rectangle rectangle = new Rectangle(0, 0, iconsizew, iconsizeh);
        rectangle.setArcWidth(20.0);   // Corner radius
        rectangle.setArcHeight(20.0);
      
        //LOG.info(String.format("Building launch Icon @ %s", li.getIconpath()));
        
        if (li.getIconpath().startsWith("resources")){
            img = new Image(li.getIconpath(), iconsizew, iconsizeh, false, false);
        }
        else{
            img = PicnoUtils.image_resources.getImage(li.getIconpath(), iconsizew, iconsizew);
        }
       
        if (img==null){
            LOG.severe(String.format("Launcher failed to load image @ ", li.getIconpath()));
        }
        else{
            ImagePattern pattern = new ImagePattern(img);
            rectangle.setFill(pattern);
        }
        
        
        if (lc.isDarkwallpaper()){
            rectangle.setStyle("-launchicon-color : white");
        }
        rectangle.getStyleClass().add("fxf-lauchicon");
        rectangle.setUserData(li);
        
        rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    //LOG.info(String.format("Launcher Context requested @ %f/%f",  e.getScreenX(), e.getScreenY()));
                    ContextMenu cm = getActivityContextMenu(rectangle);
                    cm.show(rectangle, e.getScreenX(), e.getScreenY());
                    e.consume();
                }
                else{
                    try {
                        wm.activateWindow(li.getConfigid(), li, true);
                    } catch (IOException ex) {
                        LOG.severe(String.format("Canvas Controller failed to launch due :  %s", ex.getMessage()));
                        //Logger.getLogger(FXCanvasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    e.consume();
                }
            }
        });        
        return rectangle;
    }
    
    
    
    void initialize() {
        LOG.info("Canvas Window initializing ...");
        
    }
    
    
    @Override
    public void sendSignal(PropertyLinkDescriptor pld, String sigtype) {
        
    }

    @Override
    public void clearCanvas() {
        
    }

    @Override
    public void update() {
        update(scene);
    }

    @Override
    public void updateField(String fieldname, String value, boolean required) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void yieldFocus(FXFField field, boolean fwd, boolean tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateFocus(Integer pos, boolean focused) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initValidators(FXFField field, FXFFieldDescriptor fxfd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getProfileID() {
        return "";
    }

    @Override
    public FXFCheckListViewNumber<String> getRunControl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FXFCountdownTimer getCDT() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetDevices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FXFBlaineDeviceController getBlaineDevice() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLauncher(LauncherItem li) {
        
    }

    @Override
    public LauncherItem getLauncher() {
        return null;
    }
    
    
}







    
//    private ImageView buildLaunchIcon1 (String uid, String lbl){
//        
//        
//        ImageView lb1 = new ImageView();
//        lb1.setFitHeight(iconsizew);
//        lb1.setFitWidth(iconsizeh);
//        lb1.setPickOnBounds(true);
//        lb1.setPreserveRatio(true);
//        
//        
//        lb1.setImage(new Image("resources/service_canvas1.png",150.0,150.0,false,true));
//        Rectangle clip = new Rectangle(
//                lb1.getFitWidth(), lb1.getFitHeight()
//        );
//        clip.setArcWidth(20);
//        clip.setArcHeight(20);
//        lb1.setClip(clip);
//        
//        // snapshot the rounded image.
//        SnapshotParameters parameters = new SnapshotParameters();
//        parameters.setFill(Color.TRANSPARENT);
//        WritableImage image = lb1.snapshot(parameters, null);
//
//        // remove the rounding clip so that our effect can show through.
//        lb1.setClip(null);
//        
//        lb1.getStyleClass().add("fxf-lauchicon");
//        
//        lb1.setImage(image);
//        
//        lb1.setOnMouseClicked(new EventHandler<MouseEvent>(){
//            
//            @Override 
//            public void handle(MouseEvent event) {
//                LOG.info(String.format("Launch Icon %s called ...", uid));
//            }
//            
//        });
//   
//        return lb1;
//    }
//    
//    
//    
//    private Label buildLaunchIcon (String uid, String lbl){
//        
//        Label lb1 = new Label();
//        
//        lb1.setPrefSize(150,150);
//        lb1.setAlignment(Pos.BOTTOM_CENTER);
//        
//        lb1.setTooltip(new Tooltip(lbl));
//        
//        
//        BackgroundImage myBI= new BackgroundImage(new Image("resources/service_canvas.png",150,150,false,true),
//                                                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
//                                                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//        lb1.setBackground(new Background(myBI));
//
//
//        lb1.getStyleClass().add("fxf-lauchicon");
//        
//        
//        
//        lb1.setOnMouseClicked(new EventHandler<MouseEvent>(){
//            
//            @Override 
//            public void handle(MouseEvent event) {
//                LOG.info(String.format("Launch Icon %s called ...", uid));
//            }
//            
//        });
//        
//        return lb1;
//    } 
    
    
//            Image img = new Image("resources/andromeda.png",prefwidth,prefheight,false,true);
// 
//        ImageView imgv = new ImageView();
//        imgv.setPickOnBounds(true);
//        imgv.setPreserveRatio(true);
//        imgv.setImage(img);
//        
//        ColorAdjust ca = new ColorAdjust();
//        ca.setBrightness(0.5);
//        
//        imgv.setEffect(ca);
//        
////        BackgroundImage bi= new BackgroundImage(imgv.getImage(),
////                                                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
////                                                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
////        
////        setBackground(new Background(bi));



       
        
        
        
//        Slider strokeWidthSlider = new Slider(1, 10, 1);
//        strokeWidthSlider.setShowTickLabels(true);
//        strokeWidthSlider.setShowTickMarks(true);
//        strokeWidthSlider.setMajorTickUnit(2);
//        CustomMenuItem strokeWidthItem = new CustomMenuItem(strokeWidthSlider, false);
//        dsk_menuitems.add(strokeWidthItem);
        