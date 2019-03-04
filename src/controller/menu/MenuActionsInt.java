/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.menu;

import controller.menu.enums.MenuItemEnumsV1;
import java.awt.event.ActionListener;
import javax.swing.event.MenuListener;
import view.Menu.MenuItemFlexible;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;

/**
 *
 * @author Tamas Kuller
 */
public interface MenuActionsInt {
  //public String getString(String question);   
  public ActionListener buildMenuActionListener(EntryInterface<MenuItemEnumsV1, MenuItemFlexible> menuMapEntry);    
   public MenuListener buildMenuListener();
  
   public void setMenuItemMap(MapInterface menuMap);
   public void setMenuMap(MapInterface menuMap);

}
