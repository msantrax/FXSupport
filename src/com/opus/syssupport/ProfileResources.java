/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 *
 * @author opus
 */
public class ProfileResources {

    private static final Logger LOG = Logger.getLogger(ProfileResources.class.getName());
    
    private ArrayList<ProfileResource> resources = new ArrayList<>();

    public static Profile default_profile;
    public static Profile blaine_default;
    
    
    
    public ProfileResources() {
        
        //default_profile = Profile.getInstance();
        scanProfiles(PicnoUtils.PUBLIC_PREFIX + "/Profiles/");
        
    }
    
    
    public ProfileResource getResource (String id){
        
         for (ProfileResource irc : resources){
            if (irc.getName().equals(id)){
                return irc;
            }
        }
        return null; 
    }
    
    public HashSet<String> getResourcesList(String type){
        
        HashSet<String> res = new HashSet<>();
        String [] tokens = type.split(":");
        String typeprefix = tokens[0];
        
        for (ProfileResource irc : resources){
            if (irc.getName().contains(typeprefix)){
                res.add(irc.getLabel());
            }
        }
        return res;
    }
    
    
    public ProfileResource getByLabel(String label){
        
        for (ProfileResource irc : resources){
            if (irc.getLabel().equals(label)){
                return irc;
            }
        }
        return null;
    }
    
    public void updateProfile (String id){
        
        for (ProfileResource irc : resources){
            if (irc.getName().equals(id)){
                try {
                    String path = irc.getFpath();
                    LOG.info(String.format("Saving Profile [%s] @ %s", id, path)); 
                    PicnoUtils.saveJson (path, irc.getProfile(), true);
                } catch (Exception ex) {
                    LOG.severe(String.format("Failed to update Profile %s", id));
                }
            }
        }
    }
    
    public <T extends Profile> T getProfile (String id, Class<? extends Profile> profclass ){
       
        Profile prf = default_profile;
        
        for (ProfileResource irc : resources){
            if (irc.getName().equals(id)){
                try {
                    prf = irc.getProfile(profclass);
                    //prf = PicnoUtils.loadJson (irc.getFpath(), profclass); 
                    return (T) prf;
                } catch (Exception ex) {
                    LOG.severe(String.format("Failed to load Profile %s", id));
                }
            }
        }
        return (T)prf; 
    }
    
    
    public void scanProfiles (String searchpath){
        
        Path p = Paths.get(searchpath);
        
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        try {
            Files.list(p)
            .filter(Files::isRegularFile)
            .forEach(e -> {
                try {
                    String fpath = e.toFile().getAbsolutePath();
                    String json_out = PicnoUtils.loadFile(fpath);                    
                    Profile prof = gson.fromJson(json_out, Profile.class); 
                    ProfileResource pfr = new ProfileResource(fpath, prof.getArgument(), prof.getClasstype(),  prof.getLabel());
                    //LOG.info(String.format("Found Profile [%s] type [%s / %s] @ %s", prof.getLabel(), prof.getArgument(), prof.getClasstype(), fpath)); 
                    resources.add(pfr);
                } catch (IOException ex) {
                    LOG.severe(String.format("Load Profiles failed to scan directory @ %s due : ", p.getFileName(), ex.getMessage()));
                }
            });
        } catch (IOException ex) {
            LOG.severe(String.format("Load Profiles failed to scan directory @ %s due : ", p.getFileName(), ex.getMessage()));
        }
           
        LOG.info(String.format("Added %d profiles", resources.size()));  
    }   
}




//        profpaths.values().forEach(new Consumer<String>() {
//            @Override
//            public void accept(String e) {
//                Profile prof;
//                
//                
//            } 
//        });
        