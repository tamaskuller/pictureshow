/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform.bckp;

import controller.DB.PictDBActionsV1;
import enums.InputTypes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.exceptions.PreexistingEntityException;
import view.inputForm.InputBox;
import view.interfaces.PictureFrameGettersInt;
import view.interfaces.PictureFrameInterface;
import view.util.Observer;

/**
 *
 * @author Tamas Kuller
 */
public class FramesTableFormSaveV1 extends FramesTableFormSingV1 implements Observer{
    private PictureFrameInterface parent;
    private List<Object[]> data;
    private InputBox nameInputbox;
    private JButton saveButton;
    private boolean saved=false;

    public FramesTableFormSaveV1(PictureFrameInterface parent) {
        super();
        //getInstance().close();
        this.setTitle(this.getTitle()+" - Save the selected one");                
        this.parent = parent;       
        init();
    }
        
    
    public void init()
    {       
    nameInputbox=new InputBox("Name", parent.getIconString(), 25, InputTypes.TEXT,true,false);    
    saveButton=new JButton("Save Frame");
    this.addInputBox(nameInputbox, true);
    this.addController(saveButton, false,new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {                                                        
                            String name=nameInputbox.getText().getText();
                            if (name!=null && !name.isEmpty())
                                saveFrame(name);
                        }
                    }
                    );        
    this.addWindowFocusListener(new WindowFocusListener() {
        @Override
        public void windowGainedFocus(WindowEvent e) {  
            if (parent!=null)
                parent.activate();
            else
                close();
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
                if (saved)
                    close();
                parent.deActivate();
                
        }
    });
    parent.addObserver(this);
    // updateSizeLocation();
    
    }
         
    
       

    private void saveFrame(String name)
    {
                 try {            
                    parent.adminSwitched();
                     PictDBActionsV1.getInstance().saveFrame(parent,name);
                     saved();
                     //close();
                 } catch (PreexistingEntityException ex) {
                     JOptionPane.showMessageDialog(this, ex.getMessage());                     

                     //open();
                 } finally {
                    parent.adminSwitched();                     
                 }
    }
    
    
    
    private Object[] getParentData()
    {
        Object[] frameData=new Object[4];            
        frameData[0]="";        
        frameData[1]=((PictureFrameGettersInt) parent.getGetters()).getFrameSize().getWidth();
        frameData[2]=((PictureFrameGettersInt) parent.getGetters()).getFrameSize().getHeight();
        frameData[3]=parent.getPicturePanes().size();
        System.out.println("frameData to save "+(double) frameData[1]);
        return frameData;
    }

    private void saved()
    {
        nameInputbox.setEnabled(false);      
        saveButton.setEnabled(false);
        saved=true;
        
    }

    @Override
    public void update(Action action, Object subject) {
        super.update(action, this); //To change body of generated methods, choose Tools | Templates.
        if (!parent.isActivated())
            close();
    }

    @Override
    public void close() {
        //parent.removeObserver(this);
        super.close(); //To change body of generated methods, choose Tools | Templates.
        
    }

    

    
}
