/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.recordtypeclasses;


/**
 *
 * @author Kuller Tamas
 */
public class AnimParams {

        private int steps;
        private int waitTime;

    public AnimParams(int steps, int waitTime) {
        this.steps = steps;
        this.waitTime = waitTime;
    }

    public int getSteps() {
        return steps;
    }

    public int getWaitTime() {
        return waitTime;
    }
            
    
}
