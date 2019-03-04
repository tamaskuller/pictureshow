/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inputForm;

import enums.InputTypes;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author Tamas Kuller
 */
public class InputBox extends JPanel{
    protected JLabel label;
    protected JTextField text;
    protected InputTypes inputType;
    protected boolean mandatory;
    protected final int ADD_GAP=5;

    public InputBox(String label, String text, int length, InputTypes inputType, boolean mandatory) {        
        this.label = new JLabel(label);        
        this.text = new JTextField(text, length);
        this.inputType = inputType;
        this.mandatory=mandatory;
        //setBorder(BorderFactory.createLineBorder(Color.yellow));
        add(this.label);
        this.label.setLocation(0, 0);
        add(this.text);
        this.text.setLocation(ADD_GAP+this.label.getWidth()+ADD_GAP,this.label.getY());        
        
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
            int number=Integer.parseInt(Character.toString(kChar));
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
