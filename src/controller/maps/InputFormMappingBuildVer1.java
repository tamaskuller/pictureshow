/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

import enums.InputTypes;
import util.mapping.Entry;
import view.inputForm.InputBox;
import view.enums.FormTypes;
import view.recordtypeclasses.CompParams;

/**
 *
 * @author Tamas Kuller
 */
public final class InputFormMappingBuildVer1 extends MapFactoryAbs<InputFormParamsMap,FormTypes>{    
    
    @Override
    public InputFormParamsMap getMapping(FormTypes type) {
    {              
        return setMappings(type);  
    }
    }

    private static InputFormParamsMap setMappings(FormTypes type)
    {
        InputFormParamsMap map=null;
        try {       
            boolean mandatoryImage=false;
            switch (type)
            {            
            case INPUTFORM_PICT:                
            case INPUTFORM_BACKPICT:
                mandatoryImage=true;
                break;                
            }
            if (type==FormTypes.INPUTFORM_BACKPICT)
            map=new InputFormParamsMap.Builder().newInstance()
                    .build();
            else
            map=new InputFormParamsMap.Builder().newInstance()
                    .addMapping(new Entry(CompParams.class.getField("title"),new InputBox("Title:", "",20, InputTypes.TEXT,true,false)))
                    .addMapping(new Entry(CompParams.class.getField("width"),new InputBox("Width:",  "",20, InputTypes.NUMBER,true,false)))
                    .addMapping(new Entry(CompParams.class.getField("height"),new InputBox("Height:", "",20, InputTypes.NUMBER,true,false)))
                    .build();                        
            switch (type)
            {case INPUTFORM_PANE:
            case INPUTFORM_PICT:
                map.put(new Entry(CompParams.class.getField("x"),new InputBox("X position:",  "",20, InputTypes.NUMBER,false,false)));
                map.put(new Entry(CompParams.class.getField("y"),new InputBox("Y position:", "",20, InputTypes.NUMBER,false,false)));                                                                                                      
            case INPUTFORM_BACKPICT:
                map.put(new Entry(CompParams.class.getField("imagePath"),new InputBox("Path to the image you want to add:", "",20, InputTypes.PATH,mandatoryImage,true)));                
                break;
            }                
        } catch (NoSuchFieldException ex) {
            System.out.println("no field"+ex.getMessage());
        } catch (SecurityException ex) {
            System.out.println("security exception");
        }        
        return map;
    }
    
    
    @Override
    public InputFormParamsMap getMapping() {
        return null;
    }
}
