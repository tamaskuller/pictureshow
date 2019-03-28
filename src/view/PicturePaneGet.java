/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import view.enums.MotionTypes;
import view.interfaces.PictureComponentInterface;
import view.interfaces.PicturePaneGettersInt;

/**
 *
 * @author Tamas Kuller
 */
public class PicturePaneGet extends PictureComponentGet implements PicturePaneGettersInt{
    PicturePane picturePane;

    public PicturePaneGet(PicturePane picturePane, PictureComponent pictureComponent) {
        super(pictureComponent);
        this.picturePane = picturePane;
    }

    
    @Override
    public MotionTypes getReOrderMotionType() {
        return picturePane.reOrderMotionType;
    }



    @Override
    public boolean isFullState() {
        return picturePane.isFullState();
    }


    @Override
    public List<PictureComponentInterface> getPictureComponents() {
        return picturePane.pictureComponents;
    }

    @Override
    public int getComponentOrder(Object component) {
        return picturePane.getComponentOrder(component);
    }

   


    
}
