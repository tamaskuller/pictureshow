/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.recordtypeclasses;

import view.enums.MotionTypes;

/**
 *
 * @author Kuller Tamas
 */
public final class PaintRequestParams {
public boolean show;
public boolean forced;
public MotionTypes motionType;
public int order;
public boolean checkMin;


    public PaintRequestParams(boolean show,boolean forced,MotionTypes motionType, int order, boolean checkMin) {        
        this.forced = forced;        
        this.motionType = motionType;        
        this.order=order;
        this.show=show;
        this.checkMin=checkMin;

    }
    
    
    


}
