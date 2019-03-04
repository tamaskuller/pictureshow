/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Tamas Kuller
 */
public class FileOperations {
    private static URL url=null;  
    private static File fileFrom=null;
    private static OutputStream outputStream=null;        
    private static FileInputStream fileInputStream=null;        
        
    
    public static boolean putFile(String fromPath,String toPath)
    {        
            if (fromPath!=null&&toPath!=null&&!fromPath.equals(toPath))
                if (!fromPath.isEmpty())
                {
                    System.out.println("putfile:"+fromPath+"->"+toPath);
                    if (EnvironmentParams.appHostType==AppHostType.LOCAL)
                    {
                        FileOutputStream fileOutputStream=null;
                        File fileTo=new File(EnvironmentParams.getProjectPath()+toPath);                                        
                        fileFrom=new File(fromPath);    
                        if (fileTo.exists())
                        {
                            try {                        
                                fileOutputStream=new FileOutputStream(fileTo);
                            } catch (FileNotFoundException ex) {
                                System.out.println("Output file couldn't be found "+fileTo);
                            }       
                            try {
                                fileInputStream=new FileInputStream(fileFrom);
                            } catch (FileNotFoundException ex) {
                                System.out.println("Input file couldn't be found "+fileFrom);
                            }                     
                        copyData(fileInputStream, fileOutputStream);
                        }
                        }
                    else
                        return putFileToFtp(fromPath, toPath);
                }
        return true;                
    }
    
    public static boolean putFileToFtp(String fromPath,String toPath)
    {
        
        String ftpUrl=EnvironmentParams.getFtpUrl(toPath);
        URLConnection uRLConnection=null;
        //HttpURLConnection.                
        try {
            url = new URL(ftpUrl);              
            } catch (MalformedURLException ex) {
                System.out.println("File cannot be transferred due to an URL issue with "+ftpUrl);
            }        
        try {
            if (url!=null)
               {
                uRLConnection = url.openConnection();                   
                uRLConnection.setDoOutput(true);
                uRLConnection.setDoInput(true);
                uRLConnection.setUseCaches(false);
                uRLConnection.setRequestProperty ("Content-Type", "application/x-www-form-urlencoded");                
                
                }
            } catch (IOException ex) {
                System.out.println("FTP connection failed to "+url);                 
            }
        
        try {
            
            if (!fromPath.isEmpty()&&fromPath!=null)
                {
                fileFrom=new File(fromPath);                                        
                fileInputStream=new FileInputStream(fileFrom);
                }
            } catch (IOException ex) {
                System.out.println("Input file openning failed on path "+fromPath);
            }
            
        if (uRLConnection!=null)
            {
            try {            
                outputStream=uRLConnection.getOutputStream();                
                } catch (IOException ex) {
                    System.out.println("Target file couldn't be opened for writing"+uRLConnection.toString());                    
                }            
            copyData(fileInputStream,outputStream);
            }
        return true;
    }

 private static void copyData(InputStream inputStream, OutputStream outputStream)
 {
     try {                
                
                int length;
                byte[] buffer=new byte[4096];
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);                    
                    }
                outputStream.close();
                inputStream.close();                            
                } catch (IOException ex) {
                    System.out.println("Copy failed");
                }                    
     
 }
    
}
