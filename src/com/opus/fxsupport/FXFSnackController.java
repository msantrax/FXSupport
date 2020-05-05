/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author opus
 */
public class FXFSnackController extends AnchorPane {

    private static final Logger LOG = Logger.getLogger(FXFSnackController.class.getName());
    
    @FXML
    private Text message;

    
    public FXFSnackController() {
    
    }
     
    @FXML
    void initialize() {
        
        
    }
    
    
    public void setmessage(String mes) {
        message.setText(mes);
        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(2500),
            ae -> closeStage()
        ));
        timeline.play();
   
    }
    
    
    private Stage stage;
    public void setStage (Stage stage) { 
        this.stage = stage;

    }
    
    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage lstage  = (Stage) source.getScene().getWindow();
        lstage.close();
    }
    
    private void closeStage() {
        stage.close();
    }
    
}
