/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pictureshow;

import controller.DB.PictDBActionsV1;
import controller.DB.exceptions.NonexistentEntityException;
import enums.DataSourceTypes;
import view.enums.FormTypes;
import view.enums.PictCompTypes;
import view.enums.MotionTypes;
import model.ImageFactoryV1;
import view.FormFactoryV1;
import controller.maps.AnimTypeMap;
import enums.MapFactoryTypes;
import controller.maps.MapCreatorFactV1;
import controller.maps.MapFactoryAbs;
import javax.swing.JOptionPane;
import util.AppHostType;
import util.StaticEnvironmentParams;
import view.PictCompFactV1;
import view.interfaces.NamedImageInt;
import view.interfaces.PictureFrameInterface;
import view.interfaces.PicturePaneInterface;
import view.recordtypeclasses.PictCompParams;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.util.Observer;

/**
 *
 * @author Kuller Tamas
 */
public class PictureShow {

    public static PictureFrameInterface pictureFrame;    
    
    public static PicturePaneInterface picturePaneBase;    
    public static PicturePaneInterface picturePane;
    public static PicturePaneInterface picturePane2;
    
    public static PictCompParams pictParams;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        StaticEnvironmentParams.getProjectPath();
        JFrameBaseFormParams params=new JFrameBaseFormParams.BaseFormParamsBuild()
                .newInstance()
                .width(1000)
                .height(700)              
                .title("Pictures")
                .toCenter(true)
                .adjMaxSize(false)
                .build();
        //System.out.println("main arguments:"+args[0]);
               
        if (args.length>1)                  
            try{
            StaticEnvironmentParams.appHostType=AppHostType.valueOf(args[1]);                   
            } catch (IllegalArgumentException ex)
                    {
                        System.out.println("Not supported AppHostType - "+StaticEnvironmentParams.appHostType.name());
                    }                                
        System.out.println(StaticEnvironmentParams.appHostType.name()+" host type defaulted!");
        boolean frameLoaded=false;        
        if (args.length>0)
            try {
                PictDBActionsV1.getInstance().loadFrame(args[0]);
                frameLoaded=true;
                //FormFactoryV1.createForm(FormTypes.INSTRUCTION_FORM, null, pictureFrame, null);
                } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());                
                }                
        if (!frameLoaded)
            pictureFrame=FormFactoryV1.createForm(FormTypes.PICTUREFRAME,null,null,params);                                                              
        else
        if (!frameLoaded)
            {
             pictureFrame=FormFactoryV1.createForm(FormTypes.PICTUREFRAME,null,null,params);        
            String fileBud="Budapest.jpg";
            String fileLandScape="WP_20180929_09_10_42_Pro.jpg";
            String fileLandScape2="DSC05742.JPG";
            NamedImageInt bud=(ImageFactoryV1.getImage(DataSourceTypes.DISK, null,fileBud));
            NamedImageInt landScape=(ImageFactoryV1.getImage(null, null, fileLandScape));
            NamedImageInt landScape2=(ImageFactoryV1.getImage(null, null, fileLandScape2));        

            MapFactoryAbs<AnimTypeMap,Object> mapFactory=MapCreatorFactV1.getFactory(MapFactoryTypes.ANIMTYPE_ANIMPARAMS);                

            //AnimTypeMap motionTypeMapping=AnimTypeMapBuildVer1.getAnimTypeMap();                                        
            //ObservableJComponent jPictureBud=;                               


            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(null)                
                    .iconString("Temérdek kép")
                    .toolTipText("Sok kép")
                    .width(400)
                    .height(400)
                    .x(50)
                    .y(100)
                    .defaultMotionType(MotionTypes.FAST_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())
                    .build();                               
            picturePane=PictCompFactV1.createPictPane(pictParams,pictureFrame,0,true,true);                        


            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(landScape2)                
                    .iconString("A")
                    .toolTipText("A")
                    .width(300)
                    .height(300)
                    .x(50)
                    .y(50)
                    .defaultMotionType(MotionTypes.SLOW_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())
                    .build();                                               
           // PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT,pictParams,picturePane,0,false);

            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(landScape)                
                    .iconString("Land")
                    .toolTipText("Land")
                    .width(200)
                    .height(200)
                    .x(70)
                    .y(70)
                    .defaultMotionType(MotionTypes.SIMPLE)
                    .motionTypeMaps(mapFactory.getMapping())
                    .build();                                                       
        //    PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT,pictParams,picturePane,0,false);

            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(bud)                
                    .iconString("Budapesten")
                    .toolTipText("Bud")
                    .width(800)
                    .height(800)
                    .x(100)
                    .y(100)
                    .defaultMotionType(MotionTypes.SLOW_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())
                    .build();                                                               
         //   PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT,pictParams,picturePane,3,false);        

            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(null)                
                    .iconString("Sajna csak egy kép. Vagy több? :)")
                    .toolTipText("Egy kép")
                    .width(350)
                    .height(350)
                    .x(150)
                    .y(150)
                    .defaultMotionType(MotionTypes.SLOW_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())                
                    .build();                                                                       
            picturePane2=PictCompFactV1.createPictPane(pictParams,picturePane,0,false,true);

            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(landScape2)                
                    .iconString("Tájkép. Szép")
                    .toolTipText("A")
                    .width(250)
                    .height(250)
                    .x(0)
                    .y(0)
                    .defaultMotionType(MotionTypes.SLOW_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())                
                    .build();                                                                       
            PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT,pictParams,picturePane2,0,false);                         

            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(bud)                
                    .iconString("A")
                    .toolTipText("A")
                    .width(400)
                    .height(400)
                    .x(50)
                    .y(50)
                    .defaultMotionType(MotionTypes.SLOW_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())                
                    .build();                                                                       
            PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT,pictParams,picturePane2,0,false);   

            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(null)                
                    .iconString("Alapkép")
                    .toolTipText("Alap")
                    .width(600)
                    .height(600)
                    .x(0)
                    .y(0)
                    .defaultMotionType(MotionTypes.FAST_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())
                    .build();                
         //   picturePaneBase=PictCompFactV1.createPictPane(pictParams,pictureFrame,0,false,true);                


            pictParams=new PictCompParams.PictCompParamsBuild()
                    .newInstance()
                    .image(bud)                
                    .iconString("A")
                    .toolTipText("A")
                    .width(800)
                    .height(800)
                    .x(0)
                    .y(0)
                    .defaultMotionType(MotionTypes.SLOW_FLOWING)
                    .motionTypeMaps(mapFactory.getMapping())
                    .build();                        
         //   PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT,pictParams,picturePaneBase,0,false);   
            }            
    //      
        
          
                    
    
}    
}