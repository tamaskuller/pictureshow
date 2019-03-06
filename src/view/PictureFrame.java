/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPopupMenu;
import view.enums.FormTypes;
import view.enums.MotionTypes;
import view.interfaces.NamedImageInt;
import view.interfaces.PictureComponentInterface;
import view.interfaces.PictureFrameInterface;
import view.interfaces.PicturePaneInterface;
import view.interfaces.PictureFrameGettersInt;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.recordtypeclasses.PaintRequestParams;
import view.util.Observer;


/**
 *
 * @author Kuller Tamas
 */
public class PictureFrame extends JFrameBaseFormAbs implements PictureFrameInterface {
    private List<Observer> observers;
   protected List<PicturePaneInterface> picturePanes;
   protected PictureFrameGettersInt pictureFrameGetters; 
    private boolean adminEnabled;
   private boolean fullState;
      private boolean prevFullState;

   private Dimension currBaseSize;   
   private Dimension origSize;
   protected Dimension frameSize;
   protected Color oldBackGroundColor;
   protected double sizeRatioContPaneWidth;
   protected double sizeRatioContPaneHeight;
   protected double sizeRatioWidth;
    protected double sizeRatioHeight;   
    protected JPopupMenu popupMenu;
    private Color resizeBorderColor=Color.RED;
    protected NamedImageInt image=null;    
    protected String imagePath;
    private boolean activated=true;
    protected double minSizeRatioHeight=1;

    private boolean adminSwitch=false;
    private int sizeRefreshCounter=0;
    private PictContentPane pictContentPane;
    private boolean mainForm=false;
    private boolean firstShow=true;
    

//    private String title;
   
   
    public PictureFrame(JFrameBaseFormParams params) {        
        super(params);
        observers=new ArrayList<>();
        this.setLayout(null);         
        pictContentPane=new PictContentPane();
        setContentPane(pictContentPane);                
        //contentP=getContentPane();
        this.oldBackGroundColor=getContentPane().getBackground();
        this.setVisible(true);                
        
        this.picturePanes=new LinkedList<>();
        this.adminEnabled=false;
        this.fullState=false;         
        this.sizeRatioWidth=1;
        this.sizeRatioHeight=1;        
        if (params.adjMaxSize)
            setMaxSize(null);
        if (params.toCenter)
            setLocToCenter(null);
        setFrameSizeRatios();
        System.out.println("framesize:"+frameSize);
        this.origSize=currBaseSize;
        System.out.println("orig:"+currBaseSize);
        currBaseSize=getSize();
        
  //      this.title="";
        this.addComponentListener(new ComponentAdapter() {
            
            @Override                        
            public void componentResized(ComponentEvent e) {                
                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.                                                                                          
                updateSizeLocation();                 
            }
            
            });                    

        this.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosed(WindowEvent e) {
                activated=false;                                
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
            }
            
            
            @Override
            public void windowOpened(WindowEvent e) {                
                super.windowOpened(e); //To change body of generated methods, choose Tools | Templates.                
                updateSizeLocation();                
            }
            
        });
    }

    
   
        
//grphcs.drawImage(this.image, 0, 0, this.getWidth(),this.getHeight(),null);                                                           
    
    public void addComponent(Object component, int order)
    {
        if (component instanceof Component)        
            {
            Component comp=(Component) component;
            add(comp);    
            JLayeredPane layeredPane=getLayeredPane();                
            //component.setLocation(x, y);
//            setCompOrder(component, order);
            layeredPane.add(comp,  -1);                                           
            layeredPane.setLayer(comp, order);   
            setCompOrder(component, order);                
            repaint();
            //showHideComponent(component, null,true,true, false, getComponentZOrder(component));            
            }
//      
    }
    
   @Override
    public void addPictPane(PicturePaneInterface pictPane,int order)
    {
        addComponent(pictPane, order);
        this.picturePanes.add(pictPane);     
       // pictPane.setVisible();
        
        //showHideComponents((fullState)?true:false, true);
            
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.        
        activated=b;       
    }
    
    
    
    
   @Override
    public boolean adminSwitched()
    {           
        setAdminEnabled(!adminEnabled);
        adminSwitch=true;                                                       
        update(Action.ADMIN_DISABLED);
        updateSizeLocation();
       return isAdminEnabled();
    }
    
   @Override
    public void hideShowSwitch() {                 
            setFullState(!isFullState(), null, true);                                   
    }
        
    

    
    @Override
    public void showHideComponents(boolean show, boolean forced, MotionTypes motionType) {        
        for (Component component : this.getLayeredPane().getComponents()) {                
                showHideComponent(component, motionType, show, forced, getComponentZOrder(component));            
        }
    }

    @Override
    public void showHideComponent(Object component ,MotionTypes motionType, boolean show, boolean forced,  int order) {     
        for (PicturePaneInterface picturePane : picturePanes) {
                        if (component==picturePane)
                            picturePane.setFullState(show,motionType, true);                        
        }
    }
    

    @Override
    public synchronized void updateSizeLocation() {             
        setFrameSizeRatios();
        calcSizeRatios();
        for (PicturePaneInterface picturePane : picturePanes) {            
            picturePane.updateSizeLocation();           
        }
        //repaint();
    }
    
       
private void calcSizeRatios()
{
    sizeRatioWidth=getSize().getWidth()/currBaseSize.getWidth();
    sizeRatioHeight=getSize().getHeight()/currBaseSize.getHeight();
        
}

    @Override
    public void setCompOrder(Object component, int order) {
        if (component instanceof Component)                        
            getLayeredPane().setComponentZOrder((Component) component, order);
        Collections.sort(picturePanes);            
    }
    

    @Override
    public void setFullState(boolean fullState, MotionTypes motionType, boolean checkMin) {
        this.prevFullState=this.fullState;
        this.fullState=fullState;
        showHideComponents(fullState, true, motionType);
    }
        
    

   @Override
    public boolean isFullState() {
        for (PicturePaneInterface picturePane : picturePanes) {
           if (!picturePane.isFullState())
               return false;
       }
      return fullState;
    }
    
   

    @Override
    public boolean isUnderConst() {
        for (PicturePaneInterface picturePane : picturePanes) {
            if (picturePane.isUnderConst())
                return true;
        }
        return false;
        
    }

    @Override
    public void update(Action action) {   
        System.out.println("updateFrame"+firstShow+isUnderConst());
        if (adminSwitch&&!isUnderConst()&&action==Action.ADMIN_DISABLED)
            {               
            if (adminEnabled)
                setSize(currBaseSize);                
            else                
                setCurrBaseSizeLocToCurrSizeLoc(isAdminEnabled());                                                                                                                                       
            updateSizeLocation();                               
            adminSwitch=false;
            }        
        if (!isUnderConst()&&action==Action.UNDERCONST_READY&&mainForm&&firstShow)               
            {            
            FormFactoryV1.createForm(FormTypes.INSTRUCTION_FORM, null, this, null);            
            firstShow=false;
            }
       // if (action==Action.DB_LOAD_FRAME)
          //  showState(true, null);
            
    }
    
    
    
    @Override
    public boolean isAdminEnabled() {        
        return adminEnabled;
    }

    @Override
    public double getSizeRatioWidth() {
        return sizeRatioWidth;
    }

    @Override
    public double getSizeRatioHeight() {
        return sizeRatioHeight;
    }

    @Override
    public int getComponentOrder(Object component) {
        System.out.println("getcomporderonFrame:"+getLayeredPane().getComponentZOrder((Component) component));
        if (component instanceof Component)
            return getLayeredPane().getComponentZOrder((Component) component);                    
        return 0;
    }

    @Override
    public int compareTo(PictureComponentInterface o) {
        return 0;
    }

    
    
private class PictContentPane extends Container{

        public PictContentPane() {
        }                
        
        @Override
        public void paint(Graphics g) {
            System.out.println("contentpanepaint");
            if (image!=null)
                g.drawImage(image.getImage(),1, 1, getWidth()-2, getHeight()-2, new Color(0,0,0,125), null);
            super.paint(g); //To change body of generated methods, choose Tools | Templates.
                
        }

            
        
    }
    

    @Override
    public void paint(Graphics g) {
        if (adminEnabled) 
                 setBackground(Color.YELLOW);
        else 
                setBackground(oldBackGroundColor);                            
        super.paint(g); //To change body of generated methods, choose Tools | Templates.                        
    }    
    
    @Override
    public void onClick(Object component) {
       // if (isAdminEnabled())            
            activateComponent(component,null);        
    }

    @Override
    public void activateComponent(Object component,MotionTypes motionType)
    {      
        if (component instanceof Component)            
            {
            setCompOrder(component, 0);
            repaint();
            }
    }
    
    
    @Override
    public Dimension getSize() {
        return getContentPane().getSize(); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public void setSize(Dimension d) {              
        getContentPane().setSize(d);        
        Dimension adjSize=new Dimension();
        adjSize.setSize(d.getWidth()*sizeRatioContPaneWidth, d.getHeight()*sizeRatioContPaneHeight);        
        super.setSize(adjSize); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void addButton(PictureComponentInterface component, int order) {
    }

    @Override
    public void addPictComponent(PictureComponentInterface component, int order) {    
    }    

    
    
    @Override
    public synchronized void setCurrBaseSizeLocToCurrSizeLoc(boolean adminEnabled) {      
        for (PicturePaneInterface picturePane : picturePanes) {
            picturePane.setCurrBaseSizeLocToCurrSizeLoc(adminEnabled);           
        }
        currBaseSize=getSize();                   
         setFrameSizeRatios();                                       
        calcSizeRatios();                    

    }
    
    private void setFrameSizeRatios()
    {
       frameSize=super.getSize();        
        this.sizeRatioContPaneWidth=frameSize.getWidth()/getSize().getWidth();
        this.sizeRatioContPaneHeight=frameSize.getHeight()/getSize().getHeight();          
    }

    
    
    @Override
    public void adjCurrBaseLocation(double addX, double addY) {
    }
    
    
   

    @Override
    public void setTitle(String title) {
        if (title!=null)
            super.setTitle(title); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public void adjCurrBaseSize(double addWidth, double addHeight, boolean adjustLoc) {                
    }

    @Override
    public Dimension getAdjCurrSize(boolean checkMin, boolean adjLocation, boolean calcWithMotion) {
        Dimension adjCurrSize=new Dimension();
        adjCurrSize.setSize(currBaseSize.getWidth()*sizeRatioWidth, currBaseSize.getHeight()*sizeRatioHeight);
        return adjCurrSize;
    }

    
       @Override
    public List<PicturePaneInterface> getPicturePanes() {
        return picturePanes;
    }

    @Override
    public PictureFrameGettersInt getGetters() {
        return pictureFrameGetters;       
    }

    @Override
    public void setGetters(Object getter) {
        this.pictureFrameGetters = (PictureFrameGettersInt) getter;        
    }

    
    

    @Override
    public void setAdminEnabled(boolean adminEnabled) {
        for (PicturePaneInterface picturePane : picturePanes) {                                 
                            picturePane.setAdminEnabled(adminEnabled);                                                            
        }        
        this.adminEnabled=adminEnabled;
    }

    @Override
    public List getPictureComponents() {
        return picturePanes;
    }
    
    

    @Override
    public Dimension getCurrBaseSize() {
        return currBaseSize;
    }

    @Override
    public Point getCurrBaseLocation() {
        return getLocation();
    }

    @Override
    public void showState(boolean forced, MotionTypes motionType) {
        for (PicturePaneInterface picturePane : picturePanes) {
            picturePane.showState(forced, motionType);
            //picturePane.setFullState(picturePane.isFullState(), motionType, true);
        }
    }

   @Override
    public void setImage(NamedImageInt image) {        
        this.image=image; 

    }

    @Override
    public void removeImage() {
        setImage(null);
        imagePath=null;
    }

    @Override
    public void setImagePath(String imagePath) {
        this.imagePath=imagePath;
    }
    
    

    @Override
    public NamedImageInt getImage() {
        return image;
    }

    

    @Override
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu=popupMenu;
        for (PicturePaneInterface picturePane : picturePanes) {
            picturePane.setPopupMenu(popupMenu);
            
        }
    }
    
    @Override
    public void onClick() {
        
    }

    @Override
    public void deActivate()
    {
      getRootPane().setBorder(null);                                         
    }
    
    @Override
    public void activate()
    {                 
        getRootPane().setBorder(BorderFactory.createLineBorder(getResizeBorderColor(),1));
    }
    
    @Override
    public Color getResizeBorderColor() {
        return resizeBorderColor;
    }

    @Override
    public void mouseEnterred() {
        if (isAdminEnabled())                                            
            {
            activate();
           }
    }

    @Override
    public void mouseExited() {
        if (isAdminEnabled())  
                  deActivate();
    }

    @Override
    public String getIconString() {
        return getTitle();
    }
   

    @Override
    public void paintPict(PaintRequestParams paintRequest) {
        setFullState(paintRequest.show, paintRequest.motionType, paintRequest.checkMin);
    }

    @Override
    public PicturePaneInterface getParentPane() {
        return this;
    }

    @Override
    public void Delete() {
                setVisible(false);
    }

    @Override
    public void setVisible() {
        setVisible(true);
    }

    @Override
    public void setInVisible() {
        setVisible(false);        
        
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    
    
    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public boolean isMinimzed() {
        return !isVisible();
    }

    @Override
    public boolean isResizeable() {
        return isResizable();
    }

    @Override
    public void setMainForm(boolean mainForm) {
        this.mainForm = mainForm;
    }
    
        @Override
    public void minimize() {
        setInVisible();                
    }

    @Override
    public void maximize() {
        setVisible();
    }

    
}
