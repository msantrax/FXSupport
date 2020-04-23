/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author opus
 */
public class SystemMenu extends AnchorPane{

    private static final Logger LOG = Logger.getLogger(SystemMenu.class.getName());

    
        private boolean keepvisual = false;

    public boolean isKeepvisual() {
        return keepvisual;
    }

    public void setKeepvisual(boolean keepvisual) {
        this.keepvisual = keepvisual;
    }

    
    public SystemMenu() {
        
        
        
    }
    
}
