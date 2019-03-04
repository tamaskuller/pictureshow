/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
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
        
    }
       
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.                        
        System.out.println(grphcs.getColor());   
        //currentButtonColor=(grphcs.getColor()==BUTTON_COLOR)?BUTTON_COLOR2:BUTTON_COLOR;
        grphcs.setColor(currentButtonColor);
        grphcs.fillOval(0, 0, getWidth()-1, getHeight()-1);                
    }

    @Override
    public void mouseEnterred() {
        setBorder(BorderFactory.createEmptyBorder());
        currentButtonColor=BUTTON_COLOR_ACTIVE;
        super.mouseEnterred(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited() {
        setBorder(null);
        currentButtonColor=BUTTON_COLOR;
        super.mouseExited(); //To change body of generated methods, choose Tools | Templates.        
        
    }

   
    
    
    
}
