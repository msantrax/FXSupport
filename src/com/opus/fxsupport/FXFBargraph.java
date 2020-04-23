/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author opus
 */
public class FXFBargraph extends AnchorPane implements Initializable {

    private static final Logger LOG = Logger.getLogger(FXFBargraph.class.getName());

    @FXML private Line bar_back;

    @FXML private Text low_range;

    @FXML private Line bar_fore;

    @FXML private Line underflow;

    @FXML private Line overflow;

    @FXML private Text hight_range;

    @FXML private Text title;

    @FXML private Text value;
    
    
    
    public FXFBargraph() {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXFBargraph.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    

    public void setValue(Double value){
        
        
        
    }

    public Text getTitle() {
        return title;
    }

    
    
    
    
    
}
