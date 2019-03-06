/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

import view.recordtypeclasses.AnimParams;
import util.mapping.Entry;
import view.enums.MotionTypes;

/**
 *
 * @author Kuller Tamas
 */
public final class AnimTypeMapBuildVer1 extends MapFactoryAbs<AnimTypeMap,Object>{
    private static final AnimTypeMapBuildVer1 instance;
     AnimTypeMap animTypeMap;
    
    static { instance=new AnimTypeMapBuildVer1();
            setMappings();
    }
     
    private AnimTypeMapBuildVer1() {
        animTypeMap=new AnimTypeMap();
    }      
        
    public static AnimTypeMapBuildVer1 getInstance()
    {
            return instance;
    }
        
    
    @Override
    public AnimTypeMap getMapping() {                        
            return instance.animTypeMap;
    }

    private static void setMappings()
                {
                instance.animTypeMap.put(new Entry(MotionTypes.FASTEST_FLOWING, new AnimParams(10,3)));                                
                instance.animTypeMap.put(new Entry(MotionTypes.FAST_FLOWING, new AnimParams(25, 5)));
                instance.animTypeMap.put(new Entry(MotionTypes.MEDIUM_FLOWING, new AnimParams(40, 10)));
                instance.animTypeMap.put(new Entry(MotionTypes.SLOW_FLOWING, new AnimParams(40, 20)));
                instance.animTypeMap.put(new Entry(MotionTypes.SIMPLE, new AnimParams(1, 1)));                                
                
                }

    @Override
    public AnimTypeMap getMapping(Object parameter) {
        return instance.animTypeMap;
    }

    
}
    
    
