/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.recordtypeclasses;

/**
 *
 * @author Tamas Kuller
 */
public class CompParams {
    public String title="";
    public double width;
    public double height;
    public double x=0;
    public double y=0;
    public String imagePath="";
    
    private CompParams(){
        
    }
    
    private CompParams(PictCompParamsBuild paramsBuild) {
        this.title = paramsBuild.compParams.title;
        this.width = paramsBuild.compParams.width;
        this.height = paramsBuild.compParams.height;
        this.x=paramsBuild.compParams.x;
        this.y=paramsBuild.compParams.y;        
        this.imagePath=paramsBuild.compParams.imagePath;        
    }
 
    public static class PictCompParamsBuild {    
    static CompParams compParams;
    
    public PictCompParamsBuild newInstance(){
        compParams=new CompParams();
        return this;        
    }
    
    public CompParams build() {        
        return compParams;
    }

    
    
    public PictCompParamsBuild width(double width) {
        compParams.width = width;
        return this;
    }

    public PictCompParamsBuild height(double height) {
        compParams.height = height;
        return this;
    }

    public PictCompParamsBuild x(double x) {
        compParams.x = x;
        return this;
    }

    public PictCompParamsBuild y(double y) {
        compParams.y = y;
        return this;
    }
    public PictCompParamsBuild imagePath(String imagePath) {
        compParams.imagePath = imagePath;
        return this;
    }

    

  }
    
}
