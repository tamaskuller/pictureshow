/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import view.interfaces.AutoShape.AutoShapeCompResGettersInt;
import view.interfaces.AutoShape.AutoShapeCompGettersInt;
import java.awt.Dimension;
import java.awt.Point;
import view.enums.MotionTypes;
import util.mapping.MapInterface;

/**
 *
 * @author Tamas Kuller
 */
public interface PictureComponentGettersInt extends AdministratableGetterInt,AutoShapeCompGettersInt, AutoShapeCompResGettersInt {

    public double getMinWidth();    
    public double getMinHeight();    
    public Dimension getOrigSize();    
    public Point getOrigLocation(); 
    public String getIconString();
    public String getToolTipText();        
    public MotionTypes getDefaultMotionType();    
    public MapInterface getMotionTypeMaps();        
    public boolean isShown();
    public NamedImageInt getImage();  
    public String getImagePath();
    public int getOrder();
    

    
}
