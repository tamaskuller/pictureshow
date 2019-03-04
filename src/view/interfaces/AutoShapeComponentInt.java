/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import java.awt.Dimension;

/**
 *
 * @author Tamas Kuller
 */
public interface AutoShapeComponentInt {
    public void updateSizeLocation();
    public Dimension getAdjCurrSize(boolean checkMin, boolean adjLocation, boolean calcWithMotion);    
    public double getSizeRatioWidth();
    public double getSizeRatioHeight();
    
    
    
    
}
