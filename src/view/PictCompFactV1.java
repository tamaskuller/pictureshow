/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.maps.AnimTypeMap;
import controller.maps.MapCreatorFactV1;
import controller.maps.MapFactoryAbs;
import view.listeners.CompListenerBuildSingVer1;
import view.enums.PictCompTypes;
import enums.MapFactoryTypes;
import view.enums.MotionTypes;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.PictCompParams;
import view.interfaces.PictureComponentGettersInt;
import view.interfaces.PictureComponentInterface;

/**
 *
 * @author kulle
 */
public final class PictCompFactV1 {    
    
    
    public static PictureComponentInterface createPictComponent(PictCompTypes pictCompType,PictCompParams params, PicturePaneInterface parent, int order, boolean fullState)
    {        
        CompListenerBuildSingVer1 activityJComponentBuildVer1=CompListenerBuildSingVer1.getInstance();
        PictureComponent pictureComponent=null;
        PictureComponentGettersInt pictureComponentGetters=null;
        switch (pictCompType)
        {
            case PICTURECOMPONENT:                
                pictureComponent=new PictureComponent(parent,params); 
                pictureComponentGetters=new PictureComponentGet((PictureComponent) pictureComponent);                
                parent.addPictComponent(pictureComponent, order);            
                break;
            case PICTUREBUTTON:
                pictureComponent=new PictureButton(parent,params);                  
                pictureComponentGetters=new PictureComponentGet((PictureButton) pictureComponent);                
                parent.addButton(pictureComponent, 0);            
                break;
            case PICTUREPANE:
                pictureComponent=new PicturePane(parent,params,fullState);                    
                pictureComponentGetters=new PictureComponentGet((PicturePane) pictureComponent);                
                parent.addPictComponent(pictureComponent, order);            
                break;               
        }
        if (pictureComponent!=null&&pictureComponentGetters!=null)
            {
            pictureComponent.setGetters(pictureComponentGetters);
            pictureComponent.addMouseListener(activityJComponentBuildVer1.addMouseActivity(pictureComponent, parent.getPopupMenu()));          
            pictureComponent.addMouseMotionListener(activityJComponentBuildVer1.addMouseMotionActivity(pictureComponent));          
            pictureComponent.setPopupMenu(parent.getPopupMenu());
            }    
        return pictureComponent;
    }
    
    
    public static PicturePaneInterface createPictPane(PictCompParams params, PicturePaneInterface parent,int order, boolean fullState, boolean button)
    {        
        CompListenerBuildSingVer1 activityJComponentBuildVer1=CompListenerBuildSingVer1.getInstance();        
        PicturePane picturePane=new PicturePane(parent,params,fullState);                        
        PicturePaneGet picturePaneGet=new PicturePaneGet(picturePane, picturePane);
        picturePane.setGetters(picturePaneGet);
        picturePane.addMouseListener(activityJComponentBuildVer1.addMouseActivity(picturePane,parent.getPopupMenu()));          
        picturePane.addMouseMotionListener(activityJComponentBuildVer1.addMouseMotionActivity(picturePane));                  
        picturePane.setPopupMenu(parent.getPopupMenu());
        MapFactoryAbs<AnimTypeMap,Object> mapFactory=MapCreatorFactV1.getFactory(MapFactoryTypes.ANIMTYPE_ANIMPARAMS);        
        PictCompParams pictParams=new PictCompParams.PictCompParamsBuild()
                .newInstance()
                .image(null)                
                .toolTipText("Push")
                .width(20)
                .height(20)
                .x(0)
                .y(0)
                .defaultMotionType(MotionTypes.FastFlowing)
                .motionTypeMaps(mapFactory.getMapping())
                .adminEnabled(picturePane.isAdminEnabled())
                .build();                        
        if (button)
            PictCompFactV1.createPictComponent(PictCompTypes.PICTUREBUTTON,pictParams,picturePane,0,false);                                        
        parent.addPictPane(picturePane, order);      
        return picturePane;
    }
    
    
}
