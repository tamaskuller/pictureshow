/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Menu;

import controller.menu.enums.MenuItemEnumsV1;
import java.util.ArrayList;
import java.util.List;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;

/**
 *
 * @author Tamas Kuller
 */
public class MenuItemMap implements MapInterface<MenuItemEnumsV1, MenuItemFlexible>{    
    
    public List<EntryInterface<MenuItemEnumsV1, MenuItemFlexible>> entrySet;

    public MenuItemMap() {
        entrySet=new ArrayList<>();
    }

    @Override
    public List<EntryInterface<MenuItemEnumsV1, MenuItemFlexible>> entrySet() {
        return entrySet;
    }

    
    
}
