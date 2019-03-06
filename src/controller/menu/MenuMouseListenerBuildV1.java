/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;

/**
 *
 * @author Tamas Kuller
 */
public class MenuMouseListenerBuildV1 {
    static MenuMouseListenerBuildV1 instance;
    
    private MenuMouseListenerBuildV1() {            
    }
    
    public static MenuMouseListenerBuildV1 getInstance()
    {
        if (instance==null)
            instance=new MenuMouseListenerBuildV1();
        return instance;
    }
     
    public MouseListener buildMouseListener(JPopupMenu menu)
    {
        //System.out.println("buildmenulistener"+menu.toString());
    MouseListener listener=new MouseListener() {
    
    @Override
    public void mouseReleased(MouseEvent e) {
        //super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
        if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());                                        
                }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //super.mousePressed(e); //To change body of generated methods, choose Tools | Templates.
        if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    };
    return listener;
    }
}
