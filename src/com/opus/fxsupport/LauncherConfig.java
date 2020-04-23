/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.syssupport.JsonHeader;
import java.util.ArrayList;


public class LauncherConfig extends JsonHeader{

    
        private ArrayList<LauncherItem> items = new ArrayList<>();

    public ArrayList<LauncherItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<LauncherItem> items) {
        this.items = items;
    }

    public void addItem (LauncherItem item){
        items.add(item);
    }
    
        private String gridposition = "CENTER";

    public String getGridposition() {
        return gridposition;
    }

    public void setGridposition(String gridposition) {
        this.gridposition = gridposition;
    }

        private int canvasheight = 768;
        
    public int getCanvasheight() {
        return canvasheight;
    }

    
    public void setCanvasheight(int canvasheight) {
        this.canvasheight = canvasheight;
    }

        private int canvaswidth = 1366;

    public int getCanvaswidth() {
        return canvaswidth;
    }

    public void setCanvaswidth(int canvaswidth) {
        this.canvaswidth = canvaswidth;
    }

    
        private String wallpaper;

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

        private boolean darkwallpaper = false;

    public boolean isDarkwallpaper() {
        return darkwallpaper;
    }

    public void setDarkwallpaper(boolean darkwallpaper) {
        this.darkwallpaper = darkwallpaper;
    }

    
        private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    
    
    
    
    public LauncherConfig() {
           
    }
    
    
    public LauncherConfig getDefault(){

        
//        wallpaper = "resources/wp1.png";
//        darkwallpaper = false;
//        setGridposition("CLEAN");

        label = "ACP 1 - A Direita";
        wallpaper = "resources/wp1.png";
        darkwallpaper = false;
        setGridposition("RIGHT");

//        wallpaper = "file:/Bascon/Imagens/saturnv_launch.jpg";
//        darkwallpaper = true;
//        setGridposition("LEFT");        
        
        
        items.add(new LauncherItem ("FX1Controller")
                .setConfigid("FX1-A")
                .setArgument("yara")
                .setIconlabel("Analises (Yara)")
                .setIconpath("resources/service_canvas.png")
                .setButtonbkgcolor("seagreen")
        );
        items.add(new LauncherItem ("FX3Controller")
                .setConfigid("FX3-A")
                .setIconlabel("Banco de Dados")
                .setIconpath("resources/build_canvas.png")
                .setIconlabelcolor("red")
                .setButtonbkgcolor("yellow")
                .setButtonlabelcolor("black")
        
        );
        items.add(new LauncherItem ("FX4Controller")
                .setConfigid("FX4-A")
                .setIconlabel("Documentação")
                .setIconpath("resources/help_canvas.png")
                .setButtonbkgcolor("brown")
                .setButtonlabelcolor("yellow")
        
        );
        
        items.add(new LauncherItem ("FX1Controller")
                .setConfigid("FX1-B")
                .setIconlabel("Analises (Yara)")
                .setIconpath("resources/settings_canvas.png")
        );
        
        
//        items.add(new LauncherItem ("FX3Controller")
//                .setConfigid("FX3-B")
//                .setIconlabel("Banco de Dados")
//                .setIconpath("resources/settings_canvas.png")
//                .setIconlabelcolor("blue")
//        
//        );
//        items.add(new LauncherItem ("FX4Controller")
//                .setConfigid("FX4-B")
//                .setIconlabel("Documentação")
//                .setIconpath("resources/help_canvas.png")
//        
//        );
//        
//        items.add(new LauncherItem ("FX4Controller")
//                .setConfigid("FX4-C")
//                .setIconlabel("Documentação")
//                .setIconpath("resources/help_canvas.png")
//        
//        );
        
        return this;
    }
    
    
    
}
