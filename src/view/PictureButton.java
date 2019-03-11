/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import view.enums.MotionTypes;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.PictCompParams;

/**
 *
 * @author Kuller Tamas
 */
public class PictureButton extends PictureComponent {    
    private final static Color BUTTON_COLOR=Color.RED;
    private final static Color BUTTON_COLOR_ACTIVE=Color.MAGENTA;
    private Color currentButtonColor=BUTTON_COLOR;    
    
    public PictureButton(PicturePaneInterface parentPane,PictCompParams params ) {        
        super(parentPane,params);        
        minWidthMultiplier=0.05;
        minHeightMultiplier=0.05;  
        normalBorder=BorderFactory.createEmptyBorder();
        currentBorder=normalBorder;
        
    }
       
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.                                
        //currentButtonColor=(grphcs.getColor()==BUTTON_COLOR)?BUTTON_COLOR2:BUTTON_COLOR;
        grphcs.setColor(currentButtonColor);
        grphcs.fillOval(0, 0, getWidth()-1, getHeight()-1);                
    }

    
    
    @Override
    public void mouseEnterred() {
        currentButtonColor=BUTTON_COLOR_ACTIVE;
        setBorder(BorderFactory.createEmptyBorder());        
        super.mouseEnterred(); //To change body of generated methods, choose Tools | Templates.
        repaint();
    }

    @Override
    public void mouseExited() {
        currentButtonColor=BUTTON_COLOR;
        setBorder(BorderFactory.createEmptyBorder());        
        super.mouseExited(); //To change body of generated methods, choose Tools | Templates.        
        repaint();
    }
    
    @Override
    protected void setMinDimensions(boolean checkMin, boolean adjLocation) {
        super.setMinDimensions(checkMin, adjLocation);
        double min=(minHeight>minWidth)?minWidth:minHeight;
        minWidth=min;
        minHeight=min;
    }

    @Override
    public synchronized void adjCurrBaseSize(double addWidth, double addHeight, boolean adjustLoc) {
        double addSize=(addWidth!=0)?addWidth:addHeight;
        super.adjCurrBaseSize(addSize, addSize, adjustLoc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getLocRatioHeight() {
        return super.getLocRatioHeight(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getLocRatioWidth() {   
        return getMoveFactor()*super.getLocRatioWidth(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void setCurrBaseSizeLocToCurrSizeLoc(boolean adminEnabled) {                
        locRatioWidth=locRatioWidth/getMoveFactor();
        super.setCurrBaseSizeLocToCurrSizeLoc(adminEnabled); //To change body of generated methods, choose Tools | Templates.                
    }

    private double getMoveFactor()
    {
        return 1;
       //return parentPane.getAdjCurrSize(true, true, true).getWidth()/parentPane.getAdjCurrSize(true, true, false).getWidth();
    }
   
    
    
    
}
