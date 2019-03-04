/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Point;
import view.enums.MotionTypes;
import view.interfaces.PictureComponentGettersInt;
import view.interfaces.AutoShapeCompResGettersInt;
import view.interfaces.AutoShapeCompGettersInt;
import util.mapping.MapInterface;
import view.interfaces.NamedImageInt;
import view.recordtypeclasses.AnimParams;

/**
 *
 * @author Tamas Kuller
 */
public class PictureComponentGet implements PictureComponentGettersInt, AutoShapeCompGettersInt, AutoShapeCompResGettersInt{
    PictureComponent pictureComponent;

    public PictureComponentGet(PictureComponent pictureComponent) {
        this.pictureComponent = pictureComponent;
    }        
   
         
    @Override
    public double getMinWidth() {
        return pictureComponent.minWidth;
    }

    @Override
    public double getMinHeight() {
        return pictureComponent.minHeight;
    }
    
@Override
    public Dimension getOrigSize() {
        return pictureComponent.origSize;
    }

    
    @Override
    public Point getOrigLocation() {
        return pictureComponent.origLocation;
    }

        @Override
    public String getIconString() {
        return pictureComponent.iconString;
    }

    @Override
    public String getToolTipText() {
        return pictureComponent.toolTipText;
    }
   

    
    @Override
    public MotionTypes getDefaultMotionType() {
        return pictureComponent.defaultMotionType;
    }

    @Override
    public MapInterface<MotionTypes,AnimParams> getMotionTypeMaps() {
        return pictureComponent.motionTypeMaps;
    }

    
    @Override
    public double getMotionRatio() {
        return pictureComponent.motionRatio;
    }


    @Override
    public boolean isShown() {
        return pictureComponent.shown;
    }

    @Override
    public Dimension getCurrBaseSize() {
        return pictureComponent.currBaseSize;
    }


    @Override
    public Point getCurrBaseLocation() {
        return  pictureComponent.currBaseLocation;
    }

//    @Override
//    public double getSizeParentRatioWidth() {
//     return  pictureComponent.sizeParentRatioWidth;
//    }
//
//    @Override
//    public double getSizeParentRatioHeight() {
//        return  pictureComponent.sizeParentRatioHeight;
//    }            

    @Override
    public double getSizeRatioWidth() {
        return  pictureComponent.sizeRatioWidth;
    }

    @Override
    public double getSizeRatioHeight() {
        return  pictureComponent.sizeRatioHeight;
    }

    @Override
    public Dimension getSize() {
        return pictureComponent.getSize();
    }

    @Override
    public boolean isAdminEnabled() {
        return pictureComponent.adminEnabled;
    }

    @Override
    public NamedImageInt getImage() {
        return pictureComponent.image;
        //return null;
    }

    @Override
    public String getImagePath() {
        return pictureComponent.imagePath;
        //return null;
    }

    @Override
    public int getOrder() {
        return pictureComponent.parentPane.getComponentOrder(pictureComponent);
    }

    

   
    


}
