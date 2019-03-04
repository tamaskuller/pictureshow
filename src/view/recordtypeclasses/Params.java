/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.recordtypeclasses;

import enums.InputTypes;

/**
 *
 * @author Tamas Kuller
 */
public class Params{
    String name;
    InputTypes type;

    public Params(String name, InputTypes type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public InputTypes getType() {
        return type;
    }

    
    
}
