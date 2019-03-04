/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.listeners;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPopupMenu;
import view.adapter.AdaptPictJComponent;
import view.interfaces.PictureComponentInterface;

/**
 *
 * @author Tamas Kuller
 */
public final class CompListenerBuildSingVer1 extends ListenerBuildAbs {

    private static CompListenerBuildSingVer1 instance;
    private Point mouseOrigPosition;   
    private final int MOVE_SENSITIVITY=5;
    boolean move=true;     
    boolean mouseReleased=true;     

    private CompListenerBuildSingVer1() {
    }
    
    public static CompListenerBuildSingVer1 getInstance()
    {
        if (instance==null)
            instance=new CompListenerBuildSingVer1();
        return instance;
    }    

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public void setMouseReleased(boolean mouseReleased) {
        this.mouseReleased = mouseReleased;
    }
    
    
    
    @Override
    public MouseListener addMouseActivity(PictureComponentInterface component, JPopupMenu menu) {
        MouseListener mouseAdapter=new java.awt.event.MouseListener() {                        
            
            
            @Override
            public void mouseReleased(MouseEvent e) {                
                if (e.isPopupTrigger()) 
                   menu.show(e.getComponent(), e.getX(), e.getY());                                                        
                else             
                    mouseReleased=true;
            }
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {                                                                                                                                                   
                component.onClick();                 
                //notifyObserver(action,method,copy , observerList);                                   
                System.out.println("CLICKED");
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) 
                    menu.show(e.getComponent(), e.getX(), e.getY());                                                        
                else
                {                                
                    mouseReleased=false;
                    mouseOrigPosition=e.getLocationOnScreen();                   
                    if (!move&&!component.isMinimzed())
                    {
                        System.out.println("adjcurr");                     
//                        component.setCurrBaseSizeLocToCurrSizeLoc(component.isAdminEnabled());
                    }               
                }
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));                
            if (mouseReleased)
                component.mouseExited();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            if (mouseReleased)
                component.mouseEnterred();
            }                           
            
            
            };
        
        
        return mouseAdapter;
        
    }
    
    @Override
    public MouseMotionListener addMouseMotionActivity(AdaptPictJComponent component) {
        
        
        MouseMotionListener mouseMotionListener=new MouseMotionListener() {
            
            
            @Override
            public void mouseDragged(MouseEvent e) {                    
                if (component.isAdminEnabled())
                    {
                    double moveX=e.getLocationOnScreen().getX()-mouseOrigPosition.getX();
                    double moveY=e.getLocationOnScreen().getY()-mouseOrigPosition.getY();                    
                    double addX=0;
                    double addY=0;                    
                    double addWidth=0;
                    double addHeight=0;     
                    boolean adjustLoc=false;
                    if (move)                        
                        {
                            //addX=moveX;
                            addY=moveY;                                                
                            addX=moveX;                                
                            component.adjCurrBaseLocation(addX, addY);                            
                        }
                    else if (component.isResizeable())
                        {
                           // component.setOrigSize(component.getSize());
                            System.out.println("Resize!");                        
                        switch (component.getCursor().getType())
                            {                                
                                case Cursor.W_RESIZE_CURSOR:                                                                    
                                    //addX=moveX;                                    
                                    adjustLoc=true;    
                                    if (e.getLocationOnScreen().getX()<component.getParent().getLocationOnScreen().getX())
                                        moveX=0;
                                    else
                                        moveX=-moveX;
//                                      moveX=((moveX>0||component.getParent().getLocationOnScreen().getX()<component.getLocationOnScreen().getX())?-moveX:0);                                    
                                case Cursor.E_RESIZE_CURSOR:                                                                                                               
                                    addWidth=moveX;                      
                                    break;
                                case Cursor.N_RESIZE_CURSOR:                                        
                                    //addY=moveY;                                    
                                    adjustLoc=true;
                                    if (e.getLocationOnScreen().getY()<component.getParent().getLocationOnScreen().getY())
                                        moveY=0;
                                    else
                                        moveY=-moveY;                                   
                                    //moveY=((moveY>0||component.getParent().getLocationOnScreen().getY()<component.getLocationOnScreen().getY())?-moveY:0);                                                                            
                                case Cursor.S_RESIZE_CURSOR:
                                    addHeight=moveY;
                                    break;                                       
                            }                        
                        component.adjCurrBaseSize(addWidth, addHeight,adjustLoc);                        
                        }
                    mouseOrigPosition=e.getLocationOnScreen();
                    
                    }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                component.mouseEnterred();
                int cursor=Cursor.DEFAULT_CURSOR;  
                if (component.isAdminEnabled()&&component.isResizeable())
                    {
                        double west=component.getLocationOnScreen().getX();                        
                        double east=west+component.getWidth();
                        double north=component.getLocationOnScreen().getY();
                        double south=north+component.getHeight();                                    
                        double mouseX=e.getXOnScreen();
                        double mouseY=e.getYOnScreen();
                                              
                        if (mouseX>=east-MOVE_SENSITIVITY&&mouseX<=east+MOVE_SENSITIVITY)
                                cursor=Cursor.E_RESIZE_CURSOR;                                
                        if (mouseX>=west-MOVE_SENSITIVITY&&mouseX<=west+MOVE_SENSITIVITY)
                                cursor=Cursor.W_RESIZE_CURSOR;
                        if (mouseY>=north-MOVE_SENSITIVITY&&mouseY<=north+MOVE_SENSITIVITY)
                                cursor=Cursor.N_RESIZE_CURSOR;
                        if (mouseY>=south-MOVE_SENSITIVITY&&mouseY<=south+MOVE_SENSITIVITY)
                                cursor=Cursor.S_RESIZE_CURSOR;
                        component.setCursor(new Cursor(cursor));                                                                           
                    }
                else component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));                    
            move=(cursor==Cursor.DEFAULT_CURSOR)?true:false;     
            }
        };
        return mouseMotionListener;
        
        //addMouseMotionListener(mouseMotionListener);        
        
    }

    
    
    
    @Override
    public void removeActivity(AdaptPictJComponent component, Object activity) {
        for (MouseListener mouseListener : component.getMouseListeners()) {
            if (activity==mouseListener)
                {
                component.removeMouseListener(mouseListener);
                }
        }            

    }

    
    
}
