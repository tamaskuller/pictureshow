/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;

/**
 *
 * @author Tamas Kuller
 */
public class DynamicEnvironmentParams {
    static DynamicEnvironmentParams instance;
    private File envLastUsedPath;    
    
    static {instance=new DynamicEnvironmentParams();        
    }

    private DynamicEnvironmentParams() {
    }
    
    public static DynamicEnvironmentParams getInstance()
    {
        return instance;
    }

    public File getEnvLastUsedPath() {
        return envLastUsedPath;
    }

    public void setEnvLastUsedPath(File envLastUsedPath) {
        this.envLastUsedPath = envLastUsedPath;
    }
    
    
    
}
