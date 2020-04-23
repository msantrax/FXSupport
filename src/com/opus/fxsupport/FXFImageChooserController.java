/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.syssupport.ImageResource;
import com.opus.syssupport.ImageResources;
import com.opus.syssupport.PicnoUtils;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author opus
 */
public class FXFImageChooserController extends AnchorPane {

    private static final Logger LOG = Logger.getLogger(FXFImageChooserController.class.getName());
   
    
    @FXML
    private RadioButton tg_image;

    @FXML
    private RadioButton tg_iconcolor;
    
    @FXML
    private RadioButton tg_gradientes;
    
    @FXML
    private ToggleGroup source_group;

    @FXML
    private AnchorPane solidcolor;

    
    @FXML
    private Rectangle screen_thumb;
    @FXML
    private ColorPicker color_picker;
    @FXML
    void solidcolor_action(ActionEvent event) {
        
        Color c = color_picker.getValue();
        LOG.info(String.format("Color selected : %s", c.toString()));
        screen_thumb.setFill(c);
        
    }
    
    
    
    @FXML
    private Button cancel_button;    
    @FXML
    void cancel_action(ActionEvent event) {
        result="cancel";
        closeStage(event);
    }

    @FXML
    private ScrollPane scrollpane;
    @FXML
    private HBox icon_box;

    
    private String result = "empty";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
    public FXFImageChooserController() {
    
    }
    
    
    @FXML
    void initialize() {
   
        tg_image.setUserData("image");
        tg_iconcolor.setUserData("iconcolor");
        tg_gradientes.setUserData("gradientes");
        
        icon_box.getChildren().clear();
        addImages();
        icon_box.getChildren().addAll(list);
        tg_image.setSelected(true);
        
        
        source_group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (source_group.getSelectedToggle() != null) {
                    String udata = new_toggle.getUserData().toString();
                    LOG.info(udata);

                    if (udata.equals("gradientes")){
                       scrollpane.setVisible(false);
                       solidcolor.setVisible(false); 
                        
                    }
                    else if (udata.equals("iconcolor")){
                        scrollpane.setVisible(false);
                        solidcolor.setVisible(true);
                        
                    }
                    else{
                        solidcolor.setVisible(false);
                        scrollpane.setVisible(true);
                        
                        icon_box.getChildren().clear();
                        addImages();
                        icon_box.getChildren().addAll(list);
                    }
                }
             } 
        });
    
        icon_box.setOnScroll(event -> {
            if(event.getDeltaX() == 0 && event.getDeltaY() != 0) {
                scrollpane.setHvalue(scrollpane.getHvalue() - event.getDeltaY() / this.icon_box.getWidth());
            }
        });
    }
    
    private ObservableList<Rectangle> list = FXCollections.<Rectangle>observableArrayList();
    
    private void addImages(){
           
        ImageResources irc = PicnoUtils.image_resources;
        list = irc.getDisplayNodes(ImageResources.IMAGECLASS.WALL_THUMB);
        
        for (Rectangle rec : list){
            rec.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
                public void handle(MouseEvent e) {
                    ImageResource tirc = (ImageResource)rec.getUserData();
                    LOG.info(String.format("Selected : %s", tirc.getFpath()));
                    //result = "typeimage:"+tirc.getName();
                    result = tirc.getFpath().replaceAll("Wallthumbs", "Wallpapers");
                    closeStage();
                }
            });
        }
    }
    
    
    
    private Stage stage;
    public void setStage (Stage stage) { this.stage = stage;}
    
    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage lstage  = (Stage) source.getScene().getWindow();
        lstage.close();
    }
    
    private void closeStage() {
        stage.close();
    }
    
    
    
    
}
