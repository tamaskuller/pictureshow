/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Menu;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;

/**
 *
 * @author Tamas Kuller
 */
public class MenuFlexible extends JMenu {
    List<MenuItemFlexible> menuItemFlexibleList;

    public MenuFlexible(String s) {
        super(s);
        menuItemFlexibleList=new ArrayList<>();
    }
   
    
    public void addMenuItemFlexible(MenuItemFlexible menuItemFlexible)
    {
        if (!menuItemFlexibleList.contains(menuItemFlexible))
                {
                this.menuItemFlexibleList.add(menuItemFlexible);       
                this.add(menuItemFlexible);
                }
    }

    public MenuItemFlexible getMenuItemFlexible(String name)
    {        
        MenuItemFlexible menuItemFlexible=null;
        if (!menuItemFlexibleList.isEmpty())
        {
            int i=0;        
            boolean notFound=true;                    
            while (notFound&&i<this.menuItemFlexibleList.size())    
                {
                if (this.menuItemFlexibleList.get(i).getName().equals(name))
                    {
                    notFound=false;
                    menuItemFlexible=this.menuItemFlexibleList.get(i);                    
                    }
                i++;                                
                }            
        }
        return menuItemFlexible;
    }
    
    public boolean removeMenuItemFlexible(MenuItemFlexible menuItemFlexible)
    {
        if (menuItemFlexibleList.contains(menuItemFlexible))
        {
            this.remove(menuItemFlexible);            
            return this.menuItemFlexibleList.remove(menuItemFlexible);
        }
        return false;
    }

    
    
}
