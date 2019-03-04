/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inputForm;

import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Tamas Kuller
 */
public class JScrollTable extends JScrollPane{
    private JTable jTable;

    public JScrollTable(JTable jTable) {
        super(jTable);
        this.jTable = jTable;
    }

    public JTable getjTable() {
        return jTable;
    }

    
    
    
}
