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
public class TermGroup {
    public List<Term> terms;
    
    public TermGroup(){
        terms = new ArrayList();
    }
    
    public TermGroup(List<Term> terms){
        this.terms = new ArrayList();
        
        for(Term t: terms)
            this.terms.add(t);
    }
    
    public void addTerm(Term t){
        terms.add(t);
    }
    
    public Term getTerm(int index){
        return terms.get(index);
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
    
    public int getSize(){
        return this.terms.size();
    }
    
    public int getLiterals(){
        int literals = 0;
        for(Term t: terms){
            for(char c: t.getBytes().toCharArray()){
                if(c != '-'){
                    literals++;
                }
            }
        }
        return literals;
    }
    
    public void removeTerm(Term t){
        terms.remove(t);
    }
    
}
