/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import pictureshow.PictureShow;

/**
 *
 * @author Tamas Kuller
 */
public final class StaticEnvironmentParams {
    public final static String REL_PATH_TO_PICT="/pictures/";
    public final static String REL_PATH_TO_HTML="/Webpages/Instructions.html";    
    public static AppHostType appHostType=AppHostType.LOCAL;
    
    public final static String ftpUrl = "ftp://%s:%s@%s%s;type=i";
    public static String host = "ftp.uw.hu:21/pictureshow";
    public static String user = "tamaskuller";
    public static String pass = "1aa0b891";
    
        
            
    public static String getProjectPath()
    {
        String path=null;
        switch (appHostType)
        {case IDE_RUN:
            path=Paths.get("").toAbsolutePath().toString();
            break;
        case LOCAL:
        case REMOTE:
            String jarFile=PictureShow.class.getProtectionDomain().getCodeSource().getLocation().toString();
            path=jarFile.substring(0, jarFile.lastIndexOf("/"));
            break;
        }
        URL url = PictureShow.class.getProtectionDomain().getCodeSource().getLocation();               
        URL url2=null;
        try {
            url2=new URL(url.toString());
        } catch (MalformedURLException ex) {
            System.out.println("not an URL");
        }
        
        File file=new File(url.getFile());        
        return path;                        
    }

    public static String getFtpUrl(String uploadPath) {
        return String.format(ftpUrl, user, pass, host, uploadPath);
    }
    
    public static Dimension getScreenDimension()
    {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
 

    
    
    
    
}
