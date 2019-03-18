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
import view.enums.FormTypes;
import view.inputForm.InputForm;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.util.Observer;
import view.util.Observer.Action;

/**
 *
 * @author Tamas Kuller
 */
public class MessageFormV1 extends InputForm implements AdaptInputFormIntSubject {

    private List<Observer> observers;
    private Action action;
    private boolean onTop;

    public MessageFormV1(JFrameBaseFormParams params, String message, FormTypes formType, boolean onTop) {
        super(params);
        observers = new ArrayList<>();
        this.action = getAction(formType);
        this.onTop = onTop;
        createForm(message);
    }

    private synchronized void createForm(String title) {
        addComponent(new JLabel(title), true);
        open();
        updateSizeLocation();
        setAlwaysOnTop(onTop);
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

    private Action getAction(FormTypes formType) {
        Action action = null;
        switch (formType) {
            case MESSAGE_FORM_DELETE:
                action = Observer.Action.READY_FOR_DELETE;
                break;
            case MESSAGE_FORM_SAVE:
                action = Observer.Action.READY_FOR_SAVE;
                break;
            case MESSAGE_FORM_DRAW:
                action = Observer.Action.READY_DRAW;
                break;
        }    
    return action;
    }
}
