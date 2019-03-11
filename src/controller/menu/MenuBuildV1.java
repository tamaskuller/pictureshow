/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.menu;

import view.Menu.JPopupMenuAdj;
import controller.menu.enums.MenuEnumsV1;
import controller.menu.enums.MenuItemEnumsV1;
import controller.DB.PictDBActionsV1;
import javax.swing.JPopupMenu;
import view.Menu.MenuFlexible;
import view.Menu.MenuItemFlexible;
import view.Menu.MenuItemMap;
import view.interfaces.PictureFrameInterface;
import util.mapping.MapInterface;
import controller.DB.PictDBActionsInt;
import java.awt.Component;
import view.Menu.MenuMap;

/**
 *
 * @author Kuller Tamas
 */
public class MenuBuildV1 {
    PictureFrameInterface pictureFrame;
    JPopupMenuAdj popupMenu;
    MenuFlexible menu;
    MapInterface<MenuItemEnumsV1, MenuItemFlexible> menuItemMap;
    MapInterface<MenuEnumsV1, MenuFlexible> menuMap;
    PictDBActionsInt pictDBActions;            
    
    public MenuBuildV1(PictureFrameInterface pictureFrame) {       
        this.pictureFrame = pictureFrame;
        popupMenu=new JPopupMenuAdj();              
        menuItemMap=new MenuItemMap();         
        menuMap=new MenuMap();                
        MenuActionsInt menuActions=new MenuActionsBuildV1(pictureFrame, popupMenu, PictDBActionsV1.getInstance());        
        MenuBuildStructV1 menuStructBuild=new MenuBuildStructV1(popupMenu,menuMap, menuItemMap,menuActions);                
        menuActions.setMenuItemMap(menuStructBuild.getMenuItemMap());        
        menuActions.setMenuMap(menuStructBuild.getMenuMap());                                
    }              
    
    public JPopupMenuAdj getMenuBar() {
        return popupMenu;
    }

 

    
}
