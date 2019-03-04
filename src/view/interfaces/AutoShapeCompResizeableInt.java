/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

/**
 *
 * @author Tamas Kuller
 */
public interface AutoShapeCompResizeableInt {
    public void adjCurrBaseSize(double addWidth, double addHeight, boolean adjustLoc); 
    public boolean isResizeable();
public void setCurrBaseSizeLocToCurrSizeLoc(boolean adminEnabled);  
    public void adjCurrBaseLocation(double addX, double addY);  
    
    
    
    
}
