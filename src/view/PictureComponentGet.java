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
import util.mapping.MapInterface;
import view.interfaces.NamedImageInt;
import view.recordtypeclasses.AnimParams;

/**
 *
 * @author Tamas Kuller
 */
public class PictureComponentGet implements PictureComponentGettersInt{
    PictureComponent pictureComponent;

    public PictureComponentGet(PictureComponent pictureComponent) {
        this.pictureComponent = pictureComponent;
    }        
   
         
    @Override
    public double getMinWidth() {
        return pictureComponent.autoShapeComponentRes.getMinWidth();
    }

    @Override
    public double getMinHeight() {
        return pictureComponent.autoShapeComponentRes.getMinHeight();
    }

    @Override
    public Dimension getMinSize(Dimension size) {
        return pictureComponent.autoShapeComponentRes.getMinSize(size);    
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
    public boolean isShown() {
        return pictureComponent.shown;
    }

    
    
    @Override
    public Dimension getCurrBaseSize() {
        return pictureComponent.autoShapeComponentRes.getCurrBaseSize();
    }


    @Override
    public Point getCurrBaseLocation() {
        return  pictureComponent.autoShapeComponentRes.getCurrBaseLocation();
    }
    
    @Override
    public double getSizeRatioWidth() {
        return  pictureComponent.getSizeRatioWidth();
    }

    @Override
    public double getSizeRatioHeight() {
        return  pictureComponent.getSizeRatioHeight();
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
