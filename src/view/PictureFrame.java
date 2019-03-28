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
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import view.Menu.JPopupMenuAdj;
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
import view.interfaces.AutoShape.AutoShapeCompResInt;


/**
 *
 * @author Kuller Tamas
 */
public class PictureFrame extends JFrameBaseFormAbs implements PictureFrameInterface {
    private List<Observer> observers;
   protected List<PicturePaneInterface> picturePanes;
   protected List<PicturePaneInterface> picturePanesUnderConst;
   
   protected PictureFrameGettersInt pictureFrameGetters; 
    private boolean adminEnabled;
   private boolean fullState;

   private Dimension currBaseSize;   
   private Dimension origSize;
   protected Dimension frameSize;
   protected Color oldBackGroundColor;
   protected double sizeRatioContPaneWidth;
   protected double sizeRatioContPaneHeight;
   protected double sizeRatioWidth;
    protected double sizeRatioHeight;   
    protected JPopupMenuAdj popupMenu;
    protected Border activeBorder=BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.PINK); 
    protected NamedImageInt image=null;    
    protected String imagePath;
    private boolean activated=true;
    protected double minSizeRatioHeight=1;

    private boolean adminSwitch=false;
    private int sizeRefreshCounter=0;
    private PictContentPane pictContentPane;
    private boolean mainForm=false;
    private boolean firstShow=true;
    private boolean dbLoadReady=false;
    private boolean adminAdjLayout=false;
    private int defWindowCloseOp=0;
   
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
        this.picturePanesUnderConst=new LinkedList<>();
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
            public synchronized void componentResized(ComponentEvent e) {                
                super.componentResized(e); //To change body of generated methods, choose Tools | Templates.                                                                                          
                updateSizeLocation();                             
            }
            });                    

        JFrameBaseFormAbs saveThis=this;
        
        this.addWindowListener(new WindowAdapter() {            
            @Override
            public void windowClosed(WindowEvent e) {
                activated=false;                                
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.                
            }

            @Override
            public synchronized void windowClosing(WindowEvent e) {
                if (adminAdjLayout)
                    {
                    String question="Did you save the changes you may have done to "+saveThis.getTitle()+((mainForm)?" and other Frames?":" Frame?");
                    String title="Are you sure to close "+((mainForm)?"the Application?":saveThis.getTitle()+" Frame?");                    
                    if (JOptionPane.showConfirmDialog(saveThis, question,title, JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)                                
                        saveThis.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);                         
                    }
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.                                            
            }

            @Override
            public void windowOpened(WindowEvent e) {                
                super.windowOpened(e); //To change body of generated methods, choose Tools | Templates.                
                updateSizeLocation();                
            }

            @Override
            public void windowActivated(WindowEvent e) {
                saveThis.setDefaultCloseOperation(defWindowCloseOp);                         
                super.windowActivated(e); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }
    
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
            updateSizeLocation();            
            //showHideComponent(component, null,true,true, false, getComponentZOrder(component));            
            }
    }
    
   @Override
    public void addPictPane(PicturePaneInterface pictPane,int order)
    {
        addComponent(pictPane, order);
        this.picturePanes.add(pictPane);  
        this.picturePanesUnderConst.add(pictPane);
       // pictPane.setVisible();
        //showHideComponents((fullState)?true:false, true);
    }
    
    @Override
    public void removePictPane(PicturePaneInterface picturePane) {
        removeComponent(picturePane);        
        this.picturePanes.remove(picturePane);  
        this.picturePanesUnderConst.remove(picturePane);
        Collections.sort(picturePanes);
    }
    
    public void removeComponent(Object component)
    {
        if (component instanceof Component)        
        {
            Component comp=(Component) component;
            remove(comp);
            JLayeredPane layeredPane=getLayeredPane();                            
            layeredPane.remove(comp); 
            updateSizeLocation();  
        }
    }

    @Override
    public void addButton(PictureComponentInterface component, int order) {
        //not yet implemented
    }

    @Override
    public void addPictComponent(PictureComponentInterface component, int order) {            
        //not yet implemented
    }    

    @Override
    public void removePictComponent(PictureComponentInterface component) {    
        //not yet implemented
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
        adminAdjLayout=true;
        update(Action.ADMIN_DISABLED,this);
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
        repaint();
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
        this.fullState=fullState;
        showHideComponents(fullState, true, motionType);
    }
    
    @Override
    public void update(Action action, Object subject) {   
        System.out.println("updateFrame"+firstShow+isUnderConst());
        if (action==Action.ADMIN_DISABLED&&!isUnderConst()&&adminSwitch)
            {               
            if (adminEnabled)
                setSize(currBaseSize);                
            else                
                setCurrBaseSizeLocToCurrSizeLoc(isAdminEnabled());                                                                                                                                       
            updateSizeLocation();                               
            adminSwitch=false;
            }        
        dbLoadReady=(action==Action.DB_LOAD_FRAME)?true:dbLoadReady;
        if (firstShow)
           {
           if (action==Action.UNDERCONST_READY &&subject instanceof PicturePaneInterface)
                {
                picturePanesUnderConst.remove((PicturePaneInterface)subject);
                System.out.println("paneready-get:"+((PicturePaneInterface) subject).getIconString()+dbLoadReady);                             
                firstShowActions();
                }
           if (action==Action.FRAME_READY)
               firstShowActions();           
           }
    }
    
   public void firstShowActions()
    {
        if (!isUnderConst()&&picturePanesUnderConst.isEmpty()&&dbLoadReady)  
            {
            if (mainForm)
                FormFactoryV1.createForm(FormTypes.INSTRUCTION_FORM, null, this, null);            
            firstShow=false;                    
            popupMenu.setMenuActive(true);
            }        
    }

    public void setDbLoadReady(boolean dbLoadReady) {
        this.dbLoadReady = dbLoadReady;
    }
    
    @Override
    public void setMainForm(boolean mainForm) {
        this.mainForm = mainForm;
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
    public boolean isAdminEnabled() {        
        return adminEnabled;
    }

    @Override
    public int getComponentOrder(Object component) {        
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
            if (image!=null)
                g.drawImage(image.getImage(),1, 1,(int) (getSize().getWidth()-2), (int) (getSize().getHeight()-2), new Color(0,0,0,125), null);                    
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
    public int getActivateClickCount() {
        return 1;
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
    public void setTitle(String title) {
        if (title!=null)
            super.setTitle(title); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public Dimension getAdjCurrSize(boolean calcWithMotion) {
        Dimension adjCurrSize=new Dimension();
        adjCurrSize.setSize(currBaseSize.getWidth()*sizeRatioWidth, currBaseSize.getHeight()*sizeRatioHeight);
        return adjCurrSize;
    }

    @Override
    public Point getAdjCurrLocation() {
        return getLocation();
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
    public JPopupMenuAdj getPopupMenu() {
        return popupMenu;
    }
    
    @Override
    public void setPopupMenu(JPopupMenuAdj popupMenu) {
        this.popupMenu=popupMenu;
        for (PicturePaneInterface picturePane : picturePanes) {
            picturePane.setPopupMenu(popupMenu);
        }
    }
    
    @Override
    public void onClick() {
        //JFrame manages it
    }

    @Override
    public void deActivate()
    {
      getRootPane().setBorder(null);                                         
    }
    
    @Override
    public void activate()
    {                 
    getRootPane().setBorder(activeBorder);
    }
    
    
    @Override
    public void mouseEnterred() {
        if (isAdminEnabled())                                            
            activate();
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
    public void setDefaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(operation); //To change body of generated methods, choose Tools | Templates.
        if (defWindowCloseOp==0)
            defWindowCloseOp=operation;
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
    public void minimize() {
        setInVisible();                
    }

    @Override
    public void maximize() {
        setVisible();
    }    

    @Override
    public AutoShapeCompResInt getAutoShapeCompRes() {
        return null; // JFrame manages shaping
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
    public Dimension getMinSize(Dimension size) {
        return getMinimumSize();
    }
    
    
}
