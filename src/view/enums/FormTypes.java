/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.enums;

/**
 *
 * @author Tamas Kuller
 */
public enum FormTypes {
    
    PICTUREFRAME,PICTUREFRAME_EMPTY,INPUTFORM_PANE, INPUTFORM_PICT,INPUTFORM_BACKPICT, FRAMESFORM, 
    SAVE_FRAME, MESSAGE_FORM_DELETE, MESSAGE_FORM_SAVE, MESSAGE_FORM_DRAW,
    INSTRUCTION_FORM, INPUTFORM_FRAME;
    
    @Override
    public String toString() {
        switch (this)
        {
            case INPUTFORM_BACKPICT:
                return "Background Picture";
           case INPUTFORM_PANE:
                return "Pane of Pictures";
           case INPUTFORM_PICT:
                return "Picture";
           case INPUTFORM_FRAME:
                return "New Layout";
          case SAVE_FRAME:
                    return "Save frame";                                
        }
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
