/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inputForm;

import enums.InputTypes;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


/**
 *
 * @author Tamas Kuller
 */
public class InputBox extends JPanel{
    protected JLabel label;
    protected JTextField text;
    protected InputTypes inputType;
    private BoxLayout bLayout;    
    protected boolean mandatory;
    protected boolean inputUnderNeath=false;
    protected final int ADD_GAP=5;

    public InputBox(String label, String text, int length, InputTypes inputType, boolean mandatory, boolean inputUnderNeath) {        
        this.label = new JLabel(label);        
        this.text = new JTextField(text, length);
        this.inputType = inputType;
        this.mandatory=mandatory;
        this.inputUnderNeath=inputUnderNeath;
        bLayout=new BoxLayout(this, (inputUnderNeath)?BoxLayout.Y_AXIS:BoxLayout.X_AXIS);
        setLayout(bLayout);        
        //setBorder(BorderFactory.createLineBorder(Color.yellow));
        init();

    }
        
    private synchronized void init() {
        add(this.label);
        int xGap=(bLayout.getAxis()==BoxLayout.X_AXIS)?ADD_GAP:0;
        int yGap=(bLayout.getAxis()==BoxLayout.Y_AXIS)?ADD_GAP:0;
        add(Box.createRigidArea(new Dimension(xGap,yGap)));
        add(this.text);
        this.text.addKeyListener(new KeyAdapter() {            
            @Override
            public void keyTyped(KeyEvent e) {
                if (validateKeyPressed(e.getKeyChar()))
                    super.keyPressed(e);              
                else
                  e.consume();
            }                                    
            
        });
        if (inputType==InputTypes.PATH)
                this.text.addMouseListener(new InputBoxAttachFileListener(this));                

    }
    
    
    public JLabel getLabel() {
        return label;
    }

    public JTextField getText() {
        return text;
    }

    public InputTypes getInputType() {
        return inputType;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InputBox)
            {
            InputBox inputBoxComp=(InputBox) obj;
            return this.text.equals(inputBoxComp.text)&&this.label.equals(inputBoxComp.label);
            }
        return false;
    }
    
    private boolean validateKeyPressed(char keyChar) {
        switch (inputType)
        {case NUMBER:
            return isNumber(keyChar);                            
        }
        return true;
    }
    
    private boolean isNumber(char kChar)
    {
        try {           
           Integer.parseInt(Character.toString(kChar));
        }
        catch (NumberFormatException e)
                {
                    return false;
                }        
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); //To change body of generated methods, choose Tools | Templates.
        text.setEnabled(enabled);
    }

    
    
    
    
    
}
