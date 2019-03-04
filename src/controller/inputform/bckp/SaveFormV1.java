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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.exceptions.PreexistingEntityException;
import view.inputForm.InputBox;
import view.inputForm.InputForm;
import view.interfaces.PictureFrameInterface;
import view.recordtypeclasses.JFrameBaseFormParams;

/**
 *
 * @author Tamas Kuller
 */
public class SaveFormV1 extends InputForm{

    public SaveFormV1(PictureFrameInterface parent) {
        super(new JFrameBaseFormParams.BaseFormParamsBuild().newInstance()
                                .title("Save the selected layout")
                                .x(100)
                                .y(100)
                                .build());
        createForm(parent);        
    }
    
     private void createForm(PictureFrameInterface parent)
     {
         InputForm saveThis=this;         
         InputBox input=new InputBox("Please give a unique name to save the layout!", "",20, InputTypes.TEXT, true);
         addInputBox(input, true);
         addController(new JButton("Save"), true, 
                      new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 try {
                     parent.adminSwitched();
                     PictDBActionsV1.getInstance().saveFrame(parent,input.getText().getText());
                     parent.adminSwitched(); 
                     close();
                 } catch (PreexistingEntityException ex) {
                     JOptionPane.showMessageDialog(saveThis, ex.getMessage());                     
                     parent.adminSwitched();                     
                     open();
                 }
             }

         });
                 
         addController(new JButton("Cancel"),false, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            close();
                    }
                });
                open();
     }
    
}
