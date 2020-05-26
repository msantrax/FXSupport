/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author opus
 */
public class FXFInputDialogController extends AnchorPane {
    
    
    @FXML
    private Text header;

    @FXML
    private Button ok_button;

    @FXML
    private Button cancel_button;

    @FXML
    private TextField input_field;

    
    
    @FXML
    void cancel_action(ActionEvent event) {
        result.set("cancel");
    }

    @FXML
    void ok_action(ActionEvent event) {
        result.set(input_field.getText());
    }
    
    public SimpleStringProperty result = new SimpleStringProperty("");
    
    
    
    public FXFInputDialogController() {
         
    }
    
    
    @FXML
    void initialize() {
        ok_button.setDisable(true);
    }
    
    
    public void setHeader (String defheader){
        header.setText(defheader);
    }
    
    public void setDefvalue (String defvalue){
        input_field.setText(defvalue);
        input_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ok_button.setDisable(false);
            }
        });
    }
    
    

}


//
//    
//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }
//
//    
    



//
//    private Stage stage;
//    public void setStage (Stage stage) { this.stage = stage;}
//    
//    private void closeStage(ActionEvent event) {
//        Node  source = (Node)  event.getSource(); 
//        Stage lstage  = (Stage) source.getScene().getWindow();
//        lstage.close();
//    }
//    
//    private void closeStage() {
//        stage.close();
//    }
//    
