/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author opus
 */
public class FXFFormulaEditorController extends AnchorPane{

    private static final Logger LOG = Logger.getLogger(FXFFormulaEditorController.class.getName());

    private FXFFieldDescriptor fxfd;
    
    @FXML
    private ScrollPane scrollpane;
    
    @FXML
    private Button cancel_button;

    @FXML
    private TextArea text_area;

    @FXML
    private Button ok_button;
    
    
    private boolean touched = false;
    
    @FXML
    void cancel_action(ActionEvent event) {
        result="cancel";
        closeStage(event);
    }

    @FXML
    void ok_action(ActionEvent event) {
        result=text_area.getText();
        
        ObservableList<CharSequence> list = text_area.getParagraphs();
        if (!list.isEmpty()){
            ArrayList<String>faclist = fxfd.getFormulalist();
            faclist.clear();
            list.forEach(it -> {
                faclist.add(it.toString());
            });
        }
        closeStage(event);
    }
    
    
    private String result = "empty";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
    
    
    public FXFFormulaEditorController() {
    
    }
    
    @FXML
    void initialize() {  
//        LOG.info(String.format("Loading..."));    
    }
    
    public void setPayload(FXFFieldDescriptor fxfd){
        
        this.fxfd = fxfd;
        
        ArrayList<String> aclist = fxfd.getFormulalist();
        
        
        if (aclist == null){
            text_area.appendText("#Insira suas formulas de calculo aqui :\r\n");
        }
        else{
            aclist.forEach(s -> {
                text_area.appendText(s + "\r\n");
            });
        }
        String tarea = text_area.getText();
        text_area.setText(tarea.substring(0, tarea.length()-1));
        text_area.textProperty().addListener(this::aclistChanged);
        
        ok_button.setDisable(true);
//        BooleanBinding btouched = Bindings.createBooleanBinding(() -> !touched);
//        ok_button.disableProperty().bind(btouched);
        
    }


    public void aclistChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        touched = true;
        ok_button.setDisable(false);
        //LOG.info(String.format("aclist changed - touched = %s", touched));
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
