/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.menu;

import controller.menu.enums.MenuEnumsV1;
import controller.menu.enums.MenuItemEnumsV1;
import controller.maps.MenuItemFormTypeMap;
import enums.MapFactoryTypes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import view.FormFactoryV1;
import controller.maps.MapCreatorFactV1;
import controller.maps.MapFactoryAbs;
import view.Menu.MenuFlexible;
import view.Menu.MenuItemFlexible;
import view.enums.FormTypes;
import view.enums.MotionTypes;
import view.inputForm.InputFormInt;
import view.interfaces.PictureComponentInterface;
import view.interfaces.PictureFrameInterface;
import view.interfaces.PicturePaneInterface;
import util.mapping.EntryInterface;
import util.mapping.MapInterface;
import view.listeners.CompListenerBuildSingVer1;
import controller.DB.PictDBActionsInt;

/**
 *
 * @author Tamas Kuller
 */
public class MenuActionsBuildV1 implements MenuActionsInt{    
    private PictureFrameInterface pictureFrame;
    private JPopupMenu popupMenu;
    private PictDBActionsInt pictDBActions;
    private MapInterface<MenuItemEnumsV1, MenuItemFlexible> menuItemMap;
    private MapInterface<MenuEnumsV1, MenuFlexible> menuMap;
    private InputFormInt framesForm=null;
        
    //MenuFlexible menu;

    public MenuActionsBuildV1(PictureFrameInterface pictureFrame, JPopupMenu popupMenu, PictDBActionsInt pictDBActions) {
        this.pictureFrame = pictureFrame;
        this.popupMenu = popupMenu;
        this.pictDBActions = pictDBActions;        
        buildPopupListener();
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void setMenuItemMap(MapInterface map) {
        this.menuItemMap = map;
    }

    @Override
    public void setMenuMap(MapInterface map) {
        this.menuMap = map;     
        
    }
        
    @Override
    public ActionListener buildMenuActionListener(EntryInterface<MenuItemEnumsV1, MenuItemFlexible> menuItemMapEntry)
    {
        ActionListener listener=null; 
        MenuItemEnumsV1 menuItem=menuItemMapEntry.getKey();
        switch (menuItem)
        {case INSTRUCTIONS:
            listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormFactoryV1.createForm(FormTypes.INSTRUCTION_FORM, pictureFrame, pictureFrame, null);
                }
            };
            break;        
        case ADMIN:            
            listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pictureFrame.adminSwitched();                                                
                }
            };
            break;        
        case ALLHIDE:
            listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pictureFrame.hideShowSwitch();                
                }
            };
            break;
        case SAVE:
            listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                                 
                FormFactoryV1.createForm(FormTypes.SAVE_FRAME, pictureFrame, pictureFrame, null);               
                }
            };  
            break;            
        case LOAD_DELETE:
            listener=new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {                                    
                FormFactoryV1.createForm(FormTypes.FRAMESFORM, pictureFrame,pictureFrame, null);
                
                }
            };
            break;                    
        case ADD_BACKGROUND:
        case ADD_PANE:  
        case ADD_PICT:  
        case NEW_LAYOUT:
            listener=new ActionListener() {                                      
            private FormTypes getType(MenuItemEnumsV1 menuItem)
            {               
                MapFactoryAbs<MenuItemFormTypeMap,Object> menuItemFormTypeMapBuild=MapCreatorFactV1.getFactory(MapFactoryTypes.MENU_FORMTYPE);
                MenuItemFormTypeMap map=menuItemFormTypeMapBuild.getMapping();
                return map.get(menuItem);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {                                  
                FormFactoryV1.createForm(getType(menuItem),(menuItem!=MenuItemEnumsV1.NEW_LAYOUT)?(PicturePaneInterface) popupMenu.getInvoker():null,pictureFrame,null);                                
                }                
            };
            break; 
        case REMOVE_BACKGROUND:
            listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PictureComponentInterface toRemoveParent=(PictureComponentInterface) popupMenu.getInvoker();                
                if (JOptionPane.showConfirmDialog(popupMenu, "Are you sure you want to remove background from "+toRemoveParent.getIconString()+" component?","Delete Component?", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                {
                    toRemoveParent.removeImage();
                    toRemoveParent.updateSizeLocation();
                }               
            }
            };
            break;
        case DELETE_COMP:
            listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PictureComponentInterface toDelete=(PictureComponentInterface) popupMenu.getInvoker();                
                if (JOptionPane.showConfirmDialog(popupMenu, "Are you sure you want to delete "+toDelete.getIconString()+" component?","Delete Component?", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    {                    
                    toDelete.getParentPane().getPictureComponents().remove(toDelete);
                    toDelete.Delete();
                    }
            }
            };            
        }
        return listener;
    }
    
    
    
    
    
    @Override
    public MenuListener buildMenuListener()
    {        
        MenuListener listener=new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                setMenuItems();                  
            }

            @Override
            public void menuDeselected(MenuEvent e) { 
                
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
            
        };
        return listener;
    }        

        
    private void setMenuItems()            
    {           
        boolean adminEnabled=pictureFrame.isAdminEnabled();       
        if (menuMap!=null)
            for (EntryInterface<MenuEnumsV1, MenuFlexible> mapEntry : menuMap.entrySet()) {    
                 boolean visibleMenu=false;
                switch (mapEntry.getKey())
                {
                case MANAGE_LAYOUTS:
                    visibleMenu=adminEnabled;
                    break;
                case CREATE_SUBCOMP: 
                    visibleMenu=(adminEnabled)?popupMenu.getInvoker() instanceof PicturePaneInterface:adminEnabled;               
                     break;
                case TOOLS: visibleMenu=true;               
                    break;
                }
                mapEntry.getValue().setVisible(visibleMenu);    
                System.out.println("mapentry"+mapEntry.getKey()+" visible:"+visibleMenu);
            }
        
        if (menuItemMap!=null)
        for (EntryInterface<MenuItemEnumsV1, MenuItemFlexible> mapItemEntry : menuItemMap.entrySet()) {
            int pos=0;       
            boolean visibleMenuItem=true;
            System.out.println("mapentry:"+mapItemEntry.getValue());
            Object parent=popupMenu.getInvoker();
            PicturePaneInterface parentPane=null;            
            PictureFrameInterface parentFrame=null;
            if (popupMenu.getInvoker() instanceof PicturePaneInterface)
                parentPane=(PicturePaneInterface) parent;            
            if (popupMenu.getInvoker() instanceof PictureFrameInterface)
                parentFrame=(PictureFrameInterface) parent;            
            switch (mapItemEntry.getKey())
                {case ADMIN:
                    visibleMenuItem=!pictureFrame.isUnderConst();                
                    pos=(adminEnabled)?0:1;  
                    break;
                case ALLHIDE: pos=(pictureFrame.isFullState())?0:1;            
                break;                
                case ADD_PANE:
                case LOAD_DELETE:
                case NEW_LAYOUT:
                case SAVE:visibleMenuItem=adminEnabled;
                break;
               case ADD_PICT: visibleMenuItem=adminEnabled&&parentFrame==null;                              
                 break;
                  case ADD_BACKGROUND: visibleMenuItem=adminEnabled&&parentPane!=null&&visibleMenuItem&&parentPane.getImage()==null;
                  break;
                  case REMOVE_BACKGROUND: visibleMenuItem=adminEnabled&&parentPane!=null&&parentPane.getImage()!=null;
                  break;
                  case DELETE_COMP: visibleMenuItem=adminEnabled&&parent instanceof PictureComponentInterface&&parentFrame==null;
                  break;
                }            
            mapItemEntry.getValue().chooseText(pos);                                        
            mapItemEntry.getValue().setVisible(visibleMenuItem);            
            
       }
    }
    
    
    
    
    public void buildPopupListener()
    {
        popupMenu.addPopupMenuListener(new PopupMenuListener() {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                //setMenuItems();                  
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {                
                CompListenerBuildSingVer1.getInstance().setMouseReleased(true);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {           
            }
        });
    }
    
    
}
