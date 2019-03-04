/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.mapping;

/**
 *
 * @author Kuller Tamas
 */
public interface EntryInterface<K,V> {
    
    public K getKey();        
    public V getValue(); 
    
    
}
