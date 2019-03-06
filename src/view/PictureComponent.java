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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import view.enums.MotionTypes;
import view.interfaces.PictureComponentInterface;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.PictCompParams;
import view.interfaces.PictureComponentGettersInt;
import util.mapping.MapInterface;
import view.interfaces.NamedImageInt;
import view.recordtypeclasses.AnimParams;
import view.util.Observer;

/**
 *
 * @author Kuller Tamas
 */
public class PictureComponent extends AdaptPictJComponent{               
   
    protected PicturePaneInterface parentPane;
    protected PictureComponentGettersInt pictureComponentGetters;
    protected final static int MAX_PENDING_PAINT=6;     
    protected final static int DEFAULT_FONT_STLYE=Font.BOLD;
    protected final static int BORDER_SECURE_DIST=1;
    protected Color resizeBorderColor=Color.GREEN;
    protected double minWidth;     
    protected double minHeight;                             
    protected double minWidthMultiplier=0.1;     
    protected double minHeightMultiplier=0.1;                             
    
    
    protected NamedImageInt image=null;    
    protected String imagePath=null;    
    
    protected MotionTypes adminMotionType=MotionTypes.SIMPLE;
    protected MotionTypes defaultMotionType;
    protected MapInterface<MotionTypes,AnimParams> motionTypeMaps;    
    
    protected Dimension origSize;    
    protected Dimension currBaseSize;    
    protected Dimension lastSize;    
    protected Dimension fontRectSize;
    
    protected Point currBaseLocation;
    protected Point origLocation;
    protected Point lastLoc;
    
    protected double sizeParentRatioWidth=1;
    protected double sizeParentRatioHeight=1;   
    protected double sizeRatioWidth=1;
    protected double sizeRatioHeight=1;   
    protected double locRatioWidth=1;
    protected double locRatioHeight=1;   

    protected double motionRatio=1;    
    
    protected String iconString;  
    protected String toolTipText;      
    protected Point iconStringPos;
    private static final int DEF_FONT_SIZE=30;
    private int actFontSize;    
    private Timer timer;    
    private Timer timerPending;   
    protected boolean underConst=false;
    protected boolean shown=false;
    protected boolean adminEnabled;    
    protected boolean minimized=true;        
    protected boolean minOverrideMotion=false;        
            
    private List<PaintRequestParams> paintRequests;    
    protected JPopupMenu popupMenu;


    
    public PictureComponent(PictCompParams params) {
        //this.parentComponent=parentComponent;    
        //this.imagePath=params.imagePath;        
        this.currBaseLocation=new Point();        
        this.currBaseLocation.setLocation(params.getX(),params.getY());
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
        this.currBaseSize.setSize(params.getWidth(), params.getHeight());
        this.adminEnabled=params.adminEnabled;
        this.origSize=currBaseSize;                
        //this.setVisible(true);             
        this.paintRequests=new ArrayList<>();                       
        System.out.println("OrigWidth:"+currBaseSize.getWidth());        



    }

    public PictureComponent(PicturePaneInterface parent, PictCompParams params) {
        this(params);
        this.parentPane=parent;             
    }            

                                             
   
    @Override
    public void paintPict(PaintRequestParams paintRequest)             
            {           
                System.out.println("SHOWWWW:"+paintRequest.show+"Forced:"+paintRequest.forced);                                                                
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
                            parentPane.update(Observer.Action.UNDERCONST_READY_TO_PARENT);
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
                                //timer.stop();                                                                
                            //    System.out.println("Timer"+waitTime+"EDT?:"+SwingUtilities.isEventDispatchThread()+"step:"+stepTimer);                                                           
                                stepTimer=stepTimer+((showTimer)?1:-1);                                                                    
                                motionRatio=stepTimer/stepsTimer;                                
                                updateSizeLocation();                                                               
                                if (showTimer?stepsTimer==stepTimer:0==stepTimer)
                                    {
                                    timer.stop();  
                                    System.out.println("TIMERSTOP"+stepTimer);                                                                                       
                                    shown=showTimer; 
                                    minimized=!showTimer;
                                    paintRequests.remove(paintRequestTimer);  
                                    constructed();
                                    //underConst=false;
                                    }         
                            }                
                            });
                            timer.start();                                    
                            System.out.println("PICT:"+this.toString());                                                        
        }

    @Override
    public void update(Action action) {
        if (action==Action.UNDERCONST_READY)
            {
            }
    }
    
    
    
    
    @Override    
    public void paintComponent(Graphics grphcs) {                
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.             
        
            if (image!=null&&(!isMinimzed()||underConst))
                grphcs.drawImage(image.getImage(), BORDER_SECURE_DIST,BORDER_SECURE_DIST, getWidth()-2*BORDER_SECURE_DIST, getHeight()-2*BORDER_SECURE_DIST,new Color(0, 0, 0, 125), null);        
            Font font=grphcs.getFont();        
            grphcs.setFont(new Font(font.getFontName(),DEFAULT_FONT_STLYE , DEF_FONT_SIZE));        
            Dimension coverBox=new Dimension((int)getWidth(),(int)minHeight);
            this.actFontSize=FontFunctions.getActFontSize(coverBox,grphcs, DEF_FONT_SIZE, iconString);                       
            grphcs.setFont(new Font(font.getFontName(),DEFAULT_FONT_STLYE , this.actFontSize));                
            FontFunctions.drawHighlightedString(coverBox,grphcs, Color.BLUE, Color.WHITE, iconString);  
  //          }
    }

    @Override
    public void setLocation(Point p) {
          if (currBaseLocation==null)
                    currBaseLocation=p;          
        Point adjLocation=getAdjCurrLocation(currBaseLocation);
        super.setLocation(adjLocation); //To change body of generated methods, choose Tools | Templates.                
    }                
   
    
    
    protected Point getAdjCurrLocation(Point location)
    {            
            //updateParentSizeRatios();
            Point returnPoint=new Point();                        
            double adjX=(location.getX()<=BORDER_SECURE_DIST? BORDER_SECURE_DIST : location.getX())*getLocRatioWidth();
            double adjY=(location.getY()<=BORDER_SECURE_DIST? BORDER_SECURE_DIST : location.getY())*getLocRatioHeight();
            returnPoint.setLocation(adjX, adjY);           
            //returnPoint=getMaxLocation(returnPoint);
            returnPoint=getMinMaxLocation(returnPoint);
            return returnPoint;            
    }
    
    protected Point getMinMaxLocation(Point location)
    {
            Point returnPoint=new Point();                        
            double parentWidth=parentPane.getAdjCurrSize(true, true, false).getWidth();
            double parentHeight=parentPane.getAdjCurrSize(true, true, false).getHeight();            
            double adjX=location.getX()<=(parentWidth-minWidth-BORDER_SECURE_DIST)?location.getX():parentWidth-minWidth-BORDER_SECURE_DIST;
            double adjY=location.getY()<=(parentHeight-minHeight-BORDER_SECURE_DIST)?location.getY():parentHeight-minHeight-BORDER_SECURE_DIST;            
            //returnPoint.setLocation((adjX<=1)?1:adjX,(adjY<=1)?1:adjY);
            returnPoint.setLocation(adjX,adjY);
            return returnPoint;        
    }        
        
    @Override
    public void setSize(Dimension d) {  
                Dimension adjSize=getAdjCurrSize(true,true,true);                                 
                super.setSize(adjSize); //To change body of generated methods, choose Tools | Templates.                    
                //this.iconStringPos=new Point((int)minWidth/5,(int)minHeight/2);                       
                this.iconStringPos=new Point((int)minWidth/5,(int)minHeight/2);        
              //  if (!isVisible())
                  // setVisible(true);               
    }   
       
    
    @Override
    public Dimension getAdjCurrSize(boolean checkMin, boolean adjLocation, boolean calcWithMotion)
    {                 
            Dimension adjSize=new Dimension();              
            setMinDimensions(checkMin,adjLocation);
            if (adjLocation)
                setLocation(currBaseLocation); 
           adjSize.setSize(getSizeRatioWidth()*currBaseSize.getWidth(),getSizeRatioHeight()*currBaseSize.getHeight());
           if (!parentPane.isMinimzed())               
                adjSize=getMaxSize(adjSize,calcWithMotion);                                                                        
           if (checkMin)     
               adjSize=getMinSize(adjSize);                                                 
           adjSize.setSize(adjSize.getWidth()*(calcWithMotion?motionRatio:1), adjSize.getHeight()*(calcWithMotion?motionRatio:1));                                                        
           return adjSize;
    }

    
    protected void setMinDimensions(boolean checkMin, boolean adjLocation) {
        minWidth=parentPane.getAdjCurrSize(checkMin, adjLocation, false).getWidth()*minWidthMultiplier;
        minHeight=parentPane.getAdjCurrSize(checkMin, adjLocation, false).getHeight()*minHeightMultiplier;
   }

    
    protected Dimension getMaxSize(Dimension s,  boolean calcWithMotion)
    {
            Dimension parentPaneSize=parentPane.getAdjCurrSize(true, true,calcWithMotion);
            double maxWidth=(parentPaneSize.getWidth())-getAdjCurrLocation(currBaseLocation).getX()-BORDER_SECURE_DIST;            
            double maxHeight=(parentPaneSize.getHeight())-getAdjCurrLocation(currBaseLocation).getY()-BORDER_SECURE_DIST;//-getY();
            double newWidth=(s.getWidth()<=maxWidth)? s.getWidth():maxWidth;
            double newHeight=(s.getHeight()<=maxHeight)? s.getHeight():maxHeight;
            Dimension adjSize=new Dimension();
            adjSize.setSize(newWidth,newHeight);                                            
            return adjSize;
    }
    
    protected Dimension getMinSize(Dimension size)
    {
        double newWidth=size.getWidth();
        double newHeight=size.getHeight();
        newHeight=(newHeight<minHeight?minHeight:newHeight);
        newWidth=(newWidth<minWidth?minWidth:newWidth);        
        Dimension adjSize=new Dimension();
        adjSize.setSize(newWidth,newHeight);
        return adjSize;
        
    }
        
    @Override
    public void updateSizeLocation() {                
        //updateParentSizeRatios();
        if (shown||underConst) 
               {setSize(currBaseSize);   
                repaint();
               }
    }        
        
       @Override
    public double getSizeRatioWidth() {
        return sizeRatioWidth*parentPane.getSizeRatioWidth();
    }

    @Override
    public double getSizeRatioHeight() {
        return sizeRatioHeight*parentPane.getSizeRatioHeight();
    }

    public double getLocRatioWidth() {
        return locRatioWidth*parentPane.getSizeRatioWidth();
    }

    public double getLocRatioHeight() {
        return locRatioHeight*parentPane.getSizeRatioHeight();
    }        
        
    @Override
    public void setVisible(boolean aFlag) {
                super.setVisible(aFlag); //To change body of generated methods, choose Tools | Templates.
                setToolTipText(toolTipText);
    }
    
    @Override
    public synchronized void setCurrBaseSizeLocToCurrSizeLoc(boolean adminEnabled) {   
        //updateParentSizeRatios();                    
        Dimension adjSize=(adminEnabled)?getSize():getAdjCurrSize(true,false,false);                               
        System.out.println("parentsizeratioheight:"+parentPane.getSizeRatioHeight());
       System.out.println("adjsize:"+adjSize);
        sizeRatioHeight=sizeRatioWidth=1;        
        adjSize.setSize(adjSize.getWidth()/(adminEnabled?getSizeRatioWidth():1)/1,adjSize.getHeight()/(adminEnabled?getSizeRatioHeight():1)/1);       
        Point adjLocation=(adminEnabled)?getLocation():getAdjCurrLocation(currBaseLocation);
        locRatioWidth=locRatioHeight=1;
        adjLocation.setLocation(adjLocation.getX()/(adminEnabled?getSizeRatioWidth():1), adjLocation.getY()/(adminEnabled?getSizeRatioHeight():1));
        System.out.println("currbaseSizeprev:"+currBaseSize);
        currBaseLocation=adjLocation;
        currBaseSize=adjSize;                
        System.out.println("currbaseSize:"+currBaseSize);
     
    }
    
    
    
    
    @Override
    public synchronized void adjCurrBaseSize(double addWidth, double addHeight, boolean adjustLoc) {                
        if (!underConst)
            {
            underConst=true;
            Dimension adjDim=new Dimension();                
            Dimension origDim=new Dimension(); 
            origDim=getAdjCurrSize(true,adjustLoc,false);
            adjDim.setSize(origDim.getWidth()+addWidth, origDim.getHeight()+addHeight);                    
            if (adjustLoc)    
            {
                adjCurrBaseLocation(-addWidth,-addHeight);                                        
            }
                    
//            if (adjustLoc)    
//                    adjCurrBaseLocation((origDim.getWidth()-adjDim.getWidth()),origDim.getHeight()-adjDim.getHeight());                                        
            adjDim=getMaxSize(adjDim,false);                            
            adjDim=getMinSize(adjDim);        
            sizeRatioWidth=(adjDim.getWidth()/origDim.getWidth())*sizeRatioWidth;
            sizeRatioHeight=(adjDim.getHeight()/origDim.getHeight())*sizeRatioHeight;
            
            System.out.println("NewRatioWidth:"+sizeRatioWidth+" origwidth:"+origDim.getWidth()+" newWidth:"+adjDim.getWidth());            
            updateSizeLocation();
             constructed();

            }
    }

    
    @Override
    public synchronized void adjCurrBaseLocation(double addX, double addY) {
        //setRatiosParent();
        Point origLocation=getAdjCurrLocation(currBaseLocation);
        Point adjLocation=new Point();                            
        //Dimension parentPaneSize=parentPane.getAdjCurrSize(true, true,false);
        double adjX=origLocation.getX()+(addX);
        double adjY=origLocation.getY()+(addY);
        adjX=adjX>=BORDER_SECURE_DIST?adjX:BORDER_SECURE_DIST;
        adjY=adjY>=BORDER_SECURE_DIST?adjY:BORDER_SECURE_DIST;        
        adjLocation.setLocation(adjX,adjY);
        adjLocation=getMinMaxLocation(adjLocation);
        locRatioWidth=(adjLocation.getX()/origLocation.getX())*locRatioWidth;
        locRatioHeight=(adjLocation.getY()/origLocation.getY())*locRatioHeight;            
        updateSizeLocation();
        
    }
       
    
    @Override
    public boolean isAdminEnabled() {
        return adminEnabled;
    }

    @Override
    public synchronized void setAdminEnabled(boolean adminEnabled) {
        this.adminEnabled = adminEnabled;        
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
    public void deActivate()
    {
            setBorder(null);                                         
    }
    
    @Override
    public void activate()
    {setBorder(BorderFactory.createLineBorder(getResizeBorderColor()));
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
    public Color getResizeBorderColor() {
        return resizeBorderColor;
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
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu=popupMenu;
    }

    @Override
    public PicturePaneInterface getParentPane() {
        return parentPane;
    }

    @Override
    public void Delete() {
        parentPane.getPictureComponents().remove(this);
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
}
                   
