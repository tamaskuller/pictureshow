/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import view.inputForm.InputBox;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;

/**
 *
 * @author Tamas Kuller
 */
public class InputFormParamsMap implements MapInterface {
    private List<EntryInterface<Field, InputBox>> entrySet;

    private InputFormParamsMap() {
        entrySet=new ArrayList<>();    
    }

    @Override
    public List<EntryInterface<Field, InputBox>> entrySet() {
        return entrySet;
    }
    
            
    public static class Builder{
        MapInterface<Object, Object> mapping;        
        InputFormParamsMap map;
        
        public Builder newInstance()
        {
            map=new InputFormParamsMap();
            return this;
        }
        
        public Builder addMapping(EntryInterface<Field, InputBox> entry)
        {
            map.put(entry);
            return this;
        }
        
        public InputFormParamsMap build()
                {
                return map;
                }
        
        }
    
}
