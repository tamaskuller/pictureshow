/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform;


import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import util.AppHostType;
import util.StaticEnvironmentParams;
import view.JFrameBaseFormAbs;
import view.interfaces.PictureFrameInterface;
import view.recordtypeclasses.JFrameBaseFormParams;

/**
 *
 * @author Tamas Kuller
 */
public class InstructionForm extends JFrameBaseFormAbs{
    private static InstructionForm instance;
    private PictureFrameInterface parent;
    private final static double INDENT=0.25;

    private InstructionForm(PictureFrameInterface parentFrame) {
        super(new JFrameBaseFormParams.BaseFormParamsBuild().newInstance()
                        .title("PictureShow Instructions")
                        .build());
        this.parent = parentFrame;
        createForm();
    }
    
    public static InstructionForm getInstance(PictureFrameInterface parentFrame)
    {            
        if (instance==null)
            instance=new InstructionForm(parentFrame);
        return instance;
    }
        
    private void createForm() {        
//        setSize(1600,1200);
        setLocSize();
        System.out.println("instructionformParent:"+parent.toString());
        JEditorPane jTextArea;
        try {
            //JTextArea jTextArea=new JTextArea();
            //JScrollPane jScrollPane = new JScrollPane(jTextArea);
            System.out.println("HTMLUrl:"+StaticEnvironmentParams.getProjectPath()+StaticEnvironmentParams.REL_PATH_TO_HTML);
            URL url;
            if (StaticEnvironmentParams.appHostType==AppHostType.IDE_RUN)                
                url = new URL("file:///"+StaticEnvironmentParams.getProjectPath()+StaticEnvironmentParams.REL_PATH_TO_HTML);
            else
                url = new URL(StaticEnvironmentParams.getProjectPath()+StaticEnvironmentParams.REL_PATH_TO_HTML);                            
            jTextArea=new JEditorPane(url);
            JScrollPane jScrollPane=new JScrollPane(jTextArea);
            add(jScrollPane);
            
        } catch (IOException ex) {
            Logger.getLogger(InstructionForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void setLocSize()
    {
        //setSize(EnvironmentParams.getScreenDimension().width, EnvironmentParams.getScreenDimension().height);
        //setLocation(getLocation().x+((int) (getWidth()*0.2)),getLocation().y-((int) (getHeight()*0.2)));
        Dimension parentSize=parent.getSize();
        Dimension currSize=getSize();
        Point parentLocation=parent.getLocation();
        Point currLocation=getLocation();
        currLocation.setLocation(parentLocation.getX()+parentSize.getWidth()*INDENT, parentLocation.getY()+parentSize.getHeight()*INDENT);
        currSize.setSize(parentSize.getWidth()*(1-INDENT), parentSize.getHeight()*(1-INDENT));
        setSize(currSize);     
        setLocation(currLocation);
        
        
        
    }
    
    
}
