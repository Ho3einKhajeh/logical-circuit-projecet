/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo22;

/**
 *
 * @author Mark_2
 */
public class FunctionMember {
    
    private int function;
    private boolean dont;

    public FunctionMember(int function, boolean dont) {
        this.function = function;
        this.dont = false;
    }

    public boolean equals(FunctionMember obj) {
       
        if (this.function == obj.function) {
            return true;
        }
        return false;
    }
    
    public boolean equalsAndUsable(int funct) {
       
        return (function == funct) && !dont;
    }

    public int getFunction() {
        return function;
    }

    public boolean isDont() {
        return dont;
    }
    
    public String getChar() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        return Character.toString(alphabet[function]);
    }
}
