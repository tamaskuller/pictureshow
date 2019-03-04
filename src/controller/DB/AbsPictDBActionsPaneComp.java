/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;

import model.PictureButtonTable;
import model.PictureComponentTable;
import model.PictureFrameTable;
import model.PicturePaneTable;
import view.interfaces.AttachedGettersInt;
import view.interfaces.PicturePaneInterface;

/**
 *
 * @author Tamas Kuller
 */
public abstract class AbsPictDBActionsPaneComp {
    protected abstract void savePane(PicturePaneInterface picturePaneInterface,PictureFrameTable parentPictureFrameTable, PicturePaneTable parentPicturePaneTable);
    protected abstract PictureComponentTable saveComponent(AttachedGettersInt pictureComponentInterface, PicturePaneTable parentPicturePaneTable, boolean isPaneComponent,PictureButtonTable pictureButtonTable);
    
}
