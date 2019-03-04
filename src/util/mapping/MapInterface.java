/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.mapping;

import java.util.List;


/**
 *
 * @author Kuller Tamas
 */
public interface MapInterface<K,V> {
    
    public default void put(EntryInterface<K,V> entry)
    {                
        if (get(entry.getKey())==null)                        
            {        
            entrySet().add(entry);            
            }
        else
        {
            remove(entry.getKey());
        }
    }
    public default V remove(K key)
    {if (this.containsKey(key))
            {        
                for (EntryInterface<K,V> entryInterface : entrySet()) {                    
                    V v=entryInterface.getValue();
                    entrySet().remove(entryInterface);
                    return v;
                }            
            }        
        return null;
    }
    public default V get(K key)
        {
        for (EntryInterface<K,V> entry : entrySet()) {
            if (entry.getKey().equals(key))
            {                
                return (entry.getValue());
            }
        }
        return null;
        }
    
    public List<EntryInterface<K,V>> entrySet();    
    
    public default boolean containsKey(K key)
    {
      return (this.get(key)==null)?false:true;            
    }
    
    
}
