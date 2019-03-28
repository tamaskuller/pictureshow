/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import view.interfaces.NamedImageInt;
import view.interfaces.PictureFrameGettersInt;

/**
 *
 * @author Tamas Kuller
 */
public class PictureFrameGet implements PictureFrameGettersInt{
    PictureFrame pictureFrame;

    public PictureFrameGet(PictureFrame pictureFrame) {
        this.pictureFrame = pictureFrame;
    }
    
       @Override
    public Color getOldBackGroundColor() {
        return pictureFrame.oldBackGroundColor;
    }

       @Override
    public double getSizeRatioContPaneWidth() {
        return pictureFrame.sizeRatioContPaneWidth;
    }

   @Override
    public double getSizeRatioContPaneHeight() {
        return pictureFrame.sizeRatioContPaneHeight;
    }


       @Override
    public String getTitle() {
        return pictureFrame.getTitle(); //To change body of generated methods, choose Tools | Templates.
    }


  
   
    @Override
     public Dimension getFrameSize() {
        return pictureFrame.frameSize;
    }

   


    @Override
    public boolean isFullState() {
        return pictureFrame.isFullState();
    }


    @Override
    public boolean isAdminEnabled() {
        return pictureFrame.isAdminEnabled();
    }

    @Override
    public NamedImageInt getImage() {
        return pictureFrame.image;
        //return null;
    }

    @Override
    public String getImagePath() {
        return pictureFrame.imagePath;
        //return null;
    }

    @Override
    public int getComponentOrder(Object component) {
        return pictureFrame.getComponentOrder(component);
    }
   
}
