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
import util.StaticEnvironmentParams;
import view.NamedImage;
import view.interfaces.NamedImageInt;

/**
 *
 * @author Tamas Kuller
 */
public class ImageOperationsV1{                      
    
    public static NamedImageInt getNamedImage(String path, String name) {                                  
        BufferedImage imageTemp=null;         
        NamedImageInt result=null;
       URL url=null;       
       File file=null;
       String actualPath=(path==null||path.isEmpty())?StaticEnvironmentParams.getProjectPath()+StaticEnvironmentParams.REL_PATH_TO_PICT+name:path;                  
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
                file=null;
                 }
            else
                {
                 imageTemp = ImageIO.read(url);
                 System.out.println("read using URL");
                 url=null;
                }
            } catch (IOException ex) {
                System.out.println("File couldn't be read from"+path);                      
            }
       if (imageTemp!=null)
            {
           result=new NamedImage(imageTemp, name);                                
           imageTemp.flush();
           return result;
           }
       else
           return null;
       }

    
}
