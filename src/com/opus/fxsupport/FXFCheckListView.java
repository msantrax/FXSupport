/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opus.fxsupport;

import com.opus.glyphs.FontAwesomeIcon;
import com.opus.glyphs.GlyphsBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author opus
 */
public class FXFCheckListView <T> extends ListView<T>{

    private static final Logger LOG = Logger.getLogger(FXFCheckListView.class.getName());
    
    protected final Map<T, BooleanProperty> itemBooleanMap;

    public FXFCheckListView() {
        this(FXCollections.observableArrayList());
    }
    
    
    public FXFCheckListView(ObservableList<T> items) {
        super(items);
        this.itemBooleanMap = new HashMap<>();
        
        setCheckModel(new CheckListViewBitSetCheckModel<>(getItems(), itemBooleanMap));     
        itemsProperty().addListener(ov -> {
            setCheckModel(new CheckListViewBitSetCheckModel<>(getItems(), itemBooleanMap));
        });
        
        
        setCellFactory(listView -> {
            final CheckBoxListCell<T> checkBoxListCell = new CheckBoxListCell<>(item -> getItemBooleanProperty(item));
            checkBoxListCell.focusedProperty().addListener((o, ov, nv) -> {
                if (nv) {
                    checkBoxListCell.getParent().requestFocus();
                }
            });
            return checkBoxListCell;
        });

        
        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE) {
                T item = getSelectionModel().getSelectedItem();
                if (item != null) {
                    final IndexedCheckModel<T> checkModel = getCheckModel();
                    if (checkModel != null) {
                        if (checkModel.isChecked(item)) {
                            checkModel.clearCheck(item);
                        } else {
                            checkModel.check(item);
                        }
                    }
                }
            }
        });

    }
   
    // --- Check Model
    protected ObjectProperty<IndexedCheckModel<T>> checkModel = new SimpleObjectProperty<>(this, "checkModel"); //$NON-NLS-1$
    
    /**
     * Sets the 'check model' to be used in the CheckListView - this is the
     * code that is responsible for representing the selected state of each
     * {@link CheckBox} - that is, whether each {@link CheckBox} is checked or 
     * not (and not to be confused with the 
     * selection model concept, which is used in the ListView control to 
     * represent the selection state of each row).. 
     */
    public final void setCheckModel(IndexedCheckModel<T> value) {
        checkModelProperty().set(value);
    }

    /**
     * Returns the currently installed check model.
     */
    public final IndexedCheckModel<T> getCheckModel() {
        return checkModel == null ? null : checkModel.get();
    }

    /**
     * The check model provides the API through which it is possible
     * to check single or multiple items within a CheckListView, as  well as inspect
     * which items have been checked by the user. Note that it has a generic
     * type that must match the type of the CheckListView itself.
     */
    public final ObjectProperty<IndexedCheckModel<T>> checkModelProperty() {
        return checkModel;
    }
    
    
    
    public BooleanProperty getItemBooleanProperty(int index) {
        if (index < 0 || index >= getItems().size()) return null;
        return getItemBooleanProperty(getItems().get(index));
    }
   
    public BooleanProperty getItemBooleanProperty(T item) {
        return itemBooleanMap.get(item);
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
