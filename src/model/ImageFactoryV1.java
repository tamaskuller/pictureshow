/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enums.DataSourceTypes;
import view.interfaces.NamedImageInt;

/**
 *
 * @author kulle
 */
public class ImageFactoryV1 {

    private static final DataSourceTypes defaultSourceType=DataSourceTypes.DISK;
    
    public static NamedImageInt getImage(DataSourceTypes sourceType, String path, String name)
    {
        
            if (sourceType==null)
            {return ImageOperationsV1.getNamedImage(path,name);
            }
            switch (sourceType)
            {
            case DISK: return ImageOperationsV1.getNamedImage(path,name);                
            }                 
            return ImageOperationsV1.getNamedImage(path,name);           
        
    }        
    
}
