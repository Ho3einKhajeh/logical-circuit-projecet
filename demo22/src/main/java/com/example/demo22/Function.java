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
public class Function {
    
    List<Integer> terms;
    List<Term> necessaryImplicants = new ArrayList();
    
    public Function(List<Integer> terms){
        this.terms = terms;
    }
    
    public Function(){
        this.terms = new ArrayList();
    }

    public void setNecessaryImplicants(List<Term> necessaryImplicants) {
        this.necessaryImplicants = necessaryImplicants;
    }
    
    public void deleteTerm(int index){
        terms.remove(index);
    }
    
    public void addTerm(int term){
        terms.add(term);
    }
        
    public int getLength(){
        return terms.size();
    }

    public List<Integer> getTerms() {
        return terms;
    }
    
    public List<Term> getNecessaryImplicants(){
        return necessaryImplicants;
    }
    
    public void addImplicant(Term t){
        necessaryImplicants.add(t);
    }
    
    public String getChar(int term) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        return Character.toString(alphabet[term]);
    }
}
