/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author Tamas Kuller
 */
public interface PictureFrameGettersInt extends MultiComponentGetterInt, AdministratableGetterInt{
    public String getTitle();    
    public Color getOldBackGroundColor();
    public double getSizeRatioContPaneWidth();    
    public double getSizeRatioContPaneHeight();
    public boolean isFullState();        
    public Dimension getFrameSize();
    public NamedImageInt getImage();  
    public String getImagePath();        
    
}
