/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.maps;

/**
 *
 * @author Tamas Kuller
 */
public abstract class MapFactoryAbs <M,P> {    
    public abstract M getMapping(P parameter);    
    public abstract M getMapping();        
}
