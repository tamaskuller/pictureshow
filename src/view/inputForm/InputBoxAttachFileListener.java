/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inputForm;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import util.DynamicEnvironmentParams;
import util.StaticEnvironmentParams;

/**
 *
 * @author Tamas Kuller
 */
public class InputBoxAttachFileListener implements MouseListener{
    InputBox parent=null;
    JFileChooser fileChooser=null;    
    File lastUsedPath;    
    
    public InputBoxAttachFileListener(InputBox parent) {        
        this.parent=parent;
    }        
    
    private void openFileDialog(Component component)
    {
                fileChooser=new JFileChooser();                                  
                if (getLastPath()!=null)
                    fileChooser.setCurrentDirectory(getLastPath());
                if (fileChooser.showOpenDialog(component)==JFileChooser.APPROVE_OPTION)
                {
                    File file=fileChooser.getSelectedFile();    
                    setLastUsedPath();
                    parent.getText().setText(file.getAbsolutePath());                                      
                }                                                       
    }
    
    private void setLastUsedPath() {
        lastUsedPath=fileChooser.getCurrentDirectory();
        DynamicEnvironmentParams.getInstance().setEnvLastUsedPath(fileChooser.getCurrentDirectory());    
    }
    
    private File getLastPath()
    {
        File lastPath=null;
        if (lastUsedPath!=null)
            lastPath=lastUsedPath;
        else if (DynamicEnvironmentParams.getInstance().getEnvLastUsedPath()!=null)
                lastPath=DynamicEnvironmentParams.getInstance().getEnvLastUsedPath();
        return lastPath;
    }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {        
        if (e.getClickCount()==2)    
            openFileDialog(e.getComponent());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {    
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    
}
