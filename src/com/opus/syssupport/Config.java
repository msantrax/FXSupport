/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.syssupport;


import static com.opus.syssupport.PicnoUtils.file_separator;
import java.util.Properties;
import java.util.logging.Logger;


/**
 *
 * @author opus
 */
public class Config {

    private static final Logger LOG = Logger.getLogger(Config.class.getName());

    private static transient Config instance; 
    public static transient String configpath;
    
    
    
     // ============================================== PROPERTIES ===============
        private String report_dir;

    public String getReport_dir() {
        return report_dir;
    }

    public void setReport_dir(String report_dir) {
        report_dir = report_dir;
    }

        private String context_dir;

    public String getContext_dir() {
        return context_dir;
    }

    public void setContext_dir(String context_dir) {
        context_dir = context_dir;
    }

        private String export_dir;

    public String getExport_dir() {
        return export_dir;
    }

    public void setExport_dir(String export_dir) {
        export_dir = export_dir;
    }

        private String aux_dir;

    public String getAux_dir() {
        return aux_dir;
    }

    public void setAux_dir(String aux_dir) {
        this.aux_dir = aux_dir;
    }

    
        private String profile_dir;

    public String getProfile_dir() {
        return profile_dir;
    }

    public void setProfile_dir(String profile_dir) {
        this.profile_dir = profile_dir;
    }

    
    
        private String template_dir;

    public String getTemplate_dir() {
        return template_dir;
    }

    public void setTemplate_dir(String template_dir) {
        this.template_dir = template_dir;
    }

    
        private String templatepath_full;

    public String getTemplatepath_full() {
        return templatepath_full;
    }

    public void setTemplatepath_full(String templatepath_full) {
        templatepath_full = templatepath_full;
    }

    
        private String templatepath_simple;

    public String getTemplatepath_simple() {
        return templatepath_simple;
    }

    public void setTemplatepath_simple(String templatepath_simple) {
        templatepath_simple = templatepath_simple;
    }

    
        private String database_dir;

    public String getDatabase_dir() {
        return database_dir;
    }

    public void setDatabase_dir(String database_dir) {
        this.database_dir = database_dir;
    }

        private String database_file;

    public String getDatabase_file() {
        return database_file;
    }

    public void setDatabase_file(String database_file) {
        this.database_file = database_file;
    }

        private String database_backupdir;

    public String getDatabase_backupdir() {
        return database_backupdir;
    }

    public void setDatabase_backupdir(String database_backupdir) {
        this.database_backupdir = database_backupdir;
    }

        private int database_backup_period = 604800;

    public int getDatabase_backup_period() {
        return database_backup_period;
    }

    public void setDatabase_backup_period(int database_backup_period) {
        this.database_backup_period = database_backup_period;
    }

        private boolean use_timesavings = true;

    public boolean isUse_timesavings() {
        return use_timesavings;
    }

    public void setUse_timesavings(boolean use_timesavings) {
        this.use_timesavings = use_timesavings;
    }

        private String timezone = "America/Sao_Paulo";

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

       
    public static Config getInstance(){
        if (instance == null) {instance = new Config();}
        return instance;
    }
    
    
    public Config() {
             
        context_dir = "/home/acp/PP200/";
        configpath = context_dir + "pp200config.json";

        template_dir = context_dir + "Templates/";
        templatepath_full = template_dir + "pdfroot_full.pdf";
        templatepath_simple = template_dir + "pdfroot.pdf";

        export_dir = context_dir + "Export/";
        report_dir = context_dir + "Reports/";
        profile_dir = context_dir + "Profiles/";
        aux_dir = context_dir + "Auxiliar/";

        database_dir = context_dir + "Database/";
        database_file = database_dir + "acp1";
        database_backupdir = context_dir + "Database/";
            
        instance = this;
        
    }
    
    
    
    
    
    
}
