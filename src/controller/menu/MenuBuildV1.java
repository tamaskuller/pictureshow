/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.menu;

import controller.menu.enums.MenuEnumsV1;
import controller.menu.enums.MenuItemEnumsV1;
import controller.menu.MenuBuildStructV1;
import controller.DB.PictDBActionsV1;
import controller.menu.MenuActionsBuildV1;
import javax.swing.JPopupMenu;
import controller.menu.MenuActionsInt;
import view.Menu.MenuFlexible;
import view.Menu.MenuItemFlexible;
import view.Menu.MenuItemMap;
import view.interfaces.PictureFrameInterface;
import util.mapping.MapInterface;
import controller.DB.PictDBActionsInt;
import view.Menu.MenuMap;

/**
 *
 * @author Kuller Tamas
 */
public class MenuBuildV1 {
    PictureFrameInterface pictureFrame;
    JPopupMenu popupMenu;
    MenuFlexible menu;
    MapInterface<MenuItemEnumsV1, MenuItemFlexible> menuItemMap;
    MapInterface<MenuEnumsV1, MenuFlexible> menuMap;
    PictDBActionsInt pictDBActions;    
    
    
    
    public MenuBuildV1(PictureFrameInterface pictureFrame) {       
        this.pictureFrame = pictureFrame;
        popupMenu=new JPopupMenu();       
        menuItemMap=new MenuItemMap();         
        menuMap=new MenuMap();                
        synchronized (this){
        MenuActionsInt menuActions=new MenuActionsBuildV1(pictureFrame, popupMenu, PictDBActionsV1.getInstance());        
        MenuBuildStructV1 menuStructBuild=new MenuBuildStructV1(popupMenu,menuMap, menuItemMap,menuActions);                
        menuActions.setMenuItemMap(menuStructBuild.getMenuItemMap());        
        menuActions.setMenuMap(menuStructBuild.getMenuMap());                                
        }
        
        
    }    
           
    
    public JPopupMenu getMenuBar() {
        return popupMenu;
    }

 

    
}
