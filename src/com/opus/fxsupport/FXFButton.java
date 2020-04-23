/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.validation.ValidationSupport;


public class FXFButton extends Button implements FXFField {

    private FXFControllerInterface controller;
    private FXFWidgetManager wdgtmanager;
    private WidgetContext wctx;
    private Integer focusPosition;

    private String sid= "";
    
    
    public FXFButton() {
        
        focusedProperty().addListener((obs, oldVal, newVal) -> {
           handleFocusEvent(newVal);
        });     
        
        // Add key pressed and released events to the TextField
        setOnKeyPressed((final KeyEvent keyEvent) -> {
            handleKeyEvent(keyEvent);
        });
      
    }
    
    
    @Override
    public void updateValue(String value, boolean required){    
        this.setText(value);
    }
    
    @Override
    public String getValue(){
        return getText();  
    } 
    
    
    private void handleFocusEvent(boolean focused){
        //wdgtmanager.updateFocus(wctx, focusPosition, focused);
        controller.updateFocus(focusPosition, focused);
    }
    
    // ===================================================================================================================
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
    public void setFocus(boolean set){
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
    
    // Helper Methods for Event Handling
    private void handleKeyEvent(KeyEvent e) {
        
//        String type = e.getEventType().getName();
//        KeyCode keyCode = e.getCode();
        //LOG.log(Level.INFO, ": Key Code={0}, Text={1}\n", new Object[]{keyCode.getName(), type});
        
//        ValidationSupport.setRequired(this, getText().isEmpty());
        
        if (e.getEventType() == KeyEvent.KEY_PRESSED && e.getCode() == KeyCode.ENTER) {
            //LOG.info("Enter pressed");
            //wdgtmanager.yieldFocus(wctx, this, true, false);
            controller.yieldFocus(this, true, false);
            e.consume();
        }
        else if (e.getEventType() == KeyEvent.KEY_PRESSED && e.getCode() == KeyCode.TAB){
            //wdgtmanager.yieldFocus(wctx, this, true, true);
            controller.yieldFocus(this, true, true);
            e.consume();
        }
    }
    
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public ContextMenu getConfigurationMenu(Control field, FXFFieldDescriptor fxfd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
