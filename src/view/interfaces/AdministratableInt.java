/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

/**
 *
 * @author Tamas Kuller
 */
public interface AdministratableInt extends AdministratableGetterInt{      
     public boolean adminSwitched();
     public void setAdminEnabled(boolean adminEnabled);           
 
}
