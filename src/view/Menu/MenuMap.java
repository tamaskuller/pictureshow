/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Menu;

import controller.menu.enums.MenuEnumsV1;
import java.util.ArrayList;
import java.util.List;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;

/**
 *
 * @author Tamas Kuller
 */
public class MenuMap implements MapInterface<MenuEnumsV1, MenuFlexible>{    
    
    public List<EntryInterface<MenuEnumsV1, MenuFlexible>> entrySet;

    public MenuMap() {
        entrySet=new ArrayList<>();
    }

    @Override
    public List<EntryInterface<MenuEnumsV1, MenuFlexible>> entrySet() {
        return entrySet;
    }

    
    
}
