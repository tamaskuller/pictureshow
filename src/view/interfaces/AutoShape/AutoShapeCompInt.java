/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces.AutoShape;

import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Tamas Kuller
 */
public interface AutoShapeCompInt extends AutoShapeCompUpdateInt, AutoShapeCompGettersInt{
   
    public void firstShowActions(); 
    public void setMinDimensions();
    public void setMinWidthMultiplier(double minWidthMultiplier);
    public void setMinHeightMultiplier(double minHeightMultiplier);                    
      
    public void setMotionRatio(double motionRatio);
    
    
    
    
    
    
}
