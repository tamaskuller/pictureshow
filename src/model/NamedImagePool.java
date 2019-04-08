/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import view.interfaces.NamedImageInt;

/**
 *
 * @author Tamas Kuller
 */
public class NamedImagePool implements PoolInt<NamedImageInt, String>{
    List<NamedImageInt> images;        

    public NamedImagePool() {
        this.images = new ArrayList<>();
    }
    
    @Override
    public NamedImageInt getItem(String searchFor) {                        
        for (NamedImageInt image : images) {
            if (image.getImageName().equals(searchFor))
                return image;
        }
        return null;
    }

    @Override
    public boolean contains(String searchFor) {
        return getItem(searchFor)!=null;
    }             

    @Override
    public void addItem(NamedImageInt item) {
        if (item!=null&&!images.contains(item))
            images.add(item);        
    }

    @Override
    public synchronized boolean deleteItem(NamedImageInt item) {
        if (item!=null&&!images.isEmpty()&&images.contains(item))
            {
            images.remove(item);
            System.out.println("Image remove from pool:"+item.getImageName());
            item=null;            
            return true;
            }
        return false;
    }
    
    
}
