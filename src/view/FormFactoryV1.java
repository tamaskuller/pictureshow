/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DB.PictDBActionsV1;
import controller.menu.MenuBuildV1;
import view.enums.FormTypes;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import controller.menu.MenuMouseListenerBuildV1;
import view.interfaces.PictureFrameInterface;
import controller.inputform.CompCreateInputFormV1;
import controller.inputform.FramesTableFormSingV2;
import controller.inputform.MessageFormV1;
import controller.inputform.InstructionForm;
import java.util.ArrayList;
import java.util.List;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.interfaces.PicturePaneInterface;
import view.util.Observer;
import view.util.Observer.Action;
import view.interfaces.AutoShape.AutoShapeCompInt;

/**
 *
 * @author Tamas Kuller
 */
public class FormFactoryV1 <F> {
static List<PictureFrameInterface> pictureFrames=new ArrayList<>();
static Object formObject;
static JFrame form;
static AutoShapeCompInt autoForm;
static Observer observerForm;
static PictureFrameInterface mainForm;
static int onClose;

    public static <F> F createForm(FormTypes formType, PicturePaneInterface parentPane,PictureFrameInterface parentFrame, JFrameBaseFormParams baseFormParams) {
        boolean emptyForm=false;
        onClose=WindowConstants.DISPOSE_ON_CLOSE;                    
        switch (formType)
        {                        
            case INPUTFORM_BACKPICT:
            case INPUTFORM_PANE:
            case INPUTFORM_PICT:  
            case INPUTFORM_FRAME:
                //MapFactoryAbs<FormTypePictCompMap,Object> mapFactory=MapCreatorFactory.getFactory(MapFactoryTypes.FORM_COMPTYPE);
                //FormTypePictCompMap map=mapFactory.getMapping();
                //form=new CompCreateInputForm(parent, map.get(formType));
                form=new CompCreateInputFormV1((parentPane==null)?parentFrame:parentPane, formType);
                //form=inputForm;                                                
                formObject=form;                                       
                break;        
            case MESSAGE_FORM_DRAW:
            case MESSAGE_FORM_DELETE:            
            case MESSAGE_FORM_SAVE:                       
                String message=baseFormParams.title;
                baseFormParams.title=null;    
                form=new MessageFormV1(baseFormParams,message,formType, (formType==FormTypes.MESSAGE_FORM_DRAW)?true:false);
                break;            
                
            case INSTRUCTION_FORM:                               
                form=InstructionForm.getInstance(parentFrame);                                                
                break;
            case FRAMESFORM:
                form=FramesTableFormSingV2.getInstance();                                                            
                FramesTableFormSingV2.getInstance().showHideSaveFields(false);                
                break;
            case SAVE_FRAME:
                //form=new SaveForm(parentFrame);         
                //FramesTableFormSingV2.getInstance().removeSaveSection();
                form=FramesTableFormSingV2.getInstance();                 
                FramesTableFormSingV2.getInstance().showSaveSection(parentFrame);
                
                //observerForm=new FramesTableFormSaveV1(parentFrame);
                //form=(JFrame) observerForm;
                //parentFrame.addObserver(observerForm);
                break;
            case PICTUREFRAME_EMPTY:
                emptyForm=true;
            case PICTUREFRAME:
                PictureFrame pictureFrame=new PictureFrame(baseFormParams); 
                PictureFrameGet pictureFrameGet=new PictureFrameGet(pictureFrame);
                pictureFrame.setGetters(pictureFrameGet);
                if (emptyForm)
                    pictureFrame.setDbLoadReady(true);
                MenuBuildV1 menuInit=new MenuBuildV1(pictureFrame);         
                pictureFrame.setPopupMenu(menuInit.getMenuBar());
                pictureFrame.addMouseListener(MenuMouseListenerBuildV1.getInstance().buildMouseListener(menuInit.getMenuBar()));     
                form=pictureFrame;    
                formObject=pictureFrame;    
                observerForm=pictureFrame;    
                PictDBActionsV1.getInstance().addObserver(pictureFrame); 
                pictureFrames.add(pictureFrame);
                if (mainForm==null)
                    {
                    mainForm=pictureFrame;
                    pictureFrame.setMainForm(true);
                    form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   
                    System.out.println("mainformCreated");                       
                    }                
                break;
        }
     if (form!=null)
        {
        if (form!=mainForm)            
            form.setDefaultCloseOperation(onClose);                         
        SwingUtilities.invokeLater(new Runnable (){                    
                    public void run() {
                        form.setVisible(true);                        
                        observerForm.update(Action.FRAME_READY, null);
                        System.out.println("formstarted");                                            
                    }
                });                         
        }
     return (F) form;          
     }                               

    public static List<PictureFrameInterface> getPictureFrames() {
        return pictureFrames;
    }   
    
}
