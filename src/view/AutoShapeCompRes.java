/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Point;
import view.interfaces.AutoShape.AutoShapedCompInt;
import view.interfaces.AutoShape.AutoShapeCompResInt;

/**
 *
 * @author Tamas Kuller
 */
public class AutoShapeCompRes extends AutoShapeComponent implements AutoShapeCompResInt {
    
    protected double sizeRatioWidth=1;
    protected double sizeRatioHeight=1;  
    protected double locRatioWidth=1;
    protected double locRatioHeight=1;  

    public AutoShapeCompRes(AutoShapedCompInt parent,AutoShapedCompInt mainComp,  int borderSecureDist, Dimension currBaseSize, Point currBaseLocation, double minWidthMultiplier, double minHeightMultiplier) {
        super(parent, mainComp,borderSecureDist, currBaseSize, currBaseLocation, minWidthMultiplier, minHeightMultiplier);                
    }
        
    
    @Override
    public void firstShowActions()
    {        
        super.firstShowActions();
        sizeRatioWidth=super.getSizeRatioWidth();
        sizeRatioHeight=super.getSizeRatioHeight();
    }    

    @Override
    public synchronized void adjCurrBaseLocation(double addX, double addY) {
        //setRatiosParent();
        Point origLocation=getAdjCurrLocation();
        Point adjLocation=new Point();                            
        //Dimension parentPaneSize=parentPane.getAdjCurrSize(true, true,false);
        double adjX=origLocation.getX()+(addX);
        double adjY=origLocation.getY()+(addY);
        adjX=adjX>=borderSecureDist?adjX:borderSecureDist;
        adjY=adjY>=borderSecureDist?adjY:borderSecureDist;        
        adjLocation.setLocation(adjX,adjY);
        adjLocation=getMinMaxLocation(adjLocation);
        locRatioWidth=(adjLocation.getX()/origLocation.getX())*locRatioWidth;
        locRatioHeight=(adjLocation.getY()/origLocation.getY())*locRatioHeight;                    
        updateSizeLocation();
    }
       

    @Override
    public synchronized void setCurrBaseSizeLocToCurrSizeLoc(boolean adminEnabled) {   
        //updateParentSizeRatios();                    
        Dimension adjSize=(adminEnabled)?getAdjCurrSize(false):getAdjCurrSize(false);                               
        System.out.println("parentsizeratioheight:"+parent.getSizeRatioHeight());
       System.out.println("adjsize:"+adjSize);
        sizeRatioHeight=sizeRatioWidth=1;        
        adjSize.setSize(adjSize.getWidth()/(adminEnabled?getSizeRatioWidth():1)/1,adjSize.getHeight()/(adminEnabled?getSizeRatioHeight():1)/1);       
        Point adjLocation=(adminEnabled)?getAdjCurrLocation():getAdjCurrLocation();
        locRatioWidth=locRatioHeight=1;
        adjLocation.setLocation(adjLocation.getX()/(adminEnabled?getLocRatioWidth():1), adjLocation.getY()/(adminEnabled?getLocRatioHeight():1));
        System.out.println("currbaseSizeprev:"+currBaseSize);
        currBaseLocation=adjLocation;
        currBaseSize=adjSize;                
        System.out.println("currbaseSize:"+currBaseSize);
    }
    
    @Override
    public synchronized void adjCurrBaseSize(double addWidth, double addHeight, boolean AdjustLoc) {                 
            Dimension adjDim=new Dimension();                
            Dimension origDim=new Dimension(); 
            origDim=getAdjCurrSize(false);
            adjDim.setSize(origDim.getWidth()+addWidth, origDim.getHeight()+addHeight);                    
            if (AdjustLoc)
                adjCurrBaseLocation(-addWidth,-addHeight);                                                    
//            if (adjustLoc)    
//                    adjCurrBaseLocation((origDim.getWidth()-adjDim.getWidth()),origDim.getHeight()-adjDim.getHeight());                                        
            adjDim=getMaxSize(adjDim,false);                            
            adjDim=getMinSize(adjDim);        
            sizeRatioWidth=(adjDim.getWidth()/origDim.getWidth())*sizeRatioWidth;
            sizeRatioHeight=(adjDim.getHeight()/origDim.getHeight())*sizeRatioHeight;            
            System.out.println("NewRatioWidth:"+sizeRatioWidth+" origwidth:"+origDim.getWidth()+" newWidth:"+adjDim.getWidth());                        
            updateSizeLocation();
    }
    
    @Override
     public Dimension getMinSize(Dimension size)
    {
        double newWidth=size.getWidth();
        double newHeight=size.getHeight();
        newHeight=(newHeight<minHeight?minHeight:newHeight);
        newWidth=(newWidth<minWidth?minWidth:newWidth);        
        Dimension adjSize=new Dimension();
        adjSize.setSize(newWidth,newHeight);
        return adjSize;
        
    }
    
   @Override
    public double getSizeRatioWidth() {
        return super.getSizeRatioWidth()*sizeRatioWidth;
    }

    @Override
    public double getSizeRatioHeight() {
        return super.getSizeRatioHeight()*sizeRatioHeight;
    }

    
    @Override
     public double getLocRatioWidth() {
        return super.getLocRatioWidth()*locRatioWidth;
    }

    @Override
    public double getLocRatioHeight() {
        return super.getLocRatioHeight()*locRatioHeight;
    } 
        
}
