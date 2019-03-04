/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import util.EnvironmentParams;
import view.interfaces.AutoShapeFormInt;
import view.interfaces.PictureFrameInterface;
import view.recordtypeclasses.JFrameBaseFormParams;

/**
 *
 * @author Tamas Kuller
 */
public abstract class JFrameBaseFormAbs extends JFrame implements AutoShapeFormInt {
    
    public JFrameBaseFormAbs(JFrameBaseFormParams params) {
        Point location=new Point();        
        this.setSize((int) params.width, (int) params.height);                
        location.setLocation(params.x, params.y);       
        this.setLocation(location);        
        setTitle(params.title);
    }
    
    @Override
    public void setLocToCenter(PictureFrameInterface parentFrame)
    {
        Dimension parentSize=getParentDimension(parentFrame);
        Point parentLocation=getParentLocation(parentFrame);
        int newX=(int) (parentLocation.getX()+(parentSize.getWidth()/2-super.getWidth()/2));
        int newY=(int) (parentLocation.getY()+(parentSize.getHeight()/2-super.getHeight()/2));        
        setLocation(newX,newY);         
    }

    @Override
    public void setMaxSize(PictureFrameInterface parentFrame)
    {
        Dimension parentSize=getParentDimension(parentFrame);
        Dimension currFrameSize=super.getSize();
        double newWidth=(parentSize.getWidth()>=currFrameSize.getWidth()?currFrameSize.getWidth():parentSize.getWidth());
        double newHeight=(parentSize.getHeight()>=currFrameSize.getHeight()?currFrameSize.getHeight():parentSize.getHeight());
        currFrameSize.setSize(newWidth, newHeight);
        System.out.println("currentFrameSize::"+currFrameSize);        
        super.setSize(currFrameSize);                
        
    }
    
    private Dimension getParentDimension(PictureFrameInterface parentFrame)
    {
        if (parentFrame==null)                 
            return EnvironmentParams.getScreenDimension();       
        else
            return parentFrame.getSize();              
    }
    
    private Point getParentLocation(PictureFrameInterface parentFrame)
    {
        if (parentFrame==null)                 
            return new Point(0,0);
        else
            return parentFrame.getCurrBaseLocation();              
    }
}
