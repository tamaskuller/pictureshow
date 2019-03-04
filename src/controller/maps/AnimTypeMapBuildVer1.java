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
                instance.animTypeMap.put(new Entry(MotionTypes.FastFlowing, new AnimParams(50, 2)));
                instance.animTypeMap.put(new Entry(MotionTypes.MediumFlowing, new AnimParams(50, 5)));
                instance.animTypeMap.put(new Entry(MotionTypes.SlowFlowing, new AnimParams(50, 10)));
                instance.animTypeMap.put(new Entry(MotionTypes.Simple, new AnimParams(1, 1)));                                
                instance.animTypeMap.put(new Entry(MotionTypes.Fastest, new AnimParams(1, 1)));                                
                }

    @Override
    public AnimTypeMap getMapping(Object parameter) {
        return instance.animTypeMap;
    }

    
}
    
    
