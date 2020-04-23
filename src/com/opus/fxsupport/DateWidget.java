/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;


import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;



/**
 *
 * @author opus
 */
public class DateWidget extends AnchorPane implements FXFField{

    
    private Long value = 0l;
    
    
    private FXFControllerInterface controller;
    private FXFWidgetManager wdgtmanager;
    private WidgetContext wctx;
    
    private Integer focusPosition = 0;
    public final DateWidget instance;
   
    private String sid= "";

    @FXML
    private Label day;

    @FXML
    private Label weekday;

    @FXML
    private Label month;

    @FXML
    private Label year;

    @FXML
    private Label time;
    
    
    public DateWidget() {
        
        // Load the FXML
        URL fxmlUrl = getClass().getResource("Datewidget.fxml");
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
    
    
     
    @FXML
    private void initialize() {
        updateValue(String.valueOf(System.currentTimeMillis()), true);
    }
 
    
    @Override
    public void setManagement(FXFControllerInterface controller, Integer idx, WidgetContext wctx){
        this.controller = controller;
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Long atime = Long.parseLong(value);
                time.setText(String.format("%1$tH : %1$tM : %1$tS", atime));
                day.setText(String.format("%1$td", atime));
                weekday.setText(String.format("%1$tA", atime));
                month.setText(String.format("%1$tB", atime));
                year.setText(String.format("%1$tY", atime));
            }
        });
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
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
    public ContextMenu getConfigurationMenu(Control field, FXFFieldDescriptor fxfd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    
    
    
}


//private final String rootstyle = "-fx-border-color: black; -fx-border-radius: 20; -fx-effect: dropshadow(three-pass-box, black, 10.0, 0, 5.0, 5.0);};";


//private class DateLabel extends Label {
//        
//        private final String rstyle = "-fx-text-fill: dodgerblue; -fx-opacity: 0.75; ";
//        
//        
//        public DateLabel(String text, String lstyle, Font font) {
//            super(text);
//            this.setStyle(rstyle + lstyle == null ? "" : lstyle );
//            this.setPrefHeight(16.0);
//            this.setPrefWidth(150.0);
//            
//            if (font != null){
//                this.setFont((javafx.scene.text.Font)font);
//            }
//        }
//        
//        public DateLabel setWidth(Double width) { this.setPrefWidth(width); return this;}
//        
//        public DateLabel setHeight(Double height) { this.setPrefWidth(height); return this;}
//    
//        
//        
//        
//    }


//private DateLabel day;
//    private DateLabel weekday;
//    private DateLabel month;
//    private DateLabel year;
//    private DateLabel hour;
//    
//    
//    
//    public DateWidget() {
//        
//        super();
//        this.setStyle(rootstyle);
//        
//        this.setPrefHeight(16.0);
//        this.setPrefWidth(150.0);
//        
//        
//        day = new DateLabel("01", "", new Font("DejaVu Sans", , 72));
//        weekday = new DateLabel("Quinta-Feira", null, null);
//        month = new DateLabel("Quinta-Feira", null, null);
//        year = new DateLabel("Quinta-Feira", null, null);
//        hour = new DateLabel("Quinta-Feira", null, new Font("System", Font.BOLD, 32));
//        
//        
//        
//        
//        
//        instance = this;
//    }
//    
//
