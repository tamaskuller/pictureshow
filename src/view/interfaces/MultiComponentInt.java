/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import view.enums.MotionTypes;

/**
 *
 * @author Tamas Kuller
 */
public interface MultiComponentInt {
    public void showHideComponent(Object component ,MotionTypes motionType, boolean show, boolean forced, int order);
    public void showHideComponents(boolean show, boolean forced, MotionTypes motionType);       
    public void activateComponent(Object component, MotionTypes motionType);
    public void onClick(Object component);            
    public void setCompOrder(Object component, int order);               
    public void setFullState(boolean fullState, MotionTypes motionType, boolean checkMin);   
    public void showState(boolean forced, MotionTypes motionType);    
    public void hideShowSwitch();    
    public void minimize();    
    public void maximize();    
    
    
    
}
