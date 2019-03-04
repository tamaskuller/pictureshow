/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform;

import controller.adapter.AdaptInputFormIntSubject;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import view.inputForm.InputForm;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.util.Observer;
import view.util.Observer.Action;

/**
 *
 * @author Tamas Kuller
 */
public class MessageFormV1 extends InputForm implements AdaptInputFormIntSubject{
    List<Observer> observers;    
    Action action;
    
    public MessageFormV1(JFrameBaseFormParams params, String message, Action action) {
        super(params);               
        observers=new ArrayList<>();        
        this.action=action;
        createForm(message);
    }
    
    
    private synchronized void createForm(String title) {        
        addComponent(new JLabel(title), true);                               
        open();                
        updateSizeLocation();
        addWindowListener(new WindowAdapter() {            
            
            @Override
            public synchronized void windowOpened(WindowEvent e) {
                super.windowActivated(e); //To change body of generated methods, choose Tools | Templates.
                notifyObserver(action);                                  
            }

            @Override
            public void windowClosing(WindowEvent e) {                
            }
            });
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public void close() {        
        setAlwaysOnTop(false);
        setEnabled(false);
        super.close(); //To change body of generated methods, choose Tools | Templates.                
        System.out.println("closemessageform");
    }    
        
}
