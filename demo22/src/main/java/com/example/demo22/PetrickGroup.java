package com.example.demo22;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mark_2
 */
public class PetrickGroup {

    private List<TermGroup> group = new ArrayList();

    public PetrickGroup(TermGroup terms) {
        for (Term t : terms.getTerms()) {
            TermGroup g = new TermGroup();
            g.addTerm(t);
            group.add(g);
        }
    }

    public PetrickGroup(List<PetrickGroup> groups) {
        if (groups.size() > 0) {
            PetrickGroup g1 = groups.get(0);
            for (int i = 0; i < g1.getSize(); i++) {
                group.add(new TermGroup(g1.getGroup(i).getTerms()));
            }

            for (int i = 1; i < groups.size(); i++) {
                simplifyTerm(groups.get(i));
            }
        }
    }

    public TermGroup getSmallest() {
        if(group.size() == 0)
            return new TermGroup();
        int minimumLength = group.get(0).getSize();
        List<TermGroup> smallest = new ArrayList();
        for (TermGroup g : group) {
            if (g.getSize() < minimumLength) {
                minimumLength = g.getSize();
                smallest = new ArrayList();
                smallest.add(g);
            } else if (g.getSize() == minimumLength) {
                smallest.add(g);
            }
        }

        TermGroup ret = smallest.get(0);
        int leastLiterals = ret.getLiterals();
        for (TermGroup group : smallest) {
            if (group.getLiterals() < leastLiterals) {
                ret = group;
                leastLiterals = group.getLiterals();
            }
        }
        return ret;
    }

    private void simplifyTerm(PetrickGroup g) {
//        this.printPetrick();
//        g.printPetrick();       
        List<TermGroup> simplified = new ArrayList();
        for (TermGroup termGroup : group) {
            for (TermGroup otherTermGroup : g.getTermGroups()) {
                List<Term> newTerms = new ArrayList(termGroup.getTerms());
                for (Term t : otherTermGroup.getTerms()) {
                    boolean duplicate = false;
                    for (Term t2 : newTerms) {
                        if (t == t2) {
                            duplicate = true;
                        }
                    }
                    if (!duplicate) {
                        newTerms.add(t);
                    }
                }
                TermGroup newGroup = new TermGroup(newTerms);
                simplified.add(newGroup);
            }
        }
        group = simplified;
//        this.printPetrick();
        System.out.println();
    }

    public List<TermGroup> getTermGroups() {
        return group;
    }

    public void printPetrick() {
        for (TermGroup group : group) {
            for (Term t : group.getTerms()) {
                System.out.print("(" + t.getBytes() + ")");
            }
            System.out.print(" + ");
        }
        System.out.println();
    }

    public int getSize() {
        return group.size();
    }

    public TermGroup getGroup(int index) {
        return group.get(index);
    }

    public PetrickGroup() {
    }

    public void addTermGroup(TermGroup g) {
        group.add(g);
    }

}
