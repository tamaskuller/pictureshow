/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.listeners;

import javax.swing.JPopupMenu;
import view.adapter.AdaptPictJComponent;
import view.interfaces.PictureComponentInterface;

/**
 *
 * @author Tamas Kuller
 */
public abstract class ListenerBuildAbs<Z>{
    public abstract Z addMouseActivity(PictureComponentInterface component, JPopupMenu menu);
    public abstract Z addMouseMotionActivity(AdaptPictJComponent component);
    public abstract void removeActivity(AdaptPictJComponent component,Z activity);    
}
