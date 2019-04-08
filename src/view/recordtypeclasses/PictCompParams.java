/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.recordtypeclasses;

import java.awt.event.MouseListener;
import view.enums.MotionTypes;
import util.mapping.MapInterface;
import view.interfaces.NamedImageInt;

/**
 *
 * @author Tamas Kuller
 */
public class PictCompParams {

    public NamedImageInt image=null;
    public String imagePath=null;
    public String iconString="";
    public String toolTipText="";    
    private double width;
    private double height;
    private double x=1;
    private double y=1;
    public MotionTypes defaultMotionType;
    public MapInterface<MotionTypes,AnimParams> motionTypeMaps;    
    public MouseListener mouseListener=null;
    public boolean adminEnabled=false;
        
    private PictCompParams(){
        
    }
    
    private PictCompParams(PictCompParamsBuild paramsBuild) {
        this.image = paramsBuild.pictCompParams.image;
        this.imagePath=paramsBuild.pictCompParams.imagePath;
        this.iconString = paramsBuild.pictCompParams.iconString;
        this.toolTipText = paramsBuild.pictCompParams.toolTipText;
        this.width = paramsBuild.pictCompParams.width;
        this.height = paramsBuild.pictCompParams.height;
        this.x=paramsBuild.pictCompParams.x;
        this.y=paramsBuild.pictCompParams.y;
        this.defaultMotionType = paramsBuild.pictCompParams.defaultMotionType;
        this.motionTypeMaps = paramsBuild.pictCompParams.motionTypeMaps;
        this.mouseListener=paramsBuild.pictCompParams.mouseListener;
        this.adminEnabled=paramsBuild.pictCompParams.adminEnabled;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
     
    
    public static class PictCompParamsBuild {    
    static PictCompParams pictCompParams;
    
    public PictCompParamsBuild newInstance(){
        pictCompParams=new PictCompParams();
        return this;        
    }
    
    public PictCompParams build() {        
        return pictCompParams;
    }

    public PictCompParamsBuild image(NamedImageInt image) {
        pictCompParams.image = image;
        return this;        
    }
    
    public PictCompParamsBuild imagePath(String imagePath) {
        pictCompParams.imagePath = imagePath;
        return this;        
    }
    
    public PictCompParamsBuild iconString(String iconString) {
        pictCompParams.iconString = iconString;
        return this;
    }

    public PictCompParamsBuild toolTipText(String toolTipText) {
        pictCompParams.toolTipText = toolTipText;
        return this;
    }

    public PictCompParamsBuild width(double width) {
        pictCompParams.width = width;
        return this;
    }

    public PictCompParamsBuild height(double height) {
        pictCompParams.height = height;
        return this;
    }

    public PictCompParamsBuild x(double x) {
        pictCompParams.x = x;
        return this;
    }

    public PictCompParamsBuild y(double y) {
        pictCompParams.y = y;
        return this;
    }

    public PictCompParamsBuild defaultMotionType(MotionTypes defaultMotionType) {
        pictCompParams.defaultMotionType = defaultMotionType;
        return this;
    }

    public PictCompParamsBuild motionTypeMaps(MapInterface motionTypeMaps) {
        pictCompParams.motionTypeMaps = motionTypeMaps;
        return this;
    }

    public PictCompParamsBuild mouseListener(MouseListener mouseListener) {
        pictCompParams.mouseListener = mouseListener;
        return this;        
    }
    
    public PictCompParamsBuild adminEnabled(boolean adminEnabled) {
        pictCompParams.adminEnabled = adminEnabled;
        return this;        
    }
    

    
    
  }
    
}
