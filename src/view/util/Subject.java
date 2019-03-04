/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.util;

import java.util.List;
import view.util.Observer.Action;


/**
 *
 * @author Kuller Tamas
 */
public interface Subject {    
    default public void addObserver(Observer o)
    {
        if (!getObservers().contains(o))
            getObservers().add(o);    
    }
    
    default public void removeObserver(Observer o)
    {
        if (getObservers().contains(o))
            getObservers().remove(o);
    }
    
    default public void notifyObserver(Action action)
    {
        if (!getObservers().isEmpty())
            for (Observer observer : this.getObservers()) {
                observer.update(action);   
                System.out.println("subject"+this+"notifyobs:"+observer+" - "+action);
            }
    }
    public List<Observer> getObservers();
}