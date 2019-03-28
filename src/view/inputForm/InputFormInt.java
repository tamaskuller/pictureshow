/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inputForm;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractButton;
import view.inputForm.InputBox;
import view.interfaces.AutoShape.AutoShapeCompInt;

/**
 *
 * @author Tamas Kuller
 */
public interface InputFormInt {
    public void addController(AbstractButton controller,  boolean underneath, ActionListener actionListener);
   public void addInputBox(InputBox inputBox, boolean underneath);
   public void addScrollTable(JScrollTable scrollTable, boolean underneath);
public void addComponent(Component component, boolean underneath);
public boolean removeComponent(Component component);
    public List<InputBox> getInputBoxes();
  //  public void addListener(InputFormActionListenerInt listener);    
    public void close();
    public void open(); 
    public void updateSizeLocation();
    
   
}
