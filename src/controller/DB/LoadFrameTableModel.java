/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tamas Kuller
 */
public class LoadFrameTableModel extends GenericTableModel {
    
    
    public LoadFrameTableModel(List<Object[]> data) {        
        super(Arrays.asList("Frame name", "Frame Width","Frame Height", "Count of Panes","Date of save"), data);                
    }    

    
    
}
