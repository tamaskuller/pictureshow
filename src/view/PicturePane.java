/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.AlphaComposite;
import java.awt.Component;
import view.interfaces.PictureComponentInterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPopupMenu;
import view.enums.MotionTypes;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.PaintRequestParams;
import view.recordtypeclasses.PictCompParams;
import view.interfaces.PicturePaneGettersInt;
import view.interfaces.NamedImageInt;

/**
 *
 * @author Kuller Tamas
 */
public class PicturePane extends PictureComponent implements PicturePaneInterface {        

    protected final static float TRANSPARENCY=0.95f;
    protected List<PictureComponentInterface> pictureComponents;                  
    private List<PaintRequestParams> stateRequests;        
    protected PicturePaneGettersInt picturePaneGetters;
    private Object button;   
   protected MotionTypes reOrderMotionType=MotionTypes.FAST_FLOWING;   
   protected boolean fullState;         
   protected Dimension fullStateCurrBaseSize;
    protected int sizeRefreshCounter=0;    
    private boolean firstShow=true;
    private boolean minOverride=true;   
    
   
    public PicturePane(PicturePaneInterface parent,PictCompParams params,boolean fullState) {                                      
       super(parent,params);                
        this.pictureComponents=new ArrayList<>();
        this.stateRequests=new ArrayList<>();
        fullStateCurrBaseSize=new Dimension();
        fullStateCurrBaseSize.height=currBaseSize.height;
        fullStateCurrBaseSize.width=currBaseSize.width;        
        this.button=null;  
        this.shown=false;
        this.underConst=false;               
        this.resizeBorderColor=Color.BLUE;        
        System.out.println("Width:"+getWidth());                
        minWidthMultiplier=0.3; 
        this.fullState=fullState;
        System.out.println("initfullstate:"+fullState);
        
                        
    }

    
    
    @Override
    public synchronized void showState(boolean forced, MotionTypes motionType)
    {               
        System.out.println("SETSTATE");
        if (this.getParent()!=null)
                  showHideComponent(button, motionType, true, false, getComponentOrder(button));                                             
        if (firstShow)
            {
            System.out.println("firstshow"+getIconString());                
            superPaintPict(true,motionType, true);
            if (!fullState)
                superPaintPict(false,MotionTypes.FAST_FLOWING , true);
            firstShow=false;
            }
        showHideComponents(fullState, forced,motionType);            
        
    }       
            
    @Override
    public void setFullState(boolean fullState, MotionTypes motionType, boolean checkMin) {        
        System.out.println("setfullstate");                
            this.fullState = fullState;                                        
            if (fullState)
                {
                superPaintPict(true, motionType, checkMin);
                System.out.println("getbacktoorigbasesize"+fullStateCurrBaseSize.height);
                minimized=false;                                  
                }   
            else 
                minOverride=checkMin;                    
            showState(true, motionType);
    }
  
    @Override
    public synchronized void update(Action action) 
    {                       
        if (!isUnderConst()&&action==Action.UNDERCONST_READY_TO_PARENT)
            {            
            if (!fullState&&minimized==false)                
                {
                System.out.println("updatecurrheight"+currBaseSize.height);
                minimized=true; 
                superPaintPict(false, defaultMotionType, true);
                } 
            parentPane.update(Action.UNDERCONST_READY_TO_PARENT);            
            }        
    }               

    
    @Override
    public boolean isAdminEnabled() {
        return adminEnabled;
    }
    
    private boolean underConstComp()
    {        
        for (PictureComponentInterface pictureComponent : pictureComponents) {
            if (pictureComponent.isUnderConst()) 
                return true;           
        }        
        return false;
        
    }
    
   
    @Override
    public boolean isUnderConst() {         
        return super.isUnderConst()||underConstComp()||isFullState()!=fullState;
    }

    
   @Override
    public boolean isFullState() {
         return fullState;
    } 

    @Override
    public void hideShowSwitch() {        
        setFullState(!fullState, null, true);
    }

        @Override
    public void minimize() {
        setFullState(false, null, true);
    }   

    
    @Override
    public void maximize() {
        setFullState(true, null, true);
    }   

    
    
    @Override
    public int getComponentOrder(Object component) {
        if (component instanceof Component)
            return getComponentZOrder((Component) component);                    
        return 0;
    }
    
    public void addComponent(Object component, int order)
    {
        if (component instanceof Component)
            {
            this.add((Component) component);    
            setCompOrder((Component) component, order);            
            }
        System.out.println("ADD"+component.toString());         
    }
    
    private int adjCompOrder(Object component, int order) {
        if (component==button||getComponentCount()==1)
            return 0;
        else
             return (getCompCount()-1>=order)?(order>0?order:1):getCompCount()-1;
    }
    
    private int getCompCount()
    {
        return pictureComponents.size()+((button==null)?1:0);
    }
    
    @Override
    public void setCompOrder(Object component, int order)
    {
        if (component instanceof Component)            
            setComponentZOrder((Component)component, adjCompOrder(component, order));
        Collections.sort(pictureComponents);
    }
    
    @Override
    public void addPictComponent(PictureComponentInterface component, int order)
    {
            this.pictureComponents.add(component);                            
            addComponent(component, order);                      
    }
    
    @Override
    public void addButton(PictureComponentInterface component, int order)
    {
        addPictComponent(component, 0);
        this.button=component;               
    }                            
    
    @Override
    public void addPictPane(PicturePaneInterface component, int order) {
            addPictComponent(component, order);            
    }

    
    @Override
    public void showHideComponent(Object component ,MotionTypes motionType, boolean show, boolean forced, int order)
    {
        PaintRequestParams paintRequest=new PaintRequestParams(show,forced, motionType,  order, false);                
        for (PictureComponentInterface pictureComponent : pictureComponents) {
            if (component==pictureComponent) pictureComponent.paintPict(paintRequest);
        }                        
    }
    
    @Override    
    public void showHideComponents(boolean show, boolean forced, MotionTypes motionType)
    {        
        for (Component component : getComponents()) {                  
            showHideComponent(component,motionType,(button==component)?true:show, forced, getComponentZOrder(component));                                    
        }        
        
    }

       

    @Override
    public synchronized void paintPict(PaintRequestParams paintRequest) {
        if (isMinimzed()&&!parentPane.isFullState())
                superPaintPict(true,MotionTypes.FAST_FLOWING , paintRequest.checkMin);        
        super.paintPict(paintRequest); //To change body of generated methods, choose Tools | Templates.        
        setFullState(paintRequest.show, paintRequest.motionType, paintRequest.checkMin);
    }        

    private void superPaintPict(boolean show, MotionTypes motionType,boolean checkMin)
    {
        super.paintPict(new PaintRequestParams(show, true, motionType,parentPane.getComponentOrder(this) ,checkMin));        
    }

    
    
    
    public void setCompOrderMotion(Object component, int order,MotionTypes motionType)
   {                
            synchronized (this)
            {
            showHideComponent(component,motionType,false,true,getComponentOrder(component));                
            showHideComponent(component,motionType,true, true, adjCompOrder(component, order));        
            }
            Collections.sort(pictureComponents);
            
    }
    
    @Override
    public void activateComponent(Object component, MotionTypes motionType)
    {        
        setCompOrderMotion(component, 0, motionType);
    }
        
    private void buttonClicked()
    {                
        //setFullState(!fullState, null, fullState); 
        hideShowSwitch();
        activateComponent(button, null);                    
    }

    @Override
    public synchronized void updateSizeLocation() {                
        super.updateSizeLocation(); //To change body of generated methods, choose Tools | Templates.        
        for (PictureComponentInterface pictureComponent : pictureComponents) {            
                pictureComponent.updateSizeLocation()    ;                                              
        }               
        
    }

    
    @Override
    public synchronized void setCurrBaseSizeLocToCurrSizeLoc(boolean adminEnabled) {
    if (!adminEnabled)
        for (PictureComponentInterface pictureComponent : pictureComponents) {            
            pictureComponent.setCurrBaseSizeLocToCurrSizeLoc(adminEnabled);            
            }   
    super.setCurrBaseSizeLocToCurrSizeLoc(adminEnabled); 
    
    }   

    
    @Override
    public boolean adminSwitched() {
        boolean result=super.adminSwitched(); //To change body of generated methods, choose Tools | Templates.
        for (PictureComponentInterface pictureComponent : pictureComponents) {
            pictureComponent.adminSwitched();  
        }
         return result;
    }

    @Override
    public synchronized void setAdminEnabled(boolean adminEnabled) {        
        super.setAdminEnabled(adminEnabled); //To change body of generated methods, choose Tools | Templates.        
        for (PictureComponentInterface pictureComponent : pictureComponents) {
            pictureComponent.setAdminEnabled(adminEnabled);            
        }
        
    }
    
    @Override
    public void onClick(Object component) {
       if (!isUnderConst())//&&!parentPane.isUnderConst())
            {    
            if (component==button)
                buttonClicked();    
            else
                activateComponent(component, reOrderMotionType);                    
            }
    }


    @Override
    public Dimension getCurrBaseSize() {
        return currBaseSize;
    }

    @Override
    public Point getCurrBaseLocation() {
        return currBaseLocation;
    }

    @Override
    public Object getGetters() {          
        return this.picturePaneGetters;
    }
    
    @Override
    public void setGetters(Object getter) {
        this.picturePaneGetters=(PicturePaneGettersInt) getter;
    }

    @Override
    public List getPictureComponents() {
        return pictureComponents;
    }

    @Override
    public void setImage(NamedImageInt image) {
        super.setImage(image); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }
    
     @Override
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu=popupMenu;
         for (PictureComponentInterface pictureComponent : pictureComponents) {
             pictureComponent.setPopupMenu(popupMenu);             
         }
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g=(Graphics2D) grphcs.create(); 
        float transparency=TRANSPARENCY;
        if (isAdminEnabled())
            transparency=0.8f;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));                
        super.paint(g); //To change body of generated methods, choose Tools | Templates.                
    }

    @Override
    public void Delete() {
        parentPane.getPictureComponents().remove(this);
        setVisible(false);       
    }

      @Override
    public Dimension getAdjCurrSize(boolean checkMin, boolean adjLocation, boolean calcWithMotion) {
        Dimension adjSize=super.getAdjCurrSize(checkMin, adjLocation, calcWithMotion); //To change body of generated methods, choose Tools | Templates.        
        if (minOverride)
            adjSize=getMinSize(adjSize);                                      
        return adjSize;
    }
    
 }   