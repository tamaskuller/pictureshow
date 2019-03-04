/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import java.util.List;
import view.util.Observer;

/**
 *
 * @author Kuller Tamas
 */
public interface PicturePaneInterface extends PictureComponentInterface,MultiComponentInt, MultiComponentGetterInt,AutoShapeCompGettersInt, Observer{
        //AutoShapeComponentInt,AutoShapeCompGettersInt,AutoShapeCompResizeableInt,AdministratableInt,ActiveComponentInt, MultiComponentInt, AttachedGettersInt<Object>, ImageHandlerInterface, MenuInterface {    
     public void addButton(PictureComponentInterface component, int order);
      public void addPictComponent(PictureComponentInterface component, int order);
    public void addPictPane(PicturePaneInterface pictPane, int order);
      public List<AttachedGettersInt> getPictureComponents();      
     @Override
       public String getIconString();
       
       
     
}
