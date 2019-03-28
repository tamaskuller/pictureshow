/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Point;
import view.interfaces.AutoShape.AutoShapedCompInt;
import view.interfaces.AutoShape.AutoShapeCompInt;

/**
 *
 * @author Tamas Kuller
 */
public class AutoShapeComponent implements AutoShapeCompInt{    
    protected AutoShapedCompInt parent;    
    protected AutoShapedCompInt mainComp;    
        
    protected int borderSecureDist;
    
    protected Dimension currBaseSize;    
    protected Point currBaseLocation;    

    protected double minWidth;     
    protected double minHeight;
    protected double minWidthMultiplier;
    protected double minHeightMultiplier;
    
    protected double motionRatio=0;    

    public AutoShapeComponent(AutoShapedCompInt parent, AutoShapedCompInt mainComp,int borderSecureDist, Dimension currBaseSize, Point currBaseLocation, double minWidthMultiplier, double minHeightMultiplier) {
        this.parent = parent;        
        this.mainComp=mainComp;
        this.borderSecureDist = borderSecureDist;
        this.currBaseSize = currBaseSize;
        this.currBaseLocation = currBaseLocation;
        this.minWidthMultiplier = minWidthMultiplier;
        this.minHeightMultiplier = minHeightMultiplier;
    }   

    @Override
    public void firstShowActions() {
            setMinDimensions();                 
            double actualWidth=(minWidth>currBaseSize.getWidth()?minWidth:currBaseSize.getWidth());
            double actualHeight=(minHeight>currBaseSize.getHeight()?minHeight:currBaseSize.getHeight());        
            currBaseSize.setSize(actualWidth,actualHeight);        
    }
  
    @Override
    public Dimension getAdjCurrSize(boolean calcWithMotion)
    {                 
            Dimension adjSize=new Dimension();              
            setMinDimensions();
           adjSize.setSize(getSizeRatioWidth()*currBaseSize.getWidth(),getSizeRatioHeight()*currBaseSize.getHeight());
            adjSize=getMaxSize(adjSize,calcWithMotion);                                                                        
           adjSize.setSize(adjSize.getWidth()*(calcWithMotion?motionRatio:1), adjSize.getHeight()*(calcWithMotion?motionRatio:1));                                                        
           return adjSize;
    }

    
    @Override
    public void setMinDimensions() {
        if (parent!=null)
            {
            minWidth=parent.getAdjCurrSize(false).getWidth()*minWidthMultiplier;
            minHeight=parent.getAdjCurrSize(false).getHeight()*minHeightMultiplier;
            }
   }
    
    public Dimension getMaxSize(Dimension s,  boolean calcWithMotion)
    {
            Dimension parentPaneSize=parent.getAdjCurrSize(calcWithMotion);
            double maxWidth=(parentPaneSize.getWidth())-getAdjCurrLocation().getX()-borderSecureDist;            
            double maxHeight=(parentPaneSize.getHeight())-getAdjCurrLocation().getY()-borderSecureDist;//-getY();
            double newWidth=(s.getWidth()<=maxWidth)? s.getWidth():maxWidth;
            double newHeight=(s.getHeight()<=maxHeight)? s.getHeight():maxHeight;
            Dimension adjSize=new Dimension();
            adjSize.setSize(newWidth,newHeight);                                            
            return adjSize;
    }
   
    @Override
    public Point getAdjCurrLocation()
    {            
            //updateParentSizeRatios();
            Point returnPoint=new Point();                        
            double adjX=(currBaseLocation.getX()<=borderSecureDist? borderSecureDist : currBaseLocation.getX())*getLocRatioWidth();
            double adjY=(currBaseLocation.getY()<=borderSecureDist? borderSecureDist : currBaseLocation.getY())*getLocRatioHeight();
            returnPoint.setLocation(adjX, adjY);           
            //returnPoint=getMaxLocation(returnPoint);
            returnPoint=getMinMaxLocation(returnPoint);
            return returnPoint;            
    }
    
    public Point getMinMaxLocation(Point location)
    {
            Point returnPoint=new Point();                        
            double parentWidth=parent.getAdjCurrSize(false).getWidth();
            double parentHeight=parent.getAdjCurrSize(false).getHeight();            
            double adjX=location.getX()<=(parentWidth-minWidth-borderSecureDist)?location.getX():parentWidth-minWidth-borderSecureDist;
            double adjY=location.getY()<=(parentHeight-minHeight-borderSecureDist)?location.getY():parentHeight-minHeight-borderSecureDist;            
            //returnPoint.setLocation((adjX<=1)?1:adjX,(adjY<=1)?1:adjY);
            returnPoint.setLocation(adjX,adjY);
            return returnPoint;        
    }        
    
    public double getSizeRatioWidth() {
        return parent.getSizeRatioWidth();        
    }

    public double getSizeRatioHeight() {
        return parent.getSizeRatioHeight();
    }
        
     public double getLocRatioWidth() {
        return parent.getSizeRatioWidth();
    }

    public double getLocRatioHeight() {
        return parent.getSizeRatioHeight();
    }        
    
    @Override
    public void updateSizeLocation() {
        this.mainComp.updateSizeLocation();
    }
        
    @Override
    public void setMotionRatio(double motionRatio) {
        this.motionRatio=motionRatio;
    }

    
    @Override
    public void setMinWidthMultiplier(double minWidthMultiplier) {
        this.minWidthMultiplier = minWidthMultiplier;
    }

    @Override
    public void setMinHeightMultiplier(double minHeightMultiplier) {
        this.minHeightMultiplier = minHeightMultiplier;
    }

    @Override
    public double getMinWidth() {
        return minWidth;
    }

    @Override
    public double getMinHeight() {
        return minHeight;
    }

    @Override
    public Dimension getCurrBaseSize() {
        return currBaseSize;
    }

    @Override
    public Point getCurrBaseLocation() {
        return currBaseLocation;
    }
    
    
}
