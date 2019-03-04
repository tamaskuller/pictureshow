/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform.bckp;

import controller.DB.PictDBActionsInt;
import controller.DB.exceptions.NonexistentEntityException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import view.inputForm.InputFormInt;
import view.inputForm.JScrollTable;

/**
 *
 * @author Tamas Kuller
 */
public class InputFormListenerLoadFrame implements ActionListener{

    PictDBActionsInt pictDBActions;
    InputFormInt inputFormInt;
    JScrollTable scrollTable;

    public InputFormListenerLoadFrame(PictDBActionsInt pictDBActions, InputFormInt inputFormInt, JScrollTable scrollTable) {
        this.pictDBActions = pictDBActions;
        this.inputFormInt=inputFormInt;
        this.scrollTable=scrollTable;
    }    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {     
        JTable jTable=scrollTable.getjTable();
        if (jTable.getSelectedRow()!=-1)
            try {
                pictDBActions.loadFrame((String) jTable.getValueAt(jTable.getSelectedRow(), 0));
        } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(jTable, ex.getMessage());
        }
    }
    
}
