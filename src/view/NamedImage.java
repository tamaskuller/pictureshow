/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Image;
import view.interfaces.NamedImageInt;

/**
 *
 * @author Tamas Kuller
 */
public class NamedImage implements NamedImageInt {    
    private Image image;
    private String imageName;

    public NamedImage() {
    }

    
    public NamedImage(Image image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public String getImageName() {
        return imageName;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    
    
    
}
