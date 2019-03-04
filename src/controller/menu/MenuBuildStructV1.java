/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.menu;

import controller.menu.enums.MenuEnumsV1;
import controller.menu.enums.MenuItemEnumsV1;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.JPopupMenu;
import util.mapping.Entry;
import view.Menu.MenuFlexible;
import view.Menu.MenuItemFlexible;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;

/**
 *
 * @author Tamas Kuller
 */
public final class MenuBuildStructV1 {    
MapInterface menuItemMap;
MapInterface menuMap;
JPopupMenu mainMenu;

    public MenuBuildStructV1(JPopupMenu mainMenu, MapInterface menuMap,MapInterface menuItemMap, MenuActionsInt menuActions) {
        this.menuItemMap=menuItemMap;
        this.menuMap=menuMap;
        this.mainMenu = mainMenu;
        createMenu(menuActions);
    }
    
    public void createMenu(MenuActionsInt menuActions) {
        MenuItemFlexible menuItem;
        MenuFlexible menu;
        MenuFlexible subMenu;

        
        menu=addMenu(menuActions,null,"Tools",menuMap,MenuEnumsV1.TOOLS);                
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_I)                                                                        
                .toolTipText("Instructions")
                .texts(Arrays.asList("Instructions"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,menu, menuItemMap, menuItem, MenuItemEnumsV1.INSTRUCTIONS);                        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_A)                                                                        
                .toolTipText("Administrator mode").texts(Arrays.asList("Disable Admin mode" , "Enable Admin mode"))
                .build();                     
        addMenuItem(menuActions,menu, menuItemMap,menuItem, MenuItemEnumsV1.ADMIN);                
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_H)                                                                        
                .toolTipText("Hide/Show All Pictures").texts(Arrays.asList("All Hide","All Show"))
                .build();        
        addMenuItem(menuActions,menu, menuItemMap, menuItem, MenuItemEnumsV1.ALLHIDE);        
        
        subMenu=addMenu(menuActions,menu,"Manage Layouts",menuMap, MenuEnumsV1.MANAGE_LAYOUTS);                
        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_N)                      
                .toolTipText("Create an empty new layout").texts(Arrays.asList("New layout"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,subMenu, menuItemMap, menuItem,  MenuItemEnumsV1.NEW_LAYOUT);        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_S)                                                                        
                .toolTipText("Save layout").texts(Arrays.asList("Save layout"))
                .setText(true)
                .build();                        
        addMenuItem(menuActions,subMenu, menuItemMap, menuItem,  MenuItemEnumsV1.SAVE);        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_L)                      
                .toolTipText("Load / Delete a layout from Database").texts(Arrays.asList("Load /Delete layout"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,subMenu, menuItemMap, menuItem,  MenuItemEnumsV1.LOAD_DELETE);
                                
        subMenu=addMenu(menuActions,menu,"Manage Component",menuMap, MenuEnumsV1.CREATE_SUBCOMP);        
        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_P)                                                                        
                .toolTipText("Add a backgorund Picture")
                .texts(Arrays.asList("Add Background"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,subMenu, menuItemMap, menuItem, MenuItemEnumsV1.ADD_BACKGROUND);        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_P)                                                                        
                .toolTipText("Add a new Picture")
                .texts(Arrays.asList("Add Picture"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,subMenu, menuItemMap, menuItem, MenuItemEnumsV1.ADD_PICT);        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_N)                                                                        
                .toolTipText("Add a new PicturePane")
                .texts(Arrays.asList("Add Pane"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,subMenu, menuItemMap, menuItem, MenuItemEnumsV1.ADD_PANE);                        
        
        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_B)                      
                .toolTipText("Remove BackGround picture").texts(Arrays.asList("Remove Background"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,subMenu, menuItemMap, menuItem,  MenuItemEnumsV1.REMOVE_BACKGROUND);                

        menuItem=new MenuItemFlexible.MenuItemBuild().newInstance(KeyEvent.VK_D)                      
                .toolTipText("Delete Component").texts(Arrays.asList("Delete Component"))
                .setText(true)                
                .build();
        addMenuItem(menuActions,menu, menuItemMap, menuItem,  MenuItemEnumsV1.DELETE_COMP);                
        
        
        
    }
    
    private MenuFlexible addMenu(MenuActionsInt menuActions,MenuFlexible menu, String name,MapInterface map, MenuEnumsV1 menuEnum) {
            MenuFlexible subMenu=new MenuFlexible(name);    
            if (menu==null)                
                mainMenu.add(subMenu);                  
            else
                menu.add(subMenu);
            EntryInterface menuMapEntry=new Entry(menuEnum,subMenu);
            map.put(menuMapEntry);                    
            subMenu.addMenuListener(menuActions.buildMenuListener());        
            return subMenu;
    }
    
    private void addMenuItem(MenuActionsInt menuActions,MenuFlexible menu,MapInterface map,MenuItemFlexible menuItem,  MenuItemEnumsV1 menuItemEnum) {        
        if (menu==null)                
            mainMenu.add(menuItem);                  
        else
            menu.addMenuItemFlexible(menuItem);
        EntryInterface menuMapEntry=new Entry(menuItemEnum,menuItem);
        map.put(menuMapEntry);        
        menuItem.addActionListener(menuActions.buildMenuActionListener(menuMapEntry));        
        System.out.println("menumap:"+menuItem.toString());
        
    }

    public MapInterface getMenuItemMap() {
        return menuItemMap;
    }

    public MapInterface getMenuMap() {
        return menuMap;
    }

        

        
}
