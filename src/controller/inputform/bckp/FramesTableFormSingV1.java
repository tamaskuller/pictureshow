/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform.bckp;

import controller.DB.LoadFrameTableModel;
import controller.DB.PictDBActionsV1;
import controller.DB.exceptions.NonexistentEntityException;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import view.inputForm.InputForm;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.inputForm.JScrollTable;
import view.util.Observer;
/**
 *
 * @author Tamas Kuller
 */
public class FramesTableFormSingV1 extends InputForm implements Observer {
    private static FramesTableFormSingV1 instance;
    protected LoadFrameTableModel loadFrameTable;
    protected JTable frameTable;
    
   
    static {
        instance=new FramesTableFormSingV1();
    }
        
    protected FramesTableFormSingV1() {
        super(new JFrameBaseFormParams.BaseFormParamsBuild().newInstance()
                        .title("Layouts stored in DB")
                        .x(100)
                        .y(100)                        
                        .build());
        createForm();
    }
        
                
    public static FramesTableFormSingV1 getInstance()
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
                                    try {
                                        PictDBActionsV1.getInstance().deleteFrame(name);
                                        JOptionPane.showMessageDialog(frameTable, name+" was deleted successfully!");     
                                    } catch (NonexistentEntityException ex) {
                                        JOptionPane.showMessageDialog(frameTable, ex.getMessage());     
                                    }
                                    updateSizeLocation();
                                }
                        }
                    });
                    addController(new JButton("Draw PictureFrame"), true, new InputFormListenerLoadFrame(PictDBActionsV1.getInstance(),this,scrollTable ));
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
                            close();;
                        }
                    });                   
                    

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
    public void update(Action action, Object subject) {
        if (action==Action.DB_SAVE_FRAME)
            updateSizeLocation();
    }
    
    
    
}
