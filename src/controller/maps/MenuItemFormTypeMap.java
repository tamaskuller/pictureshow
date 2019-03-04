/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

import controller.menu.enums.MenuItemEnumsV1;
import java.util.ArrayList;
import java.util.List;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;
import view.enums.FormTypes;

/**
 *
 * @author Tamas Kuller
 */
public class MenuItemFormTypeMap implements MapInterface<MenuItemEnumsV1, FormTypes>{

    List<EntryInterface<MenuItemEnumsV1, FormTypes>> entrySet;

    public MenuItemFormTypeMap() {
        this.entrySet = new ArrayList<>();
    }
    
    
    @Override
    public List<EntryInterface<MenuItemEnumsV1, FormTypes>> entrySet() {
        return entrySet;
    }

    
}
