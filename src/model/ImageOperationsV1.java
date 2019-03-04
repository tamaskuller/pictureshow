/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import util.EnvironmentParams;
import view.NamedImage;
import view.interfaces.NamedImageInt;

/**
 *
 * @author Tamas Kuller
 */
public class ImageOperationsV1{                      
    
    public static NamedImageInt getNamedImage(String path, String name) {                                  
        BufferedImage imageTemp=null;         
       URL url=null;       
       File file=null;
       String actualPath=(path==null||path.isEmpty())?EnvironmentParams.getProjectPath()+EnvironmentParams.REL_PATH_TO_PICT+name:path;                  
        try {
            url = new URL(actualPath);
        } catch (MalformedURLException ex) {
            System.out.println("not URL");
        }          
       System.out.println("loadfilepath:"+actualPath);
       try {            
            if (url==null)
                {
                file=new File(actualPath); 
                imageTemp = ImageIO.read(file);
                name=(name==null || name.isEmpty())?file.getName():name;
                 }
            else
                {
                 imageTemp = ImageIO.read(url);
                 System.out.println("read using URL");
                }
            } catch (IOException ex) {
                System.out.println("File couldn't be read from"+path);                      
            }
       if (imageTemp!=null)
            return new NamedImage(imageTemp, name);                                 
       else
           return null;
       }

    
}
