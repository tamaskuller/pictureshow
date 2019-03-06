/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform;

import controller.DB.LoadFrameTableModel;
import controller.DB.PictDBActionsV1;
import controller.DB.exceptions.NonexistentEntityException;
import controller.adapter.AdaptInputFormIntSubject;
import enums.InputTypes;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.exceptions.PreexistingEntityException;
import view.FormFactoryV1;
import view.enums.FormTypes;
import view.inputForm.InputBox;
import view.inputForm.InputForm;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.inputForm.JScrollTable;
import view.interfaces.PictureFrameInterface;
import view.util.Observer;

/**
 *
 * @author Tamas Kuller
 */
public class FramesTableFormSingV2 extends InputForm implements Observer {
    private static FramesTableFormSingV2 instance;
    private LoadFrameTableModel loadFrameTable;
    private JTable frameTable;
    private static InputBox nameInputBox;
    private static JButton saveButton;
    private PictureFrameInterface parent;
    private FocusListener saveFocusListener;
    private String toDeleteName;
    private String toSaveName;
    private AdaptInputFormIntSubject messageForm;
   
    static {
        instance=new FramesTableFormSingV2();
    }
        
    private FramesTableFormSingV2() {
        super(new JFrameBaseFormParams.BaseFormParamsBuild().newInstance()
                        .title("Manage layouts stored in DB")
                        .x(100)
                        .y(100)                        
                        .build());
        createForm();
    }
        
                
    public static FramesTableFormSingV2 getInstance()
    {
        return instance;
    }
    
    private void createForm()
    {
                    PictDBActionsV1.getInstance().addObserver(this);
                    loadFrameTable=new LoadFrameTableModel(PictDBActionsV1.getInstance().getFrameRecordsLimited());
                    frameTable=new JTable(loadFrameTable);                                                        
                    frameTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    JScrollTable scrollTable = new JScrollTable(frameTable);                    
                    scrollTable.setPreferredSize(new Dimension(400,300));
                    addScrollTable(scrollTable,true);
                    addController(new JButton("Delete PictureFrame from DB"), true,new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {                           
                            JTable jTable=scrollTable.getjTable();
                            if (jTable.getSelectedRow()!=-1)
                                {
                                String name=(String) jTable.getValueAt(jTable.getSelectedRow(), 0);
                                if (JOptionPane.showConfirmDialog(frameTable, "Are you sure you want to delete "+name+" Frame from DB?","Delete Frame?", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)                                
                                    {                                                                        
                                    toDeleteName=name;
                                    deleteInit(toDeleteName);
                                    //deleteFrame(name);
                                    }
                                //updateSizeLocation();
                                }
                        }
                    });
                    addController(new JButton("Draw PictureFrame"), true, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JTable jTable=scrollTable.getjTable();
                            if (jTable.getSelectedRow()!=-1)
                                try {
                                    PictDBActionsV1.getInstance().loadFrame((String) jTable.getValueAt(jTable.getSelectedRow(), 0));
                                    } catch (NonexistentEntityException ex) {
                                        JOptionPane.showMessageDialog(jTable, ex.getMessage());
                                    }
                        }
                    });                
                    addController(new JButton("Refresh Frames list"), false,  new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {                                                        
                            updateSizeLocation();
                        }
                    }
                    );                                        
                    addController(new JButton("Exit"), false,new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            close();
                        }
                    });                                                            
                    nameInputBox=new InputBox("Name", "", 25, InputTypes.TEXT,true, false);                        
                    saveButton=new JButton("Save Frame");        
                    this.addInputBox(nameInputBox, true);        
                    this.addController(saveButton, false,new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {                                                        
                            String name=nameInputBox.getText().getText();
                            if (name!=null && !name.isEmpty())
                                {
                                toSaveName=name;
                                saveInit(toSaveName);
                                }
                        }
                    });
                    setSaveFocusListener();                    
                    nameInputBox.getText().addFocusListener(saveFocusListener);
                    saveButton.addFocusListener(saveFocusListener);
                    showHideSaveFields(false);
                    
    }
    
    
    
    @Override
    public void updateSizeLocation() {
        super.updateSizeLocation(); //To change body of generated methods, choose Tools | Templates.
        loadFrameTable.setData(PictDBActionsV1.getInstance().getFrameRecordsLimited());
        loadFrameTable.fireTableDataChanged();
        if (!loadFrameTable.getData().isEmpty())
            frameTable.setRowSelectionInterval(frameTable.getRowCount()-1,frameTable.getRowCount()-1);
        
        
        
    
    }

    @Override
    public synchronized void update(Action action) {
        switch (action)
        {
            case DB_SAVE_FRAME:
                toSaveName=null;  
                updateSizeLocation(); 
                break;
            case DB_DELETE_FRAME:
                toDeleteName=null;                
                updateSizeLocation();                
             break;
            case READY_FOR_DELETE:                 
                deleteFrame(toDeleteName);                                        
            break;
            case READY_FOR_SAVE:                 
                saveFrame(toSaveName, parent);
            break;
        }
        
            
    }
    
    public void showSaveSection(PictureFrameInterface parent)
    {
        this.parent=parent;         
        nameInputBox.getText().setText(parent.getIconString());        
        showHideSaveFields(true);    
    }
    
    private void createMessageForm(FormTypes formTypes, String title)
    {
        messageForm=FormFactoryV1.createForm(formTypes, null, null, new JFrameBaseFormParams.BaseFormParamsBuild().newInstance()
                .title(title)                                                                
                .x(this.getX())
                .y(this.getY())                       
               .build());        
        messageForm.addObserver(getInstance());                
       
    }
    
    private synchronized void deleteInit(String name)
    {
        createMessageForm(FormTypes.MESSAGE_FORM_DELETE, "Please wait while deleting of "+name+" is in progress...");
    }
    
    private synchronized void deleteFrame(String name)
    {
        try {               
                                
            
            PictDBActionsV1.getInstance().deleteFrame(name);          
            messageForm.close();                                                                   
            JOptionPane.showMessageDialog(frameTable, name+" was deleted successfully!");                         
            messageForm.close();             
            } catch (NonexistentEntityException ex) {
              JOptionPane.showMessageDialog(frameTable, ex.getMessage());     
            }
       
    }

    private synchronized void saveInit(String name)
    {
            createMessageForm(FormTypes.MESSAGE_FORM_SAVE, "Please wait while saving of "+name+" is in progress...");
    }
    
    private void saveFrame(String name, PictureFrameInterface parent)
    {
                 try {            
                    parent.adminSwitched();
                     PictDBActionsV1.getInstance().saveFrame(parent,name);
                     setSaveFieldsEnabling(false);
                     messageForm.close();                                                       
                     JOptionPane.showMessageDialog(frameTable, name+" was saved successfully!");     
                     messageForm.close();                                                       
                    parent.deActivate();
                     
                     //close();
                 } catch (PreexistingEntityException ex) {
                     JOptionPane.showMessageDialog(this, ex.getMessage());                     

                     //open();
                 } finally {
                    parent.adminSwitched();                     
                 }
    }
 private void setSaveFieldsEnabling(boolean enabled)
    {
        nameInputBox.setEnabled(enabled);      
        saveButton.setEnabled(enabled);                                
        
    }   
 
 public void showHideSaveFields(boolean show)
 {   
     setSaveFieldsEnabling(show);
     nameInputBox.setVisible(show);
     saveButton.setVisible(show);
     updateSizeLocation();     
 }
 
 private void setSaveFocusListener()
 {
     saveFocusListener=new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            if (parent!=null)
                                parent.activate();
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            if (parent!=null)
                                parent.deActivate();
                        }
                    
    };
 }
 
}
