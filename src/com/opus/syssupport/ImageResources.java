/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author opus
 */
public class ImageResources{

    private static final Logger LOG = Logger.getLogger(ImageResources.class.getName());

    public static String ICONSPATH = "/home/acp/PP200/Resources/Icons";
    public static String WALLSPATH = "/home/acp/PP200/Resources/Wallpapers";
    public static String WALLTHUMBSPATH = "/home/acp/PP200/Resources/Wallthumbs";
    public static String IMAGESPATH = "/home/acp/PP200/Resources/Images";
    public static String IMAGETHUMBSPATH = "/home/acp/PP200/Resources/Imagethumbs";
    
    public static enum IMAGECLASS {ICON, IMAGE, IMAGE_THUMB, WALL, WALL_THUMB, FULLPATH} ;
    
    private ArrayList<ImageResource> resources = new ArrayList<>();
    
    private Image default_image;
    
    private int loaded_resources=0;
    private int loaded_icons = 0;
    private int loaded_thumbs = 0;
    private int loaded_images = 0;
    private int loaded_wallpapers = 0;
    
    public ImageResources() {
    
       registerIcons(); 
       registerImages();
       registerWall();
       
       default_image = new Image("resources/build_canvas.png", 150, 100, false, false);
       
       LOG.info(String.format("Registered %d graphic resources where : %d are Icons / %d are Thumbs / %d are Images / %d are Wallpapers ", 
               loaded_resources, loaded_icons, loaded_thumbs, loaded_images, loaded_wallpapers));
    }
    
    
    public ImageResource getResource (String id, ImageResources.IMAGECLASS iclass){
         
         for (ImageResource irc : resources){
            if (irc.getImage_class() == iclass && irc.getName().equals(id)){
                return irc;
            }
        }
        return null; 
     }
    
    
    public Image getImage (String id, ImageResources.IMAGECLASS iclass){
       
        Image img = default_image;
        
        for (ImageResource irc : resources){
            if ((irc.getImage_class() == iclass && irc.getName().equals(id))
                    || ((iclass == ImageResources.IMAGECLASS.FULLPATH) && irc.getFpath().equals(id))){
                try {
                    img = irc.getImage();
                    return img;
                } catch (FileNotFoundException ex) {
                    LOG.severe(String.format("Failed to load Image %s", id));
                }
            }
        }
        return img; 
    }
    
    public Image getImage (String id, double sizew, double sizeh){
       
        Image img = default_image;
        
        for (ImageResource irc : resources){
            if (irc.getFpath().equals(id)){
                try {
                    if (sizew == 0.0 || sizeh == 0.0){
                        img = irc.getImage();
                    }
                    img = irc.getImage(sizew, sizeh);
                    return img;
                } catch (FileNotFoundException ex) {
                    LOG.severe(String.format("Failed to load Image %s", id));
                }
            }
        }
        return img; 
    }
    
    public ObservableList<Rectangle> getDisplayNodes(ImageResources.IMAGECLASS iclass){
    
        ObservableList<Rectangle> list = FXCollections.<Rectangle>observableArrayList();
        
        for (ImageResource irc : resources){
            if (irc.getImage_class() == iclass){
                try {
                    Rectangle rectangle = new Rectangle(0, 0, 80, 80);
                    ImagePattern pattern = new ImagePattern(irc.getImage());
                    rectangle.setFill(pattern);
                    rectangle.setUserData(irc);
                    list.add(rectangle);
                    //LOG.info(String.format("Display Node %s was created", irc.getName()));
                } catch (FileNotFoundException ex) {
                    LOG.severe(String.format("Unable to load image from resource @ ", irc.getFpath()));
                }
            }
        }
        return list;
    } 
     
    
    public void registerIcons(){
        
        try {
            Path p = Paths.get(ICONSPATH);
           
            Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws FileNotFoundException{
                    
                    String sfile = file.getFileName().toString();
                    String fpath = ICONSPATH + "/" + sfile;
                    int i = sfile.lastIndexOf('.');
                    String name = sfile.substring(0, i);
             
                    ImageResource ir = new ImageResource(fpath, name, ImageResources.IMAGECLASS.ICON);
                    resources.add(ir);
                    loaded_icons++;
                    loaded_resources++;
                    //LOG.info(String.format("Registering Icon [%s] found @ %s", name, fpath));
                    
                    Image img = new Image(new FileInputStream(fpath));
                    ir.setImage(img);
                    
                    return FileVisitResult.CONTINUE;
                }  
            });
            
        } catch (IOException ex) {
            Logger.getLogger(ImageResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void registerImages(){
        
        try {
            Path p0 = Paths.get(IMAGESPATH);         
            Files.walkFileTree(p0, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
                    
                    String sfile = file.getFileName().toString();
                    String fpath = IMAGESPATH + "/" + sfile;
                    int i = sfile.lastIndexOf('.');
                    String name = sfile.substring(0, i);
             
                    resources.add(new ImageResource(fpath, name, ImageResources.IMAGECLASS.IMAGE));
                    loaded_images++;
                    loaded_resources++;
                    //LOG.info(String.format("Registering Image [%s] found @ %s", name, fpath));
                    return FileVisitResult.CONTINUE;
                }  
            });
            
            Path p1 = Paths.get(IMAGETHUMBSPATH);         
            Files.walkFileTree(p1, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws FileNotFoundException{
                    
                    String sfile = file.getFileName().toString();
                    String fpath = IMAGETHUMBSPATH + "/" + sfile;
                    int i = sfile.lastIndexOf('.');
                    String name = sfile.substring(0, i);
             
                    ImageResource ir = new ImageResource(fpath, name, ImageResources.IMAGECLASS.IMAGE_THUMB);
                    resources.add(ir);
                    loaded_thumbs++;
                    loaded_resources++;
                    //LOG.info(String.format("Registering Image Thumb [%s] found @ %s", name, fpath));
                    
                    Image img = new Image(new FileInputStream(fpath));
                    ir.setImage(img);
                    
                    
                    return FileVisitResult.CONTINUE;
                }  
            });
          
        } catch (Exception ex) {
            Logger.getLogger(ImageResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void registerWall(){
        
        Path p;
        
        try {
            p = Paths.get(WALLSPATH);         
            Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
                    
                    String sfile = file.getFileName().toString();
                    String fpath = WALLSPATH + "/" + sfile;
                    int i = sfile.lastIndexOf('.');
                    String name = sfile.substring(0, i);
             
                    resources.add(new ImageResource(fpath, name, ImageResources.IMAGECLASS.WALL));
                    loaded_wallpapers++;
                    loaded_resources++;
                    //LOG.info(String.format("Registering Wallpaper [%s] found @ %s", name, fpath));
                    return FileVisitResult.CONTINUE;
                }  
            });
            
            p = Paths.get(WALLTHUMBSPATH);         
            Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws FileNotFoundException{
                    
                    String sfile = file.getFileName().toString();
                    String fpath = WALLTHUMBSPATH + "/" + sfile;
                    int i = sfile.lastIndexOf('.');
                    String name = sfile.substring(0, i);
             
                    ImageResource ir = new ImageResource(fpath, name, ImageResources.IMAGECLASS.WALL_THUMB);
                    resources.add(ir);
                    loaded_thumbs++;
                    loaded_resources++;
                    //LOG.info(String.format("Registering Wallpaper Thumb [%s] found @ %s", name, fpath));
                    
                    Image img = new Image(new FileInputStream(fpath));
                    ir.setImage(img);
                    
                    return FileVisitResult.CONTINUE;
                }  
            });
        
        } catch (Exception ex) {
            Logger.getLogger(ImageResources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}




//int count = 0;
//       for (ImageResource irc : resources){
//           
//           if (irc.getImage_class() == ImageResources.IMAGECLASS.IMAGE_THUMB){
//               LOG.info(String.format("Found Image Thumb : %s @ %s", irc.getName(), irc.getFpath()));
//               count++;
//           }
//           
//           if (irc.getImage_class() == ImageResources.IMAGECLASS.WALL_THUMB){
//               LOG.info(String.format("Found Wall Thumb : %s @ %s", irc.getName(), irc.getFpath()));
//               count++;
//           }
//           
//       }
//       LOG.info(String.format("Found %d thumbs...", count));