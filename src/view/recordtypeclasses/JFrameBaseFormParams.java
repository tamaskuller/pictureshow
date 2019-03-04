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
public class JFrameBaseFormParams {
    public double x;
    public double y;
    public double width;
    public double height;
    public String title;
    public boolean toCenter;
    public boolean adjMaxSize;
    
    
    private JFrameBaseFormParams(){        
    }
    
    private JFrameBaseFormParams(BaseFormParamsBuild paramsBuild) {
        this.width = paramsBuild.baseFormParams.width;
        this.height = paramsBuild.baseFormParams.height;
        this.x=paramsBuild.baseFormParams.x;        
        this.y=paramsBuild.baseFormParams.y;
        this.title=paramsBuild.baseFormParams.title;
        this.toCenter=paramsBuild.baseFormParams.toCenter;
        this.adjMaxSize=paramsBuild.baseFormParams.adjMaxSize;
    }
    
 
    public static class BaseFormParamsBuild {    
    static JFrameBaseFormParams baseFormParams;
    
    public BaseFormParamsBuild newInstance(){
        baseFormParams=new JFrameBaseFormParams();
        return this;        
    }
    
    public JFrameBaseFormParams build() {        
        return baseFormParams;
    }

    
    public BaseFormParamsBuild width(double width) {
        baseFormParams.width = width;
        return this;
    }

    public BaseFormParamsBuild height(double height) {
        baseFormParams.height = height;
        return this;
    }

    public BaseFormParamsBuild x(double x) {
        baseFormParams.x = x;
        return this;
    }

    public BaseFormParamsBuild y(double y) {
        baseFormParams.y = y;
        return this;
    }
    
    public BaseFormParamsBuild title(String title) {
        baseFormParams.title = title;
        return this;
    }
    public BaseFormParamsBuild toCenter(boolean toCenter) {
        baseFormParams.toCenter = toCenter;
        return this;
    }
    public BaseFormParamsBuild adjMaxSize(boolean adjMaxSize) {
        baseFormParams.adjMaxSize = adjMaxSize;
        return this;
    }
    
    
    
    

  }
    
}
