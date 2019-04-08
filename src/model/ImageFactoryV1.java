/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enums.DataSourceTypes;
import view.FormFactoryV1;
import view.interfaces.AttachedGettersInt;
import view.interfaces.NamedImageInt;
import view.interfaces.PictureComponentInterface;
import view.interfaces.PictureFrameInterface;
import view.interfaces.PicturePaneInterface;

/**
 *
 * @author kulle
 */
public class ImageFactoryV1 {

    private static NamedImagePool imagePool=new NamedImagePool();
    private static final DataSourceTypes defaultSourceType=DataSourceTypes.DISK;
    
    public static NamedImageInt getImage(DataSourceTypes sourceType, String path, String name)
    {
            NamedImageInt result;
            if (imagePool.contains(name))
                result=imagePool.getItem(name);                
            else
                {                   
                if (sourceType==null)
                    result=ImageOperationsV1.getNamedImage(path,name);  
                else
                    switch (sourceType)
                        {                                
                        default:
                        case DISK: result=ImageOperationsV1.getNamedImage(path,name);                                                
                        }                 
                imagePool.addItem(result);
                }
            return result;
    }        
    
    public static boolean removeImage(NamedImageInt toRemove)
    {                
        for (PictureFrameInterface pictureFrame : FormFactoryV1.getPictureFrames()) {
            if(pictureFrame.getImage()!=null&&pictureFrame.getImage().equals(toRemove))
               return false;
            for (PicturePaneInterface picturePane : pictureFrame.getPicturePanes()) {
                if (picturePane.getImage()!=null&&picturePane.getImage().equals(toRemove))
                    return false;                                        
                boolean canPictDel=canPictDelPane(picturePane, toRemove);
                if (!canPictDel)
                    return canPictDel;
                }
            }        
        return imagePool.deleteItem(toRemove);
    }

private static boolean canPictDelPane(PicturePaneInterface picturePane, NamedImageInt toRemove)        
{    
    for (PictureComponentInterface pictureComponent : picturePane.getPictureComponents()) {                    
        if (pictureComponent.getImage()!=null&&pictureComponent.getImage().equals(toRemove))
            return false;
        if (pictureComponent instanceof PicturePaneInterface)
            {
            boolean canPictDel=canPictDelPane((PicturePaneInterface) pictureComponent, toRemove);
            if(!canPictDel) 
                return canPictDel;
            }                    
    }
    return true;                
}

}
