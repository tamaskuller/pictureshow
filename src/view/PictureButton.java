/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.BorderFactory;
import view.enums.MotionTypes;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.PictCompParams;
import view.interfaces.AutoShape.AutoShapeCompResInt;

/**
 *
 * @author Kuller Tamas
 */
public class PictureButton extends PictureComponent {    
    private final static Color BUTTON_BORDER_COLOR=Color.WHITE;    
    private final static Color BUTTON_COLOR=Color.RED;
    private final static Color BUTTON_COLOR_ACTIVE=Color.MAGENTA;
    private final static int BUTTON_BORDER_WIDTH=2;
    private Color currentButtonColor=BUTTON_COLOR;        
    
    public PictureButton(PicturePaneInterface parentPane,PictCompParams params ) {        
        super(parentPane,params);
        autoShapeComponentRes=new AutoShapeCompRes(parentPane, this, BORDER_SECURE_DIST, currBaseSize, currBaseLocation, 0.05, 0.05)
        {
            @Override
            public void setMinDimensions() {
                super.setMinDimensions(); //To change body of generated methods, choose Tools | Templates.
                 double min=(minHeight>minWidth)?minWidth:minHeight;
                 minWidth=min;
                 minHeight=min;                
            }            
        };
        normalBorder=BorderFactory.createEmptyBorder();
        currentBorder=normalBorder;
        bckColor=null;
        autoShapeComponentRes.setMinDimensions();
        
    }
       
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.                                
        grphcs.setColor(BUTTON_BORDER_COLOR);
        grphcs.fillOval(0, 0, getWidth()-1, getHeight()-1);                                    
        grphcs.setColor(currentButtonColor);
        grphcs.fillOval(BUTTON_BORDER_WIDTH, BUTTON_BORDER_WIDTH, getWidth()-(BUTTON_BORDER_WIDTH*2)-1, getHeight()-(BUTTON_BORDER_WIDTH*2)-1);                
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
    
    
//    @Override
//    public synchronized void adjCurrBaseSize(double addWidth, double addHeight, boolean adjustLoc) {
//        double addSize=(addWidth!=0)?addWidth:addHeight;
//        super.adjCurrBaseSize(addSize, addSize, adjustLoc); //To change body of generated methods, choose Tools | Templates.
//    }



    @Override
    public double getSizeRatioHeight() {
        return super.getSizeRatioHeight()/parentPane.getSizeRatioHeight(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getSizeRatioWidth() {
        return super.getSizeRatioWidth()/parentPane.getSizeRatioWidth(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
