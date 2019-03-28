/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;

import controller.DB.exceptions.SystemRecordCannotBeDeleted;
import com.mysql.cj.result.LocalDateTimeValueFactory;
import controller.DB.exceptions.IllegalOrphanException;
import controller.DB.exceptions.NonexistentEntityException;
import view.enums.FormTypes;
import view.enums.PictCompTypes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.PictureButtonTable;
import model.PictureComponentTable;
import model.PictureFrameTable;
import model.PicturePaneTable;
import model.exceptions.PreexistingEntityException;
import view.FormFactoryV1;
import controller.maps.AnimTypeMap;
import view.PictCompFactV1;
import view.PictureButton;
import view.PictureFrame;
import view.enums.MotionTypes;
import view.interfaces.PictureComponentInterface;
import view.interfaces.PictureFrameInterface;
import view.interfaces.PicturePaneInterface;
import view.interfaces.PicturePaneGettersInt;
import view.interfaces.PictureFrameGettersInt;
import view.interfaces.PictureComponentGettersInt;
import enums.MapFactoryTypes;
import controller.maps.MapCreatorFactV1;
import controller.maps.MapFactoryAbs;
import enums.DataSourceTypes;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.ImageFactoryV1;
import model.Logins;
import util.StaticEnvironmentParams;
import util.FileOperations;
import view.interfaces.AttachedGettersInt;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.recordtypeclasses.PictCompParams;
import view.util.Observer;
import view.util.Subject;

/**
 *
 * @author Tamas Kuller
 */
public final class PictDBActionsV1 extends AbsPictDBActionsPaneComp implements PictDBActionsInt, Subject {    
    static PictureFrameTableJpaController pictureFrameCont;
    static PicturePaneTableJpaController picturePaneCont;
    static PictureComponentTableJpaController pictureCompCont;
    static PictureButtonTableJpaController pictureButtonCont;
    static LoginsJpaController loginsCont;
    static PictDBActionsV1 instance;
    static List<Observer> observers;
            
    static {
        instance=new PictDBActionsV1();
    }
    
    private PictDBActionsV1() {                
    EntityManagerFactory factory=Persistence.createEntityManagerFactory("ShowPicturePU");                   
    pictureFrameCont=new PictureFrameTableJpaController(factory);        
    picturePaneCont=new PicturePaneTableJpaController(factory);        
    pictureCompCont=new PictureComponentTableJpaController(factory);        
    pictureButtonCont=new PictureButtonTableJpaController(factory);
    loginsCont=new LoginsJpaController(factory);
    observers=new ArrayList<>();
    }        
          
    
    public static PictDBActionsV1 getInstance()
    {
        return instance;
    }
    
    @Override
    public synchronized void saveFrame(PictureFrameInterface pictureFrameInterface, String name)throws PreexistingEntityException
    {                
        PictureFrameGettersInt pictureFrameGetters=(PictureFrameGettersInt) pictureFrameInterface.getGetters();
        String oldTitle=pictureFrameGetters.getTitle();
        pictureFrameInterface.setTitle(name);        
        System.out.println("oldtitle"+oldTitle+"getTitle"+pictureFrameGetters.getTitle());
        System.out.println(oldTitle.equals(pictureFrameGetters.getTitle()));
        //if (!oldTitle.equals(pictureFrameGetters.getTitle())&&
        if (!pictureFrameGetters.getTitle().isEmpty())
            {                       
                System.out.println("save");
            PictureFrameTable pictureFrameTable=new PictureFrameTable();
                setFrameFields(pictureFrameTable, pictureFrameGetters);
            try {
                saveFrameRecord(pictureFrameTable, pictureFrameInterface,pictureFrameGetters, oldTitle);
                for (PicturePaneInterface picturePane : pictureFrameInterface.getPicturePanes()) {
                    savePane(picturePane, pictureFrameTable, null);            
                    }
            }             
            catch (PreexistingEntityException ex)
            {
                throw ex;
//                System.out.println(pictureFrameTable.getName()+" - "+ex.getMessage());
            }
            catch (Exception ex) {
                Logger.getLogger(PictDBActionsV1.class.getName()).log(Level.SEVERE, null, ex);
            }
            notifyObserver(Observer.Action.DB_SAVE_FRAME);

          }
        
        
    }
    
    private void saveFrameRecord(PictureFrameTable pictureFrameTable,PictureFrameInterface pictureFrameInterface,PictureFrameGettersInt pictureFrameGetters, String oldTitle) throws Exception
    {
        try {            
            pictureFrameCont.create(pictureFrameTable);
        } 
        catch (Exception ex)
                  {                      
                  Query query=pictureFrameCont.getEntityManager().createNamedQuery("PictureFrameTable.findByName");
                  query.setParameter("name", pictureFrameGetters.getTitle());
                  if (!query.getResultList().isEmpty())//&&!oldTitle.equals(pictureFrameGetters.getTitle()))                      
                        {                        
                        String title=pictureFrameGetters.getTitle();
                        pictureFrameInterface.setTitle(oldTitle);                                                                        
                        throw new PreexistingEntityException("Pictureframe " + title + " already exists. ", ex);
                        }
                  else
                    throw ex; 
                  }               
    }
    
    
    private void setFrameFields(PictureFrameTable pictureFrameTable,PictureFrameGettersInt pictureFrameGetters)
    {
            
            pictureFrameTable.setAdminEnabled(booleanToShort(pictureFrameGetters.isAdminEnabled()));            
//            pictureFrameTable.setCurrBaseSizeHeight(pictureFrameGetters.getCurrBaseSize().getHeight());
//            pictureFrameTable.setCurrBaseSizeWidth(pictureFrameGetters.getCurrBaseSize().getWidth());        
            pictureFrameTable.setFrameSizeHeight(pictureFrameGetters.getFrameSize().getHeight());
            pictureFrameTable.setFrameSizeWidth(pictureFrameGetters.getFrameSize().getWidth());
            pictureFrameTable.setFullState(booleanToShort (pictureFrameGetters.isFullState()));                
            pictureFrameTable.setName(pictureFrameGetters.getTitle());            
            pictureFrameTable.setSaveDate(getDateNow());
            if (pictureFrameGetters.getImage()!=null)
               {       
                pictureFrameTable.setImageName(pictureFrameGetters.getImage().getImageName());
                FileOperations.putFile(pictureFrameGetters.getImagePath(),StaticEnvironmentParams.REL_PATH_TO_PICT+pictureFrameGetters.getImage().getImageName());            
                }
            //pictureframe.setOldBackGroundColor(pictureFrame.getOldBackGroundColor().toString());
            pictureFrameTable.setSizeRatioContPaneHeight(pictureFrameGetters.getSizeRatioContPaneHeight());
            pictureFrameTable.setSizeRatioContPaneWidth(pictureFrameGetters.getSizeRatioContPaneWidth());
            //pictureframe.setSizeRatioHeight(pictureFrameInterface.getSizeRatioHeight());
            //pictureframe.setSizeRatioWidth(pictureFrameInterface.getSizeRatioWidth());
            
    }

    
    @Override
    public void savePane(PicturePaneInterface picturePaneInterface,PictureFrameTable parentPictureFrameTable, PicturePaneTable parentPicturePaneTable) {
            PicturePaneTable picturePaneTable=new PicturePaneTable();
            setPaneFields(picturePaneInterface, parentPictureFrameTable, parentPicturePaneTable,picturePaneTable);
            picturePaneCont.create(picturePaneTable);                 
            if (picturePaneInterface instanceof PictureComponentInterface)
                {
                PictureComponentInterface pictureComponentInterface=(PictureComponentInterface) picturePaneInterface;
                saveComponent(pictureComponentInterface, picturePaneTable, true,null);                                                           
                }            
            
            for (AttachedGettersInt pictureComponent : picturePaneInterface.getPictureComponents()) {                                               
                if (pictureComponent instanceof PicturePaneInterface)                    
                    savePane((PicturePaneInterface) pictureComponent, null, picturePaneTable);
                else
                    if (pictureComponent instanceof PictureButton)
                        {
                        PictureButtonTable pictureButtonTable=saveButton(picturePaneTable);
                        saveComponent(pictureComponent, picturePaneTable, false,pictureButtonTable);                        
                        }
                    else
                        saveComponent(pictureComponent, picturePaneTable,false,null);
            }            
        }
            
      
    private void setPaneFields(PicturePaneInterface picturePaneInterface,PictureFrameTable parentPictureFrameTable, PicturePaneTable parentPicturePaneTable,PicturePaneTable picturePaneTable )
    {
        PicturePaneGettersInt picturePaneGetters=(PicturePaneGettersInt) picturePaneInterface.getGetters();
        picturePaneTable.setFullState(booleanToShort(picturePaneGetters.isFullState()));        
            picturePaneTable.setReorderMotionType(picturePaneGetters.getReOrderMotionType().toString());
            picturePaneTable.setAdminEnabled(booleanToShort (picturePaneGetters.isAdminEnabled()));        
            if (parentPictureFrameTable!=null)
                picturePaneTable.setParentframeID(parentPictureFrameTable);
            if (parentPicturePaneTable!=null)
                picturePaneTable.setParentpaneID(parentPicturePaneTable);            
    }

    @Override
    public PictureComponentTable saveComponent(AttachedGettersInt pictureComponentInterface, PicturePaneTable parentPicturePaneTable, boolean isPaneComponent, PictureButtonTable pictureButtonTable) {
            PictureComponentTable pictureComponentTable=new PictureComponentTable();            
            setComponentFields(pictureComponentTable, pictureComponentInterface, parentPicturePaneTable, isPaneComponent, pictureButtonTable);                      
            pictureCompCont.create(pictureComponentTable);                    
            return pictureComponentTable;
                
    }

    private PictureButtonTable saveButton(PicturePaneTable picturePaneTable) {
        PictureButtonTable pictureButtonTable=new PictureButtonTable();
        pictureButtonTable.setParentpaneID(picturePaneTable);        
        pictureButtonCont.create(pictureButtonTable);        
        return pictureButtonTable;
    }
    
    private void setComponentFields(PictureComponentTable pictureComponentTable, AttachedGettersInt pictureComponentInterface, PicturePaneTable picturePaneTable, boolean isPaneComponent, PictureButtonTable pictureButtonTable)
    {
        PictureComponentGettersInt pictureComponentGet=(PictureComponentGettersInt) pictureComponentInterface.getGetters();
        pictureComponentTable.setAdminEnabled(booleanToShort (pictureComponentGet.isAdminEnabled()));
        pictureComponentTable.setCurrBaseSizeHeight(pictureComponentGet.getCurrBaseSize().getHeight());
        pictureComponentTable.setCurrBaseSizeWidth(pictureComponentGet.getCurrBaseSize().getWidth());
        pictureComponentTable.setCurrbaselocationX(pictureComponentGet.getCurrBaseLocation().getX());
        pictureComponentTable.setCurrbaselocationY(pictureComponentGet.getCurrBaseLocation().getY());        
       // pictureComponentTable.setDefaultMotionType(pictureComponentGet.getMotionTypeMaps().toString());
        pictureComponentTable.setIconString(pictureComponentGet.getIconString());
      //  pictureComponentTable.setImagePath("");
        pictureComponentTable.setMinHeight(pictureComponentGet.getMinHeight());
        pictureComponentTable.setMinWidth(pictureComponentGet.getMinWidth());  
//        pictureComponentTable.setMotionRatio(pictureComponentGet.getMotionRatio());
        pictureComponentTable.setMotiontypemapID(0);
        pictureComponentTable.setOrigSizeHeight(pictureComponentGet.getOrigSize().getHeight());
        pictureComponentTable.setOrigSizeWidth(pictureComponentGet.getOrigSize().getWidth());
        pictureComponentTable.setOriglocationX(pictureComponentGet.getOrigLocation().getX());
        pictureComponentTable.setOriglocationY(pictureComponentGet.getOrigLocation().getY());
       // pictureComponentTable.setResizeBorderColor(pictureComponentInterface.getResizeBorderColor().toString());
        pictureComponentTable.setShown(booleanToShort(pictureComponentGet.isShown()));        
        //pictureComponentTable.setSizeParentRatioHeight(pictureComponentGet.getSizeParentRatioHeight());
      //  pictureComponentTable.setSizeParentRatioWidth(pictureComponentGet.getSizeParentRatioWidth());
        pictureComponentTable.setSizeRatioHeight(pictureComponentGet.getSizeRatioHeight());
        pictureComponentTable.setSizeRatioWidth(pictureComponentGet.getSizeRatioWidth());
        pictureComponentTable.setToolTipText(pictureComponentGet.getToolTipText());        
        pictureComponentTable.setParentpaneID(picturePaneTable);        
        if (pictureComponentGet.getImage()!=null)
            {
                System.out.println("savepictimage"+pictureComponentGet.getImagePath());
            pictureComponentTable.setImageName(pictureComponentGet.getImage().getImageName());        
            FileOperations.putFile(pictureComponentGet.getImagePath(),StaticEnvironmentParams.REL_PATH_TO_PICT+pictureComponentGet.getImage().getImageName());                            
            }
        
        pictureComponentTable.setIsPaneComponent(booleanToShort(isPaneComponent));
        pictureComponentTable.setIsButtonComponent(booleanToShort(pictureButtonTable!=null));
        if (pictureButtonTable!=null)
            pictureComponentTable.setButtonId(pictureButtonTable);
        pictureComponentTable.setPosition(pictureComponentGet.getOrder());
        System.out.println("savedcomponentorder:"+pictureComponentGet.getOrder()+"-"+pictureComponentGet.getIconString());
    }

    
    
    private short booleanToShort(boolean bln)
    {
        return (short) (bln?1:0);
    }
    
    private Boolean ShortToBoolean(Short shrt)
    {            
        return (shrt==null?false:(shrt==1?true:false));
    }

    @Override
    public List<Object[]> getFrameRecordsLimited()
{
    Query query=pictureFrameCont.getEntityManager().createNamedQuery("PictureFrameTable.findAll");
    List<PictureFrameTable> queryResult=query.getResultList();
    List<Object[]> resultList=new ArrayList<>();    
    
    for (PictureFrameTable pictureFrameTable : queryResult) {
        Object[] frameTableLimited=new Object[6];            
        frameTableLimited[0]=pictureFrameTable.getName();
        frameTableLimited[1]=pictureFrameTable.getFrameSizeWidth();
        frameTableLimited[2]=pictureFrameTable.getFrameSizeHeight();
        frameTableLimited[3]=pictureFrameTable.getPicturePaneTableCollection().size();        
        frameTableLimited[4]=pictureFrameTable.getSaveDate();        
        frameTableLimited[5]=ShortToBoolean(pictureFrameTable.getSystemRecord());
        resultList.add(frameTableLimited);
    }
    return resultList;
        
}
    

    
@Override
public synchronized PictureFrameInterface loadFrame(String name) throws NonexistentEntityException{
       PictureFrameTable pictureFrameTable;
           Query query=getFramesByName(name);
           if (!query.getResultList().isEmpty()&&!name.isEmpty())
                {
               pictureFrameTable=(PictureFrameTable) query.getResultList().get(0);
               System.out.println("found record:"+query.getResultList().get(0).toString());
                    JFrameBaseFormParams params=new JFrameBaseFormParams.BaseFormParamsBuild()
                            .newInstance()
                            .width(pictureFrameTable.getFrameSizeWidth())
                            .height(pictureFrameTable.getFrameSizeHeight())
                            .x(100)
                            .y(100)
                            .title(name)
                            .adjMaxSize(true)
                            .toCenter(true)
                            .build();
               PictureFrameInterface pictureFrame=FormFactoryV1.createForm(FormTypes.PICTUREFRAME,null,null, params);               
               pictureFrame.setImage(ImageFactoryV1.getImage(DataSourceTypes.DISK, null, pictureFrameTable.getImageName()));               
               for (PicturePaneTable picturePaneTable : pictureFrameTable.getPicturePaneTableCollection()) {                        
                        loadPane(picturePaneTable, pictureFrame, true);                                                
                        }                        
                notifyObserver(Observer.Action.DB_LOAD_FRAME); 
                //pictureFrame.showState(true, null);                
                }
           else
               if (name!=null)
                    if (!name.isEmpty())
                        throw new NonexistentEntityException("There is no "+name+" saved layout.");                        
        return null;
    }

public void loadPane(PicturePaneTable picturePaneTable, PicturePaneInterface picturePaneParent, boolean firstLevelPane) {    
                        PictureComponentTable pictCompTablePane=null;
                        for (PictureComponentTable pictureComponentTable : picturePaneTable.getPictureComponentTableCollection()) {
                            if (ShortToBoolean(pictureComponentTable.getIsPaneComponent()))
                                pictCompTablePane=pictureComponentTable;                                
                        }
                        if (pictCompTablePane!=null) 
                            {                            
                            PicturePaneInterface picturePane=PictCompFactV1.createPictPane(fillPictCompParams(PictCompTypes.PICTUREPANE,pictCompTablePane), picturePaneParent, pictCompTablePane.getPosition(),ShortToBoolean(picturePaneTable.getFullState()),false);                                                      
                                System.out.println("loadpaneposition:"+pictCompTablePane.getPosition()+"-"+picturePane.getIconString());
                            for (PictureButtonTable pictureButtonTable : picturePaneTable.getPictureButtonTableCollection()) {
                                for (PictureComponentTable pictureComponentTable : pictureButtonTable.getPictureComponentTableCollection()) {
                                    loadButtonComponent(picturePane, pictureComponentTable);
                                }
                            }
                            for (PicturePaneTable picturePaneTable1 : picturePaneTable.getPicturePaneTableCollection()) {
                                    loadPane(picturePaneTable1,picturePane, false);    
                                    System.out.println("pictpanesub:"+picturePaneTable.toString());
                            }                                                            
                            for (PictureComponentTable pictureComponentTable : picturePaneTable.getPictureComponentTableCollection()) {                                        
                                if (!ShortToBoolean(pictureComponentTable.getIsPaneComponent())&&!ShortToBoolean(pictureComponentTable.getIsButtonComponent()))
                                        {                                            
                                            loadComponent(picturePane,pictureComponentTable);                                            
                                        }        
                            }
                            if (!firstLevelPane)
                                picturePane.showState(true, null);                                
                            else
                                if (picturePane.isFullState())
                                    picturePane.maximize();
                                else
                                    picturePane.minimize();                                
                            }                                    
                    }


public void loadComponent(PicturePaneInterface picturePane,PictureComponentTable pictureComponentTable) {    
    PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT,fillPictCompParams(PictCompTypes.PICTURECOMPONENT,pictureComponentTable), picturePane,pictureComponentTable.getPosition(), true);                                                
}

private void loadButtonComponent(PicturePaneInterface picturePane, PictureComponentTable pictureComponentTable) {    
//PictCompParams pictCompParams=new PictCompParams(null,pictureComponentTable.getImagePath(),pictureComponentTable.getIconString(), pictureComponentTable.getToolTipText(), pictureComponentTable.getCurrBaseSizeWidth(), pictureComponentTable.getCurrBaseSizeHeight(), pictureComponentTable.getCurrbaselocationX(), pictureComponentTable.getCurrbaselocationY(), MotionTypes.MedumFlowing, motionTypeMapping,menuMouseListener);
    PictCompFactV1.createPictComponent(PictCompTypes.PICTUREBUTTON,fillPictCompParams(PictCompTypes.PICTUREBUTTON,pictureComponentTable), picturePane,pictureComponentTable.getPosition(), true);                                                
}


private PictCompParams fillPictCompParams(PictCompTypes pictCompType,PictureComponentTable pictureComponentTable)
                {
                    System.out.println("loadpath:"+StaticEnvironmentParams.getProjectPath()+StaticEnvironmentParams.REL_PATH_TO_PICT+pictureComponentTable.getImagePath());
                 MapFactoryAbs<AnimTypeMap,Object> mapFactory=MapCreatorFactV1.getFactory(MapFactoryTypes.ANIMTYPE_ANIMPARAMS);                
                 PictCompParams pictCompParams=new PictCompParams.PictCompParamsBuild()
                .newInstance()
                .image(ImageFactoryV1.getImage(DataSourceTypes.DISK, null, pictureComponentTable.getImageName()))                         
                .iconString(pictureComponentTable.getIconString())
                .toolTipText(pictureComponentTable.getToolTipText())
                .width(pictureComponentTable.getCurrBaseSizeWidth())
                .height(pictureComponentTable.getCurrBaseSizeHeight())
                .x(pictureComponentTable.getCurrbaselocationX())
                .y(pictureComponentTable.getCurrbaselocationY())
                .defaultMotionType((pictCompType==PictCompTypes.PICTUREBUTTON)?MotionTypes.FASTEST_FLOWING:MotionTypes.MEDIUM_FLOWING)
                .motionTypeMaps(mapFactory.getMapping())
                .build();     
                return pictCompParams;
                }

    

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    private Query getFramesByName(String name)
    {
           Query query=pictureFrameCont.getEntityManager().createNamedQuery("PictureFrameTable.findByName");
           query.setParameter("name", name);
           return query;
        
    }
    
    @Override
    public synchronized boolean deleteFrame(String name) throws NonexistentEntityException, SystemRecordCannotBeDeleted{
        PictureFrameTable pictureFrameTable=(PictureFrameTable) getFramesByName(name).getResultList().get(0);
        if (ShortToBoolean(pictureFrameTable.getSystemRecord())==true)
                throw new SystemRecordCannotBeDeleted(name+" is a system record. Cannot be deleted!!");        
        for (PicturePaneTable picturePaneTable : pictureFrameTable.getPicturePaneTableCollection()) {                
                deletePane(name,picturePaneTable);
            }
        try {            
            pictureFrameCont.destroy(pictureFrameTable.getFrameID());                                    
        } catch (NonexistentEntityException ex) {
             throw new NonexistentEntityException(name+" wasn'T found in DB");            
        }
        notifyObserver(Observer.Action.DB_DELETE_FRAME);            
        return true;
    }

    private synchronized void deletePane(String name,PicturePaneTable picturePaneTable) throws NonexistentEntityException
    {
                try 
                {                                    
                    for (PicturePaneTable picturePaneTable1 : picturePaneTable.getPicturePaneTableCollection()) {
                        deletePane(name,picturePaneTable1);
                    }                
                    for (PictureComponentTable pictureComponentTable : picturePaneTable.getPictureComponentTableCollection()) {
                            pictureCompCont.destroy(pictureComponentTable.getComponentID());                
                    }                
                    for (PictureButtonTable pictureButtonTable : picturePaneTable.getPictureButtonTableCollection()) {
                            pictureButtonCont.destroy(pictureButtonTable.getButtonID());                    
                    }                               
                    picturePaneCont.destroy(picturePaneTable.getPaneID());
                } catch (IllegalOrphanException ex) {
                        throw new NonexistentEntityException(name+" related record couldn'T be deleted");
                }
        
    }

    @Override
    public void saveLogin() {
        Logins logins=new Logins();
        logins.setLoginDate(getDateNow());        
        try {
            logins.setIPaddress(InetAddress.getLocalHost().getHostAddress());
            logins.setHostName(InetAddress.getLocalHost().getCanonicalHostName());
        } catch (UnknownHostException ex) {
            Logger.getLogger(PictDBActionsV1.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loginsCont.create(logins);
        } catch (Exception ex) {
            Logger.getLogger(PictDBActionsV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Date getDateNow() {
            LocalDateTime ldt=LocalDateTime.now();
            return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());            
            }

   
}




