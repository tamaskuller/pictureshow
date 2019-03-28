/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.util.FontFunctions;
import view.adapter.AdaptPictJComponent;
import view.recordtypeclasses.PaintRequestParams;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import view.enums.MotionTypes;
import view.interfaces.PictureComponentInterface;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.PictCompParams;
import view.interfaces.PictureComponentGettersInt;
import util.mapping.MapInterface;
import view.Menu.JPopupMenuAdj;
import view.interfaces.NamedImageInt;
import view.recordtypeclasses.AnimParams;
import view.util.Observer;
import view.interfaces.AutoShape.AutoShapeCompResInt;

/**
 *
 * @author Kuller Tamas
 */
public class PictureComponent extends AdaptPictJComponent{               
   
    protected PicturePaneInterface parentPane;    
    protected PictureComponentGettersInt pictureComponentGetters;
    protected AutoShapeCompResInt autoShapeComponentRes;    
    //protected AutoShapeCompGettersInt autoShapeCompGet;    
    
    protected final static int MAX_PENDING_PAINT=6;     
    protected final static int DEFAULT_FONT_STLYE=Font.BOLD;
    protected final static int BORDER_SECURE_DIST=1;    
    
    protected Border activeBorder=BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.PINK);    
    protected Border normalBorder=BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.GRAY);
    protected Border currentBorder=normalBorder;
    protected Color bckColor=Color.WHITE;
    protected Color fontColor=Color.BLACK;    
   
    protected NamedImageInt image=null;    
    protected String imagePath=null;    
    
    protected MotionTypes adminMotionType=MotionTypes.SIMPLE;
    protected MotionTypes minMaxMotionType=MotionTypes.FAST_FLOWING;    
    protected MotionTypes defaultMotionType;    
    protected MapInterface<MotionTypes,AnimParams> motionTypeMaps;    
    
    protected Dimension origSize;        
    protected Dimension fontRectSize;
    protected Dimension currBaseSize;    
    
    protected Point currBaseLocation;    
    protected Point origLocation;                                        
        
    protected String iconString;  
    protected String toolTipText;      
    protected Point iconStringPos;
    protected double icon_x_indent_ratio=0.1;
    protected double icon_y_indent_ratio=0.1;
            
    private Timer timer;    
    private Timer timerPending;   
    protected boolean underConst=false;
    protected boolean shown=false;
    protected boolean adminEnabled;    
    protected boolean minimized=true;        
    protected boolean activated=false;
    protected boolean firstShow=true;
    protected int activateClickCount=1;
            
    private List<PaintRequestParams> paintRequests;    
    protected JPopupMenuAdj popupMenu;

    public PictureComponent(PictCompParams params) {
        //this.parentComponent=parentComponent;    
        //this.imagePath=params.imagePath;        
        this.currBaseLocation=new Point();        
        currBaseLocation.setLocation(params.getX(),params.getY());
        this.origLocation=currBaseLocation;
        //this.image=params.image;      
        this.fontRectSize=new Dimension();
        this.image=params.image;
        this.imagePath=params.imagePath;
        this.defaultMotionType=params.defaultMotionType;
        this.motionTypeMaps=params.motionTypeMaps;
        this.iconString=params.iconString;
        this.toolTipText=params.toolTipText;
        this.currBaseSize=new Dimension();
        currBaseSize.setSize((params.getWidth()),params.getHeight());        
        System.out.println("size-width:"+params.getWidth());
        this.adminEnabled=params.adminEnabled;
        this.origSize=currBaseSize;                        
        //this.setVisible(true);             
        this.paintRequests=new ArrayList<>();                       
        System.out.println("OrigWidth:"+currBaseSize.getWidth());          
    }

    public PictureComponent(PicturePaneInterface parent, PictCompParams params) {
        this(params);
        this.parentPane=parent;             
        this.autoShapeComponentRes=new AutoShapeCompRes(parentPane, this,BORDER_SECURE_DIST, currBaseSize, currBaseLocation, 0.2, 0.1);
    }            
    
    @Override
    public void setVisible(boolean aFlag) {
                super.setVisible(aFlag); //To change body of generated methods, choose Tools | Templates.
                setToolTipText(toolTipText); 
    }
        
    @Override
    public void paintPict(PaintRequestParams paintRequest)             
            {                       
                if (firstShow)
                    autoShapeComponentRes.firstShowActions();
                firstShow=false;                    
                if ((paintRequests.isEmpty()&&!shown==paintRequest.show)||paintRequest.forced)
                    paintRequests.add(paintRequest);
                if (timerPending==null)
                        {                        
                        timerPending=new Timer(20,new ActionListener() {                        
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                           if (!paintRequests.isEmpty())
                            {
                             if (!underConst&&!shown==paintRequests.get(0).show)                                                                                                                                 
                                animator(paintRequests.get(0));                                                                
                             if (!underConst&&!(!shown==paintRequests.get(0).show))
                                paintRequests.remove(0);
                            }
                           else
                          {
                            timerPending.stop();
                            timerPending=null;                                                  
                            parentPane.update(Observer.Action.UNDERCONST_READY, this);
                           //    update(Observer.Action.UNDERCONST_READY);
                           }
                          };
                        });                        
                        timerPending.start();                        
                        }                                                     
        }
    
   protected void animator(PaintRequestParams paintRequest) {
                    underConst=true;
                    
                    MotionTypes motionType=((paintRequest.motionType==null)?defaultMotionType:paintRequest.motionType);
                    int steps=motionTypeMaps.get(motionType).getSteps();                    
                    int waitTime=motionTypeMaps.get(motionType).getWaitTime();
                    int step=(paintRequest.show?0:steps);                         
                    //Component copyThis=this;                   
                    parentPane.setCompOrder(this, paintRequest.order);
                    //System.out.println("Steps"+steps+" step:"+step+" show::"+paintRequest.show);                
                    timer=new Timer(waitTime,new ActionListener() {
                                double stepsTimer=steps;
                                double stepTimer=step;
                                boolean showTimer=paintRequest.show;                                
                                int orderTimer=paintRequest.order;     
                                PaintRequestParams paintRequestTimer=paintRequest;                                                                                                
                                
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                            //    System.out.println("Timer"+waitTime+"EDT?:"+SwingUtilities.isEventDispatchThread()+"step:"+stepTimer);                                                           
                                stepTimer=stepTimer+((showTimer)?1:-1);                                                                    
                                setMotionRatio(stepTimer/stepsTimer);                                
                                updateSizeLocation();                                                               
                                if (showTimer?stepsTimer==stepTimer:0==stepTimer)
                                    {
                                    timer.stop();                                      
                                    shown=showTimer; 
                                    minimized=!showTimer;
                                    paintRequests.remove(paintRequestTimer);                                      
                                    constructed();                                                                        
                                    }         
                            }                
                            });
                            timer.start();                                    
                            System.out.println("PICT:"+this.toString());                                                        
        }

    public void setMotionRatio(double motionRatio) {        
        autoShapeComponentRes.setMotionRatio(motionRatio);
    }    
   
    @Override    
    public void paintComponent(Graphics grphcs) {                
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.             
        Graphics2D g=(Graphics2D) grphcs.create();                         
        if (!isMinimzed()||underConst||shown)
            if (image!=null)
                g.drawImage(image.getImage(), BORDER_SECURE_DIST,BORDER_SECURE_DIST, getWidth()-2*BORDER_SECURE_DIST, getHeight()-2*BORDER_SECURE_DIST,new Color(0, 0, 0, 125), null);        
            else if (bckColor!=null)
                {g.setColor(bckColor);
                g.fillRect(1, 1, getWidth()-2, getHeight()-2);
                }
        Dimension coverBox=new Dimension((int) ((getWidth()<autoShapeComponentRes.getMinWidth())?getWidth():autoShapeComponentRes.getMinWidth()),(int) autoShapeComponentRes.getMinHeight());
        this.iconStringPos=FontFunctions.drawHighlightedString(coverBox,grphcs,iconString,DEFAULT_FONT_STLYE,fontColor , bckColor,icon_x_indent_ratio, icon_y_indent_ratio);    
  //          }
    }

    @Override
    public void setLocation(Point p) {
//          if (currBaseLocation==null)
//                    currBaseLocation=p;          
        Point adjLocation=getAdjCurrLocation();
        super.setLocation(adjLocation); //To change body of generated methods, choose Tools | Templates.                
    }                
        
        
    @Override
    public void setSize(Dimension d) {          
                Dimension adjSize=getAdjCurrSize(true);                                 
                setLocation(null); 
                super.setSize(adjSize); //To change body of generated methods, choose Tools | Templates.                    
                //this.iconStringPos=new Point((int)minWidth/5,(int)minHeight/2);        
    }   

    
    @Override
    public Dimension getAdjCurrSize(boolean calcWithMotion) {
        return autoShapeComponentRes.getAdjCurrSize(calcWithMotion);
    }

    @Override
    public Point getAdjCurrLocation() {
        return autoShapeComponentRes.getAdjCurrLocation();
    }
    
    @Override
    public void setCurrBaseSizeLocToCurrSizeLoc(boolean adminEnabled) {
        autoShapeComponentRes.setCurrBaseSizeLocToCurrSizeLoc(adminEnabled);
    }     
    
    @Override
    public double getSizeRatioWidth() {
        return autoShapeComponentRes.getSizeRatioWidth();
    }

    @Override
    public double getSizeRatioHeight() {
        return autoShapeComponentRes.getSizeRatioHeight();
    }

    @Override
    public Dimension getMinSize(Dimension size) {
        return autoShapeComponentRes.getMinSize(size);
    }        
    
    @Override
    public void updateSizeLocation() {                
        //if (shown||underConst) 
        //       {
                setSize(null);
                repaint();
//               }
    }        
        
    
//    @Override
//    public synchronized void adjCurrBaseSize(double addWidth, double addHeight, boolean AdjustLoc) {                
//        if (!underConst)
//            {
//            underConst=true;
//            autoShapeComponentRes.adjCurrBaseSize(addWidth, addHeight, AdjustLoc);            
//            updateSizeLocation();
//            constructed();
//            }
//    }
    
    @Override
    public boolean isAdminEnabled() {
        return adminEnabled;
    }

    @Override
    public void setAdminEnabled(boolean adminEnabled) {        
        this.adminEnabled = adminEnabled;             
        currentBorder();        
    }
   
    @Override
    public boolean adminSwitched() {
        setAdminEnabled(!adminEnabled);        
        return adminEnabled;        
    }

    @Override
    public void onClick() {        
        if (!isUnderConst()&&!parentPane.isUnderConst())
                parentPane.onClick(this);            
    }

    @Override
    public int getActivateClickCount() {
        return activateClickCount;
    }
    
    
    
    @Override
    public void deActivate()
    {                   
        activated=false;
        currentBorder();
    }
    
    @Override
    public void activate()
    {   
        activated=true;
        currentBorder=activeBorder;        
        currentBorder();
    }
    
    protected void currentBorder()
    {
        currentBorder=(activated)? currentBorder: (isMinimzed()&&!underConst)? null: normalBorder;
        setBorder(currentBorder);       
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
    public void minimize() {
        paintPict(new PaintRequestParams(false, true, minMaxMotionType, parentPane.getComponentOrder(this), false));
    }

    @Override
    public void maximize() {
        paintPict(new PaintRequestParams(true, true, minMaxMotionType, parentPane.getComponentOrder(this), false));
    }

    @Override
    public void mouseEnterred() {
        if (isAdminEnabled())                                            
            {
            parentPane.activate();        
            activate();        
           }
    }

    @Override
    public void mouseExited() {
        if (isAdminEnabled())  
                {
                    parentPane.deActivate();        
                    deActivate();
                }
    }

    @Override
    public Object getGetters() {
        return this.pictureComponentGetters;
    }

    @Override
    public void setGetters(Object getter) {
        this.pictureComponentGetters=(PictureComponentGettersInt) getter;
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
    public JPopupMenuAdj getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void setPopupMenu(JPopupMenuAdj popupMenu) {
        this.popupMenu=popupMenu;
    }

    @Override
    public PicturePaneInterface getParentPane() {
        return parentPane;
    }

    @Override
    public void Delete() {
        parentPane.removePictComponent(this);
        setVisible(false);
    }

    @Override
    public String getIconString() {
        return iconString;
    }

    @Override
    public NamedImageInt getImage() {
        return image;
    }

    @Override
    public boolean isUnderConst() {
        return underConst||!paintRequests.isEmpty();
    }
    
    protected void constructed()
    {                    
        underConst=false;        
        currentBorder();
    }

    @Override
    public boolean isResizeable() {
        return !isMinimzed()&&!isUnderConst()&&!parentPane.isMinimzed();
    }

    @Override
    public boolean isMinimzed() {
        return minimized;
    }

    @Override
    public int compareTo(PictureComponentInterface o) {
        return ((Integer) this.getParentPane().getComponentOrder(this)).compareTo(((Integer) o.getParentPane().getComponentOrder(o)));       
    }

    @Override
    public AutoShapeCompResInt getAutoShapeCompRes() {
        return autoShapeComponentRes;
    }
    
}
                   
