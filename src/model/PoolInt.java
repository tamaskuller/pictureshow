/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tamas Kuller
 */
interface PoolInt<I, S> {    
    public I getItem(S searchFor);
    public void addItem(I item);
    public boolean deleteItem(I item);    
    public boolean contains(S searchFor);
}
