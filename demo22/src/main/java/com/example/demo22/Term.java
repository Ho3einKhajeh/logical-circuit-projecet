/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo22;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mark_2
 */
public class Term {

    private List<FunctionMember> functions;
    private List<Integer> terms;
    private int length;
    private String bytes;
    private boolean checked;
    private List<Term> covering = new ArrayList();

    public Term(int length, int termVal) {
        functions = new ArrayList();
        terms = new ArrayList();
        this.length = length;
        bytes = Integer.toBinaryString(termVal);
        while (bytes.length() < length) {
            bytes = "0" + bytes;
        }
        terms.add(termVal);
    }
    
    public void addCovering(Term term){
        covering.add(term);
    }
    
    public List<Term> getCovering(){
        return covering;
    }

    public Term(int length, String termVal, List<FunctionMember> functions, List<Integer> terms) {
        this.functions = functions;
        this.terms = terms;
        this.length = length;
        bytes = termVal;
        while (bytes.length() < length) {
            bytes = "0" + bytes;
        }
    }

    public void addTerms(int term) {
        terms.add(term);
    }

    public void addFunction(FunctionMember funct) {
        functions.add(funct);
    }

    public boolean isUsed() {
        if (functions.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean isChecked() {
        return checked;
    }

    public void check() {
        checked = true;
    }

    public String getBytes() {
        return bytes;
    }

    public List<FunctionMember> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionMember> functions) {
        this.functions = functions;
    }

    public List<Integer> getTerms() {
        return terms;
    }

    public void setTerms(List<Integer> terms) {
        this.terms = terms;
    }
    
    public boolean fillsTerm(int function, int term){
        boolean hasFunction = false;
        
        for(FunctionMember f: functions){
            if(f.getFunction() == function && !f.isDont())
                hasFunction = true;
        }
        
        boolean hasTerm = false;
        
        for(int t: terms){
            if(t == term)
                hasTerm = true;
        }
        
        return hasFunction && hasTerm;
    }

    public FunctionMember getFunction(int index) {
        return functions.get(index);
    }

    public String toString() {
        String ret = "";

        for (FunctionMember function : functions) {
            ret += function.getChar();
            ret += ",";
        }

        if (ret.length() > 0) {
            ret = ret.substring(0, ret.length() - 1);
        }

        while (ret.length() <= 2 * length) {
            ret += " ";
        }
        ret += "| ";

        ret += bytes;
        ret += " | ";

        for (int term : terms) {
            ret += term;
            ret += ",";
        }
        ret = ret.substring(0, ret.length() - 1);

        if (checked) {
            ret += " X";
        }

        return ret;
    }

    public boolean compareFunctions(List<FunctionMember> otherFunctions) {
        if (otherFunctions.size() != functions.size()) {
            return false;
        }

        for (FunctionMember function : functions) {
            boolean shared = false;
            for (FunctionMember f2 : otherFunctions) {
                if (function.equals(f2)) {
                    shared = true;
                }
            }
            if (!shared) {
                return false;
            }
        }

        return true;
    }

    public boolean compareTerms(Term t2) {
        if (!bytes.equals(t2.getBytes())) {
            return false;
        }

        List<FunctionMember> otherFunctions = t2.getFunctions();

        if (!compareFunctions(otherFunctions)) {
            return false;
        }

        return true;
    }
}
