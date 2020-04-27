/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.syssupport.SMTraffic;
import com.opus.syssupport.VirnaPayload;
import com.opus.syssupport.VirnaServiceProvider;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;


public class FXFCheckListViewNumber <T> extends ListView<T>{

    private static final Logger LOG = Logger.getLogger(FXFCheckListViewNumber.class.getName());
    
    private VirnaServiceProvider appctrl;
    
    private ObservableList<FXFAnaliseListItem> list_entries = FXCollections.observableArrayList();
 
    private boolean skipfirst = true;
    
    
    public FXFCheckListViewNumber() {
        this(FXCollections.observableArrayList());
    }

    
    public FXFCheckListViewNumber(ObservableList<T> items) {
        super(items);
        
        super.setItems((ObservableList<T>)list_entries);

        setCellFactory(listView -> {    
            final FXFAnaliseStringConverter cnv = new FXFAnaliseStringConverter<FXFAnaliseListItem>();
            
            final CheckBoxListCell<T> checkBoxListCell = new CheckBoxListCell<>(item -> getItemBooleanProperty(item));
            checkBoxListCell.setConverter(cnv);
 
            checkBoxListCell.focusedProperty().addListener((o, ov, nv) -> {
                if (nv) {
                    checkBoxListCell.getParent().requestFocus();
                }
            });
            return checkBoxListCell;
        }); 
    }
    
    public void setManagement(VirnaServiceProvider appctrl) {
        this.appctrl = appctrl;
    }
    
    
    private void updateStatus() {
        
        int num = 0;
        double average = 0.0;
        double dif = 0.0;
        double rsd = 0.0;
        final ArrayList<Double> values = new ArrayList<>();
        
        
        ArrayList<FXFAnaliseListItem> checked_items = getCheckedItems();
        if (checked_items != null && checked_items.size() > 1){
            LOG.info(String.format("Now calculating to %d items", checked_items.size()));
            checked_items.forEach(v -> {
                values.add(v.getValue());
            });
            // Calculate Average
            for (Double ivl : values){
                if (ivl != 0.0){
                    average += ivl;
                    num++;
                }
            }
            average = average / num;
            // Calculate RSD
            for (double vl : values){
                if (vl != 0.0){
                    dif += Math.pow(vl-average, 2);
                }    
            }
            dif = dif / num;     
            Double rawrsd = Math.sqrt(dif);
            rsd = rawrsd;
            rsd = (rsd /average) * 100;
            if (rsd == 0.0) rsd = 0.001;
            appctrl.processSignal(new SMTraffic(0l, 0l, 0, "ENDRUN", this.getClass(),
                                   new VirnaPayload()
                                           .setObject(average)
                                           .setAuxiliar(rsd)
                                           .setFlag1(true)
            ));
        }
        else{
            appctrl.processSignal(new SMTraffic(0l, 0l, 0, "ENDRUN", this.getClass(),
                                   new VirnaPayload()
                                           .setObject(0.0)
                                           .setAuxiliar(0.0)
                                           .setFlag1(true)
            ));
        }
      
    }
    
    
    public void checkChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        
        int index = getSelectionModel().getSelectedIndex();
        LOG.info(String.format("check changed to %s @ %d", newValue, index));
        updateStatus();
    }
    
    
    
    public void initTimeList() { 
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (list_entries != null){
                    list_entries.clear();
                    LOG.info(String.format("Run list was cleared"));
                }
            }
        });
    }
    
    public void addEntry (Double value, String status){
        
        FXFAnaliseListItem ai;
        int index = list_entries.size()+1;
        
        if (skipfirst && index == 1){
            ai = new FXFAnaliseListItem(index,value, "Preliminar", false);
        }
        else{
            ai = new FXFAnaliseListItem(index,value, status, true);
        }
         
        list_entries.add(ai);
        //FXFAnaliseListItem li = (FXFAnaliseListItem)list_entries.get(list_entries.size()-1);
        BooleanProperty bp = ai.getCheck();
        bp.addListener(this::checkChanged);
        
        LOG.info(String.format("Loaded entry = %5.2f", value));
        
        ArrayList<FXFAnaliseListItem> checked_items = getCheckedItems();
        if (checked_items != null && checked_items.size() > 1){
            LOG.info(String.format("Now calculating to %d items", checked_items.size()));
        }
        updateStatus();
        
        
        
    }
    
    
    public ArrayList<FXFAnaliseListItem> getCheckedItems(){
        
        ArrayList<FXFAnaliseListItem> checkeds = new ArrayList<>();
        for(Object o : getItems()){
            FXFAnaliseListItem li =  (FXFAnaliseListItem)o;
            if(li.getCheck().get()){
                checkeds.add(li);
            }
        }
        return checkeds;
    }
    
    public BooleanProperty getItemBooleanProperty(int index) {
        
        if (index < 0 || index >= getItems().size()) return null;
        return getItemBooleanProperty(getItems().get(index));
    }
   
    public BooleanProperty getItemBooleanProperty(T item) {
        
        FXFAnaliseListItem ai = (FXFAnaliseListItem) item;
        return ai.getCheck();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    protected static class CheckListViewBitSetCheckModel<T> extends CheckBitSetModelBase<T> {
        
        private final ObservableList<T> items;
     
        CheckListViewBitSetCheckModel(final ObservableList<T> items, final Map<T, BooleanProperty> itemBooleanMap) {
            super(itemBooleanMap);
            
            this.items = items;
            this.items.addListener((ListChangeListener<T>) c -> updateMap());
            
            updateMap();
        }
       
        
        @Override public T getItem(int index) {
            return items.get(index);
        }
        
        @Override public int getItemCount() {
            return items.size();
        }
        
        @Override public int getItemIndex(T item) {
            return items.indexOf(item);
        }
        
    }
    
    
    
    
    
    
    
    
    
    
}




//final CheckBoxListCell<T> checkBoxListCell = new CheckBoxListCell<>(item -> getItemBooleanProperty(item));