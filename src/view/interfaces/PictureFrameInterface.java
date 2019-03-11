/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import java.util.List;
import view.util.Observer;
import view.util.Subject;

/**
 *
 * @author Tamas Kuller
 */
public interface PictureFrameInterface extends PicturePaneInterface,AdministratableInt, MultiComponentInt,MultiComponentGetterInt,AttachedGettersInt<Object>, Subject, Observer, AutoShapeFormInt {    
//    public boolean hideShowClicked();
    public void setTitle(String title);
    public List<PicturePaneInterface> getPicturePanes();   
    public boolean isActivated();
    public void setMainForm(boolean mainForm);
    public void dbLoad();
    
}
