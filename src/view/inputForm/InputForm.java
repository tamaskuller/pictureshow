/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inputForm;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import view.JFrameBaseFormAbs;
import view.recordtypeclasses.JFrameBaseFormParams;


/**
 *
 * @author Tamas Kuller
 */
public class InputForm extends JFrameBaseFormAbs implements InputFormInt{
    //private JFrame inputForm;
    private SpringLayout sLayout;    
    private List<InputBox> inputBoxes;       
    private final int AUTO_SIZE_ADD=5;
    

    protected InputForm(JFrameBaseFormParams params) {
        super(params);
        sLayout=new SpringLayout();
        getContentPane().setLayout(sLayout);
        this.inputBoxes=new ArrayList<>();        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e); //To change body of generated methods, choose Tools | Templates.                
                    updateSizeLocation();
            }                               
        });
        this.setResizable(false);
        
    }    

    @Override
    public void close() {
        setVisible(false);
    }

    @Override
    public void open() {
        setVisible(true);
    }
   
    
    
    public static class InputFormBuild {
        InputForm inputForm;
        JFrameBaseFormParams params;
        
    public InputFormBuild(JFrameBaseFormParams params) {        
        this.params=params;
    }        
    
    
        
        public InputFormBuild newInstance(){
            inputForm=new InputForm(params);            
            return this;        
        }
                        
        public InputFormBuild addInputBox(InputBox inputBox,boolean underneath) {        
            inputForm.addInputBox(inputBox,underneath);           
            return this;
        }        
        
                
        
        public InputForm build() {        
        return inputForm;
        }        
        
    }
    
    
    @Override
    public void updateSizeLocation()
    {
        Dimension size=new Dimension();
        size.setSize(getFrameSouthEastCorner().getX()+AUTO_SIZE_ADD*2, getFrameSouthEastCorner().getY()+AUTO_SIZE_ADD*2);        
        setSize(adjustDimension(size));
        
    }    

    
    

    @Override
    public void addInputBox(InputBox inputBox,boolean underneath) {                
        if (!inputBoxes.contains(inputBox))
            {
            add(inputBox);
            inputBoxes.add(inputBox);             
            adjustComponentPos(inputBox, underneath);                        
            updateSizeLocation();
            }        
    }
    
    

    @Override
    public List<InputBox> getInputBoxes() {
        return inputBoxes;        
    }


    @Override
    public void addScrollTable(JScrollTable scrollTable,boolean underneath) {
        this.add(scrollTable);      
        adjustComponentPos(scrollTable, underneath);                    
        updateSizeLocation();
    }

    
   @Override
    public void addController(AbstractButton controller, boolean underneath, ActionListener actionListener) {                            
            if (actionListener!=null)
                controller.addActionListener(actionListener);
            add(controller); 
            adjustComponentPos(controller,underneath);            
            updateSizeLocation();            
    }


    @Override
    public void addComponent(Component component, boolean underneath)
    {
        add(component);
        adjustComponentPos(component,underneath);
        updateSizeLocation();    
    }
    
    @Override
    public boolean removeComponent(Component component) {
        int inputBoxIndex=-1;
        for (InputBox inputBox : getInputBoxes()) {
                    if (inputBox==component)
                        inputBoxIndex=getInputBoxes().indexOf(inputBox);
        }
        if (inputBoxIndex>=0)
            getInputBoxes().remove(inputBoxIndex);
        for (Component component1 : getContentPane().getComponents()) {
            if(component1==component)            
                {
                remove(component);                
                return true;
                }
        }        
        return false;
    }
    
    
    private void adjustComponentPos(Component component, boolean underneath)
    {
            Component[] components=getContentPane().getComponents();            
            if (components.length>0)
                {
                int position=0;
                while (position<components.length&&components[position]!=component) {            
                    position++;}
                if (position<components.length)
                    if (position>0)
                        {
                        sLayout.putConstraint(SpringLayout.NORTH, component, underneath?AUTO_SIZE_ADD:0,underneath?SpringLayout.SOUTH:SpringLayout.NORTH , components[position-1]);
                        sLayout.putConstraint(SpringLayout.WEST, component,AUTO_SIZE_ADD, underneath?SpringLayout.WEST:SpringLayout.EAST, underneath?getContentPane():components[position-1]);                
                        }
                    else
                        {                            
                            sLayout.putConstraint(SpringLayout.NORTH, component,AUTO_SIZE_ADD,SpringLayout.NORTH , getContentPane());
                            sLayout.putConstraint(SpringLayout.WEST, component,AUTO_SIZE_ADD,SpringLayout.WEST, getContentPane());                
                        }
                }
    }    
       
    
    private Point getFrameSouthEastCorner()
    {
     int maxY=0;
     int maxX=0;     
     for (Component component : getContentPane().getComponents()) {                    
                    if (component.getY()+component.getSize().getHeight()>maxY)
                        maxY=component.getY()+component.getHeight();
                    if (component.getX()+component.getSize().getWidth()>maxX)
                        maxX=component.getX()+component.getWidth();                                                          
                }
     return new Point(maxX, maxY);
    }
        
    private Dimension adjustDimension(Dimension dimension)
    {        
        double addWidth=getSize().getWidth()-getContentPane().getWidth();
        double addHeight=getSize().getHeight()-getContentPane().getHeight();
        Dimension resultDimension=new Dimension();
        resultDimension.setSize(dimension.getWidth()+addWidth, dimension.getHeight()+addHeight);
        return resultDimension;
    }
    
    private JButton createButton(String text)
    {        
        JButton button=new JButton(text);        
        return button;
    }

    
    
    
}
