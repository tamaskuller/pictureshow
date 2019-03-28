/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import java.util.List;
import view.enums.MotionTypes;

/**
 *
 * @author Tamas Kuller
 */
public interface PicturePaneGettersInt extends MultiComponentGetterInt, AdministratableGetterInt    
{
    public MotionTypes getReOrderMotionType(); 
    public List<PictureComponentInterface> getPictureComponents();  
}
