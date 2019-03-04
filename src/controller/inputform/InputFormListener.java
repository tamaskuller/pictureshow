/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inputform;

import enums.DataSourceTypes;
import enums.InputTypes;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ImageFactoryV1;
import controller.menu.MenuBuildV1;
import controller.maps.AnimTypeMap;
import view.PictCompFactV1;
import view.enums.MotionTypes;
import view.enums.PictCompTypes;
import view.interfaces.PicturePaneInterface;
import enums.MapFactoryTypes;
import controller.maps.MapCreatorFactV1;
import controller.maps.MapFactoryAbs;
import java.awt.event.ActionListener;
import util.mapping.MapInterface;
import view.FormFactoryV1;
import view.enums.FormTypes;
import view.inputForm.InputBox;
import view.inputForm.InputFormInt;
import view.recordtypeclasses.CompParams;
import view.recordtypeclasses.JFrameBaseFormParams;
import view.recordtypeclasses.PictCompParams;

/**
 *
 * @author Tamas Kuller
 */
public class InputFormListener implements ActionListener {

    CompParams compParams;
    PictCompParams pictCompParams;

    PicturePaneInterface parentPane;
    MapInterface<Field, InputBox> mapPane;
    FormTypes type;
    InputFormInt inputFormInt;

    public InputFormListener(PicturePaneInterface parentPane, CompParams compParams, MapInterface<Field, InputBox> map, FormTypes type, InputFormInt inputFormInt) {
        this.compParams = compParams;
        this.parentPane = parentPane;
        this.mapPane = map;
        this.type = type;
        this.inputFormInt = inputFormInt;        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean filledCorrectly = true;
        for (InputBox inputBox : inputFormInt.getInputBoxes()) {
            filledCorrectly = inputBox.getText().getText().isEmpty() && inputBox.isMandatory() ? false : filledCorrectly;
        }
        if (filledCorrectly) {
            Field[] fieldList = CompParams.class.getFields();
            for (InputBox inputBox : inputFormInt.getInputBoxes()) {
                for (Field field : fieldList) {
                    if (mapPane.get(field) != null) {
                        if (inputBox.equals(mapPane.get(field))) {
                            try {
                                if (!inputBox.getText().getText().isEmpty()) {
                                    if (inputBox.getInputType() == InputTypes.NUMBER) {
                                        field.set(compParams, Integer.parseInt(inputBox.getText().getText()));
                                    } else {
                                        field.set(compParams, inputBox.getText().getText());
                                    }
                                }
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(MenuBuildV1.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(MenuBuildV1.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
            boolean created = true;
            switch (type) {
                case SAVE_FRAME:
                case INPUTFORM_FRAME:
                    FormFactoryV1.createForm(FormTypes.PICTUREFRAME, null, null, getFrameParams());
                    break;
                case INPUTFORM_PANE:
                    setPictCompParams();
                    PicturePaneInterface pane = PictCompFactV1.createPictPane(pictCompParams, parentPane, 0, true, true);
                    pane.showState(true, null);
                    break;
                case INPUTFORM_PICT:
                    setPictCompParams();
                    PictCompFactV1.createPictComponent(PictCompTypes.PICTURECOMPONENT, pictCompParams, parentPane, 0, false);
                    System.out.println("pictcompaddheight:"+pictCompParams.getHeight());                    
                    parentPane.showState(true, null);
                    //parentPane.setFullState(true, MotionTypes.Simple,true);
                    break;
                case INPUTFORM_BACKPICT:
                    setPictCompParams();
                    parentPane.setImage(pictCompParams.image);
                    parentPane.setImagePath(pictCompParams.imagePath);
                    parentPane.updateSizeLocation();
                    break;
                default:
                    created = false;
            }
            if (created) {
                inputFormInt.close();
            }
        }
    }

    private void setPictCompParams() 
    {
        pictCompParams=new PictCompParams.PictCompParamsBuild().newInstance()
                .x(compParams.x)
                .y(compParams.y)
                .width(compParams.width)
                .height(compParams.height)
                .iconString(compParams.title)
                .imagePath(compParams.imagePath)
                .build();                
        pictCompParams.defaultMotionType = MotionTypes.MediumFlowing;
        pictCompParams.adminEnabled = parentPane.isAdminEnabled();
        MapFactoryAbs<AnimTypeMap, Object> mapFactory = MapCreatorFactV1.getFactory(MapFactoryTypes.ANIMTYPE_ANIMPARAMS);
        pictCompParams.motionTypeMaps = mapFactory.getMapping();
        pictCompParams.image = (pictCompParams.imagePath == null || pictCompParams.imagePath.isEmpty()) ? null : ImageFactoryV1.getImage(DataSourceTypes.DISK, pictCompParams.imagePath, null);
        pictCompParams.toolTipText = "";
    }

    private JFrameBaseFormParams getFrameParams() {
        return new JFrameBaseFormParams.BaseFormParamsBuild().newInstance()
                .x(compParams.x)
                .y(compParams.y)
                .width(compParams.width)
                .height(compParams.height)
                .title(compParams.title)
                .adjMaxSize(true)
                .toCenter(true)
                .build();                                
    }

}
