/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

import java.util.ArrayList;
import java.util.List;
import view.enums.MotionTypes;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;
import view.recordtypeclasses.AnimParams;

/**
 *
 * @author Kuller Tamas
 */
public class AnimTypeMap implements MapInterface<MotionTypes,AnimParams>{    

   private List<EntryInterface<MotionTypes,AnimParams>> entrySet;    

    public AnimTypeMap() {
        entrySet=new ArrayList<>();        
    }

    @Override
    public List<EntryInterface<MotionTypes,AnimParams>> entrySet() {
        return entrySet;
    }
               
            
    
    
    
    
}
