/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import java.awt.Color;
import view.recordtypeclasses.PaintRequestParams;
import view.util.Observer;

/**
 *
 * @author Kuller Tamas
 */
public interface PictureComponentInterface extends AutoShapeComponentInt,AutoShapeCompResizeableInt,ActiveComponentInt, AdministratableInt, AttachedGettersInt<Object>, ImageInt<NamedImageInt>,MenuInterface, Comparable<PictureComponentInterface> {
     public void paintPict(PaintRequestParams paintRequest);  
     public PicturePaneInterface getParentPane();
     //public Color getResizeBorderColor();   
     public void Delete();
     public String getIconString();
     public void setVisible();
     public void setInVisible();
     public void setImagePath(String imagePath);
     public void removeImage();
     public boolean isUnderConst();
     public boolean isMinimzed();
     
     
     //public void activateComponent();
    //public void deActivateComponent();
}
