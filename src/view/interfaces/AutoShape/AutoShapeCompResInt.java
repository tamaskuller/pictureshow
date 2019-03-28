/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces.AutoShape;

import java.awt.Dimension;

/**
 *
 * @author Tamas Kuller
 */
public interface AutoShapeCompResInt extends AutoShapeCompInt, AutoShapeCompResUpdateInt {
    public void adjCurrBaseSize(double addWidth, double addHeight,boolean adjustLoc ); 
    public void adjCurrBaseLocation(double addX, double addY);  
   
    
    
    
    
}
