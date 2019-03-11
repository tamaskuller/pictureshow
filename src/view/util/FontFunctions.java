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
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Tamas Kuller
 */
public final class FontFunctions {        
    private static final int DEF_FONT_SIZE=30;  
    
    public static int getActFontSize(Dimension coverBox,Graphics g, String text,int fontStyle, double x_indent, double y_indent) {        
            int tempFontSize=DEF_FONT_SIZE;                   
            boolean tooBigFont=true;
            do        
            {
                Font font=new Font(g.getFont().getFontName(),fontStyle,tempFontSize);
                FontMetrics fontMetrics=new FontMetrics(font) {};            
                Rectangle2D rect=(fontMetrics.getStringBounds(text, g));                                
                int fontHeight=(int) rect.getHeight();
                int fontWidth=(int) rect.getWidth();
                if (((coverBox.getHeight()*(1-y_indent)<fontHeight)||(coverBox.getWidth()*(1-x_indent))<fontWidth)&&tempFontSize>1)                
                        tempFontSize--;                                    
                else
                    {tooBigFont=false;
                    }
            }
            while (tooBigFont);
            //System.out.println("ResultFontSize:"+tempFontSize);        
            return tempFontSize;
            
    }
    
    public static Point drawHighlightedString(Dimension coverBox,Graphics grphcs,String text,int fontStyle, Color fontColor,Color backgroundColor,  double x_indent,  double y_indent) {
        if (coverBox!=null)
            {                        
            int actFontSize=getActFontSize(coverBox,grphcs,text,fontStyle, x_indent, y_indent);                       
            grphcs.setFont(new Font(grphcs.getFont().getFontName(),fontStyle , actFontSize));                            
            grphcs.setColor(backgroundColor);                                  
            FontMetrics fontMetrics=new FontMetrics(grphcs.getFont()) {};                            
            Rectangle2D rect=(fontMetrics.getStringBounds(text, grphcs));                            
            int x=(int) (coverBox.getWidth()-rect.getWidth())/2;
            int y=(int) (coverBox.getHeight()-rect.getHeight())/2;            
            grphcs.fillRect(x,y, (int)rect.getWidth(),(int)rect.getHeight());               
            grphcs.setColor(fontColor);                      
            grphcs.drawString(text, x,fontMetrics.getAscent()+y);                                        
            return new Point(x,y);
            }
        return null;
    }

}
