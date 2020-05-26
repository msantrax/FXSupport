/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.syssupport.PicnoUtils;

/**
 *
 * @author opus
 */
public class LauncherItem {

    
       private String appclass;

    public String getAppclass() {
        return appclass;
    }

    public LauncherItem setAppclass(String appclass) {
        this.appclass = appclass;
        return this;
    }

        private String configid;

    public String getConfigid() {
        return configid;
    }

    public LauncherItem setConfigid(String configid) {
        this.configid = configid;
        return this;
    }

        private String argument;

    public String getArgument() {
        return argument;
    }

    public LauncherItem setArgument(String argument) {
        this.argument = argument;
        return this;
    }

    
        private String iconlabel;

    public String getIconlabel() {
        return iconlabel;
    }

    public LauncherItem setIconlabel(String iconlabel) {
        this.iconlabel = iconlabel;
        return this;
    }

        private String iconlabelcolor;

    public String getIconlabelcolor() {
        return iconlabelcolor;
    }

    public LauncherItem setIconlabelcolor(String iconlabelcolor) {
        this.iconlabelcolor = iconlabelcolor;
        return this;
    }

        private String iconpath;

    public String getIconpath() {
        return iconpath;
    }

    public LauncherItem setIconpath(String iconpath) {
        this.iconpath = iconpath;
        return this;
    }

        private String buttonbkgcolor = "dodgerblue";

    public String getButtonbkgcolor() {
        return buttonbkgcolor;
    }

    public LauncherItem setButtonbkgcolor(String buttonbkgcolor) {
        this.buttonbkgcolor = buttonbkgcolor;
        return this;
    }

        private String buttonlabelcolor = "white";

    public String getButtonlabelcolor() {
        return buttonlabelcolor;
    }

    public LauncherItem setButtonlabelcolor(String buttonlabelcolor) {
        this.buttonlabelcolor = buttonlabelcolor;
        return this;
    }

    
    
    
    public LauncherItem() {
        
    }
    
    public LauncherItem(String classname) {
        appclass = classname;
    }
    
    
    public LauncherItem clone (){
        
        LauncherItem li = new LauncherItem();
        
        li.setAppclass(appclass);
        li.setArgument(argument);
        li.setButtonbkgcolor(buttonbkgcolor);
        li.setConfigid(PicnoUtils.getSUID());
        li.setIconlabel("(Cópia)" + iconlabel);
        li.setIconlabelcolor(iconlabelcolor);
        li.setIconpath(iconpath);
      
        return li;
    }
    
    
    
}
