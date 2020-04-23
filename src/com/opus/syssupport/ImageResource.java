/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

/**
 *
 * @author opus
 */
public class ImageResource {
    
        private String fpath;

    public String getFpath() {
        return fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

            private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

        private Image image;

    public Image getImage() throws FileNotFoundException {
        if (image == null){
            image = new Image(new FileInputStream(fpath));     
        }
        return image;
    }

    public Image getImage(double sizew, double sizeh) throws FileNotFoundException {
        if (image == null){
            image = new Image(new FileInputStream(fpath), sizew, sizeh, false, true);     
        }
        return image;
    }
    
    
    public void setImage(Image image) {
        this.image = image;
    }

        private ImageResources.IMAGECLASS image_class;

    public ImageResources.IMAGECLASS getImage_class() {
        return image_class;
    }

    public void setImage_class(ImageResources.IMAGECLASS image_class) {
        this.image_class = image_class;
    }

    
 
    public ImageResource() {


    }

    public ImageResource(String fpath, String name, ImageResources.IMAGECLASS iclass) {
        this.fpath = fpath;
        this.name = name;
        this.image_class = iclass;
    }
    
   
    
}
