/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author opus
 */
public class FXFConfirmationDialog {

    
    private static final Logger LOG = Logger.getLogger(FXFConfirmationDialog.class.getName());

    private StageStyle style = StageStyle.UNDECORATED;
    private Modality modality = Modality.WINDOW_MODAL;
    private boolean useblocking = true;
    private int closeafter = 0;
    private String custom_header = "";
    private Window owner = null;
    private String custom_graphic = "";
    private String title = "";
    private String message = "";
    private String optional_header = "Ooops !!";
    private boolean usecancel = true;
    
    
    private Alert dlg;
    
    
    public FXFConfirmationDialog() {
        
    }

    
    public Alert activateAlert() {
      
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
                
                ButtonType loginButtonType = new ButtonType("Test");

                dlg = new Alert(AlertType.WARNING, "");
                dlg.initModality(modality);
                dlg.initOwner(owner);
                dlg.setTitle(title);
                dlg.getDialogPane().setContentText(message);
                dlg.getDialogPane().getButtonTypes().add(loginButtonType);
                
                if (usecancel) dlg.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                
                if (!optional_header.isEmpty()) {
                    dlg.getDialogPane().setHeaderText(optional_header);
                }

                if (!custom_graphic.isEmpty()) {
                    //dlg.getDialogPane().setGraphic(new ImageView(new Image(getClass().getResource(custom_graphic).toExternalForm())));
                }

                dlg.initStyle(style);
                DialogPane dlgp = dlg.getDialogPane();
                dlgp.getStylesheets().add( getClass().getResource("dialogs.css").toExternalForm());
                dlgp.getStyleClass().add("dialog-pane");
         
                
                if (closeafter > 0) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(closeafter);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> dlg.close());
                    }).start();
                }
   
//                if (useblocking) {
//                    dlg.showAndWait().ifPresent(result -> {
//                        LOG.info("Result is " + result);
//                        if (result.getButtonData() == ButtonData.CANCEL_CLOSE){
//                            
//                        }
//                    });
//                } else {
//                    dlg.show();
//                    dlg.resultProperty().addListener(o -> {
//                        LOG.info("Result is: " + dlg.getResult());
//                    });
//                    LOG.info("This println is _after_ the show method - we're non-blocking!");
//                }

//            }
//            
//        });
        
        return dlg;
        
    }
    
    
    // ========================= GET/SET ======================================================================
    
    public StageStyle getStyle() {
        return style;
    }

    public FXFConfirmationDialog setStyle(StageStyle style) {
        this.style = style;
        return this;
    }

    public Modality getModality() {
        return modality;
    }

    public FXFConfirmationDialog setModality(Modality modality) {
        this.modality = modality;
        return this;
    }

    public boolean isUseblocking() {
        return useblocking;
    }

    public FXFConfirmationDialog setUseblocking(boolean useblocking) {
        this.useblocking = useblocking;
        return this;
    }

    public boolean isUsecancel() {
        return usecancel;
    }

    public FXFConfirmationDialog setUsecancel(boolean usecancel) {
        this.usecancel = usecancel;
        return this;
    }
    
    
    public int getCloseafter() {
        return closeafter;
    }

    public FXFConfirmationDialog setCloseafter(int closeafter) {
        this.closeafter = closeafter;
        return this;
    }

    public String getCustom_header() {
        return custom_header;
    }

    public FXFConfirmationDialog setCustom_header(String custom_header) {
        this.custom_header = custom_header;
        return this;
    }

    public Window getOwner() {
        return owner;
    }

    public FXFConfirmationDialog setOwner(Window owner) {
        this.owner = owner;
        return this;
    }

    public String getCustom_graphic() {
        return custom_graphic;
    }

    public FXFConfirmationDialog setCustom_graphic(String custom_graphic) {
        this.custom_graphic = custom_graphic;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FXFConfirmationDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public FXFConfirmationDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getOptional_header() {
        return optional_header;
    }

    public FXFConfirmationDialog setOptional_header(String optional_header) {
        this.optional_header = optional_header;
        return this;
    }
    
    
    
    
}
