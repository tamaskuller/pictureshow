/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

import controller.maps.MapFactoryAbs;
import controller.menu.enums.MenuItemEnumsV1;
import util.mapping.Entry;
import view.enums.FormTypes;

/**
 *
 * @author Tamas Kuller
 */
public class MenuItemFormTypeMapBuildV1 extends MapFactoryAbs<MenuItemFormTypeMap,Object>{           
    MenuItemFormTypeMap map=null;
    
    @Override
    public MenuItemFormTypeMap getMapping(Object parameter) {
        return getMapping();
    }

    @Override
    public MenuItemFormTypeMap getMapping() {
        if (map==null)
            setMappings();
        return map;        
    }
    
    private void setMappings()
    {
            map=new MenuItemFormTypeMap();
            map.put(new Entry(MenuItemEnumsV1.ADD_BACKGROUND, FormTypes.INPUTFORM_BACKPICT));
            map.put(new Entry(MenuItemEnumsV1.ADD_PANE, FormTypes.INPUTFORM_PANE));
            map.put(new Entry(MenuItemEnumsV1.ADD_PICT, FormTypes.INPUTFORM_PICT));
            map.put(new Entry(MenuItemEnumsV1.NEW_LAYOUT, FormTypes.INPUTFORM_FRAME));

    }
}
