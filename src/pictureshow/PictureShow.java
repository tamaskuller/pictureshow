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
        PictDBActionsV1.getInstance().saveLogin();
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
            pictureFrame=FormFactoryV1.createForm(FormTypes.PICTUREFRAME_EMPTY,null,null,params);                                                              
          
                    
    
}    
}