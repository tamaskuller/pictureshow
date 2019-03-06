/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform;

import controller.maps.InputFormParamsMap;
import controller.maps.MapCreatorFactV1;
import controller.maps.MapFactoryAbs;
import enums.MapFactoryTypes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import javax.swing.JButton;
import util.mapping.EntryInterface;
import view.enums.FormTypes;
import view.inputForm.InputBox;
import view.inputForm.InputForm;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.CompParams;
import view.recordtypeclasses.JFrameBaseFormParams;

/**
 *
 * @author Tamas Kuller
 */
public class CompCreateInputFormV1 extends InputForm{        
    
    
    public CompCreateInputFormV1(PicturePaneInterface pane, FormTypes type) {
        
        super(new JFrameBaseFormParams.BaseFormParamsBuild().newInstance()
                                .title("Create a(n) "+type.toString()+((pane!=null)?" on "+pane.getIconString():""))
                                .x(pane.getLocation().getX())
                                .y(pane.getLocation().getY())
                                .build());                
        createForm(pane, type);
        
    }
    
    private void createForm(PicturePaneInterface pane, FormTypes type)
        {        
            MapFactoryAbs<InputFormParamsMap,FormTypes> inputFormParamsMap=MapCreatorFactV1.getFactory(MapFactoryTypes.PARAMFIELD_INPUTBOX);                
            InputFormParamsMap mapPane=inputFormParamsMap.getMapping(type);                                    
                for (EntryInterface<Field, InputBox> entryInterface : mapPane.entrySet()) {
                    addInputBox(entryInterface.getValue(),true); 
                }
                addController(new JButton("Add"), true,
                      new InputFormListener(pane,new CompParams.PictCompParamsBuild().newInstance().build(),  mapPane, type,this));
                addController(new JButton("Cancel"),false,  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            close();
                    }
                });
                updateSizeLocation();  
                open();
            }
                        
}
