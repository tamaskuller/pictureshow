/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Tamas Kuller
 */
public final class FontFunctions {    
    private final static double X_INDENT_RATIO=0.1;
    private final static double Y_INDENT_RATIO=0.1;
    
    public static int getActFontSize(Dimension coverBox,Graphics g, int defFontSize, String text) {        
            int tempFontSize=defFontSize;                   
            boolean tooBigFont=true;
            do        
            {
                Font font=new Font(g.getFont().getFontName(),g.getFont().getStyle(),tempFontSize);
                FontMetrics fontMetrics=new FontMetrics(font) {};            
                int fontHeight=(int) (fontMetrics.getStringBounds(text, g).getHeight());
                int fontWidth=(int) (fontMetrics.getStringBounds(text, g).getWidth());            
                if (((coverBox.getHeight()*(1-Y_INDENT_RATIO)<fontHeight)||coverBox.getWidth()*(1-X_INDENT_RATIO)<fontWidth)&&tempFontSize>1)                
                        tempFontSize--;                                    
                else
                    {tooBigFont=false;
                    }
            }
            while (tooBigFont);
            //System.out.println("ResultFontSize:"+tempFontSize);        
            return tempFontSize;
            
    }

    
    
    
    public static Dimension drawHighlightedString(Dimension coverBox,Graphics grphcs, Color fontColor,Color backgroundColor, String text) {
        if (coverBox!=null)
            {            
            grphcs.setColor(backgroundColor);                      
            Rectangle2D rect=(new FontMetrics(grphcs.getFont()) {}).getStringBounds(text, grphcs);                            
            int x=(int) (coverBox.getWidth()*X_INDENT_RATIO);
            int y=(int) (coverBox.getHeight()*(1-Y_INDENT_RATIO));
            grphcs.fillRect(x,y-(new FontMetrics(grphcs.getFont()) {}).getAscent(), (int)rect.getWidth(),(int)rect.getHeight());               
            grphcs.setColor(fontColor);                      
            grphcs.drawString(text, x, y);                                        
            return new Dimension((int)rect.getWidth(),(int)rect.getHeight());
            }
        return null;
    }

}
