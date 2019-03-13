/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

/**
 *
 * @author Tamas Kuller
 */
public interface ActiveComponentInt {
    public void mouseEnterred();
    public void mouseExited();
    public void onClick();
    public int  getActivateClickCount();
    public void activate();
    public void deActivate();
    public void minimize();    
    public void maximize();          
    
}
