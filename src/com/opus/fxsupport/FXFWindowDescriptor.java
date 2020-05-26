/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import java.net.URL;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author opus
 */
public class FXFWindowDescriptor {

    private static final Logger LOG = Logger.getLogger(FXFWindowDescriptor.class.getName());

    
    // Properties
    
        private URL url;

    public URL getUrl() {
        return url;
    }

    public FXFWindowDescriptor setUrl(URL url) {
        this.url = url;
        return this;
    }

        private FXMLLoader loader;

    public FXMLLoader getLoader() {
        return loader;
    }

    public FXFWindowDescriptor setLoader(FXMLLoader loader) {
        this.loader = loader;
        return this;
    }

        private LauncherItem launcheritem;

    public LauncherItem getLauncheritem() {
        return launcheritem;
    }

    public void setLauncheritem(LauncherItem launcheritem) {
        this.launcheritem = launcheritem;
    }

    
        private FXFControllerInterface fxcontroller;

    public FXFControllerInterface getFxcontroller() {
        return fxcontroller;
    }

    public FXFWindowDescriptor setFxcontroller(FXFControllerInterface fxcontroller) {
        this.fxcontroller = fxcontroller;
        return this;
    }

        private Class controllerclass;

    public Class getControllerclass() {
        return controllerclass;
    }

    public FXFWindowDescriptor setControllerclass(Class controllerclass) {
        this.controllerclass = controllerclass;
        return this;
    }

        private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public FXFWindowDescriptor setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

        private Pane rootpane;

    public Pane getRootpane() {
        return rootpane;
    }

    public FXFWindowDescriptor setRootpane(Pane rootpane) {
        this.rootpane = rootpane;
        return this;
    }

        private FXFHeaderband headerband;

    public FXFHeaderband getHeaderband() {
        return headerband;
    }

    public void setHeaderband(FXFHeaderband headerband) {
        this.headerband = headerband;
    }

    
        private Rectangle snapshot;

    public Rectangle getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Rectangle snapshot) {
        this.snapshot = snapshot;
    }

    
    
        private Boolean loaded = false;

    public Boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    
    
    public void freeResource(){
        fxcontroller = null;
        headerband = null;
        launcheritem = null;
        loader = null;
    }
    
    
    
    public FXFWindowDescriptor() {
        
    }
    
    
    
    
}
