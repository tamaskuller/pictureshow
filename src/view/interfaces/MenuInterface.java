/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import javax.swing.JPopupMenu;
import view.Menu.JPopupMenuAdj;

/**
 *
 * @author Tamas Kuller
 */
public interface MenuInterface {
      public JPopupMenuAdj getPopupMenu();
    public void setPopupMenu(JPopupMenuAdj popupMenu);      
}
