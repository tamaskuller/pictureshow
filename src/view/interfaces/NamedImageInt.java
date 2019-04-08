/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import java.awt.Image;

/**
 *
 * @author Kuller Tamas
 */
public interface NamedImageInt extends ImageInt<Image>, ImageGetterInt<Image>{
    public String getImageName();
    public void setImageName(String Name);   
    
}
