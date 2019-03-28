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
public interface AutoShapeCompUpdateInt {
    public void updateSizeLocation();    
    public Dimension getAdjCurrSize(boolean calcWithMotion);        
    public Point getAdjCurrLocation();
}
