/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

import enums.MapFactoryTypes;

/**
 *
 * @author Tamas Kuller
 */
public class MapCreatorFactV1 {
    public static MapFactoryAbs getFactory(MapFactoryTypes mapFactoryType){
        switch (mapFactoryType)
        {case ANIMTYPE_ANIMPARAMS: return AnimTypeMapBuildVer1.getInstance();
        case PARAMFIELD_INPUTBOX: return new InputFormMappingBuildVer1();        
        case MENU_FORMTYPE: return new MenuItemFormTypeMapBuildV1();
        }
        return null;
    }
}
