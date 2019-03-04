/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Tamas Kuller
 */
public class GenericTableModel extends AbstractTableModel {

    private List<String> columnNames;
    private List<Object[]> data;

    public GenericTableModel(List<String> columnNames, List<Object[]> data) {
        this.columnNames = columnNames;
        this.data = data;
    }    
    
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       return data.get(rowIndex)[columnIndex];
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }
    
    
    
    
}
