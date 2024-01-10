/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo22;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mark_2
 */
public class TermTable {

    private List<Term> terms;
    private List<TermGroup> groups;
    public List<Function> functions = new ArrayList();
    public boolean pos;

    public static int[] lastlist;

    private int length;
    private int depth;


    public TermTable(int length, int depth) {
        terms = new ArrayList();
        groups = new ArrayList();

        this.length = length;
        this.depth = depth;

        for (int i = 0; i < length + 1 - depth; i++) {
            groups.add(new TermGroup());
        }
    }

    public TermTable() {
        terms = new ArrayList();
        groups = new ArrayList();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of variables: ");
        this.length = controller.len;

        this.depth = 0;

        for (int i = 0; i < length + 1 - depth; i++) {
            groups.add(new TermGroup());
        }

        int numTerms = (int) Math.pow(2, length);
        for (int i = 0; i < numTerms; i++) {
            terms.add(new Term(length, i));
        }

        makeFunctions();
    }

    public boolean isPos() {
        return pos;
    }

    public void setPos(boolean pos) {
        this.pos = pos;
    }

    private void makeFunct(int function, List<Integer> values, List<Integer> donts, boolean pos) {
        List<Integer> usedValues;

        if (this.pos == pos) {
            usedValues = values;
        } else {
            usedValues = new ArrayList();
            for (int i = 0; i < Math.pow(2, length); i++) {
                boolean used = false;
                for (int value : values) {
                    if (i == value) {
                        used = true;
                    }
                }
                for (int dont : donts) {
                    if (i == dont) {
                        used = true;
                    }
                }
                if (!used) {
                    usedValues.add(i);
                }
            }
        }

        functions.add(new Function(usedValues));

        for (int value : usedValues) {
            terms.get(value).addFunction(new FunctionMember(function, false));
        }

        for (int dont : donts) {
            terms.get(dont).addFunction(new FunctionMember(function, true));
        }
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public List<Term> getTerms() {
        return terms;
    }

    private void makeFunctions() {
        System.out.println(lastlist);
       controller1 obj=new controller1();
        List<Integer> dos = new ArrayList();
        List<Integer> donts = new ArrayList();
        int[] ar = lastlist;
        List<Integer> inner = new ArrayList<>();
        String makeFunct = null;
        for (int i = 0; i < ar.length; i++) {
            dos.add(ar[i]);
        }
        /*for (int i = 0; i < ar.length; i++) {
            donts.add(0);
        }*/

            pos = false;

            for (int i = 0; i < dos.size(); i++) {
                makeFunct(i, dos, donts, false);
            }

            for (Term term : terms) {
                if (term.isUsed()) {
                    String str = "" + term.getBytes();
                    int ones = 0;
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == '1') {
                            ones++;
                        }
                    }
                    groups.get(ones).addTerm(term);
                }
            }

    }
    public String toString() {
        String ret = "";

        for (TermGroup group : groups) {
            boolean empty = true;
            for (Term term : group.getTerms()) {
                ret += term + "\n";
                empty = false;
            }
            if (!empty) {
                for (int i = 0; i < (3 * length + 10); i++) {
                    ret += "-";
                }
                ret += "\n";
            }
        }
        return ret;
    }

    public void addTermToGroup(Term term, int group) {
        groups.get(group).addTerm(term);
    }

    public TermGroup getGroup(int index) {
        return groups.get(index);
    }

    private void simplifyGroup(TermTable newTable, int index) {
        TermGroup group1 = groups.get(index);
        TermGroup group2 = groups.get(index + 1);
        for (int x = 0; x < group1.getSize(); x++) {
            Term term1 = group1.getTerm(x);
            for (int y = 0; y < group2.getSize(); y++) {
                Term term2 = group2.getTerm(y);
                Term newTerm = compareBytes(term1, term2);
                if (newTerm != null) {
                    TermGroup newGroup = newTable.getGroup(index);
                    boolean exists = false;
                    for (Term t : newGroup.getTerms()) {
                        if (newTerm.compareTerms(t)) {
                            exists = true;
                        }
                    }
                    if (!exists) {
                        newGroup.addTerm(newTerm);
                    }
                }
            }
        }
    }

    public List<TermGroup> getGroups() {
        return groups;
    }

    private Term compareBytes(Term t1, Term t2) {
        Term ret = null;

        List<FunctionMember> sharedFunctions = compareFunctions(t1, t2);

        if (sharedFunctions.size() > 0) {

            //checks of function if all terms are taken
//            if (sharedFunctions.size() == t1.getFunctions().size()) {
//                boolean check = true;
//                for (int i = 0; i < sharedFunctions.size(); i++) {
//                    if (!t1.getFunction(i).equals(sharedFunctions.get(i))) {
//                        check = false;
//                    }
//                }
//                if (check) {
//                    t1.check();
//                }
//            }
            //checks if term can be make and makes term
            int changedNum = 0;
            String newBytes = "";
            for (int i = 0; i < length; i++) {
                if (t1.getBytes().charAt(i) == t2.getBytes().charAt(i)) {
                    newBytes += t1.getBytes().charAt(i);
                } else {
                    newBytes += "-";
                    changedNum++;
                }
            }

            if (changedNum == 1) {

                if (t1.compareFunctions(sharedFunctions)) {
                    t1.check();
                }

                if (t2.compareFunctions(sharedFunctions)) {
                    t2.check();
                }

                List<Integer> combined = new ArrayList(t1.getTerms());
                combined.addAll(t2.getTerms());
                ret = new Term(length, newBytes, sharedFunctions, combined);
            }
        }
        return ret;
    }

    private List<FunctionMember> compareFunctions(Term t1, Term t2) {
        List<FunctionMember> ret = new ArrayList();
        for (FunctionMember f1 : t1.getFunctions()) {
            for (FunctionMember f2 : t2.getFunctions()) {
                if (f1.getFunction() == f2.getFunction()) {
                    if (f1.isDont() && f2.isDont()) {
                        ret.add(new FunctionMember(f1.getFunction(), true));
                    } else {
                        ret.add(new FunctionMember(f1.getFunction(), false));
                    }
                }
            }
        }
        return ret;
    }

    public boolean isEmpty() {
        for (TermGroup t : groups) {
            if (t.getSize() != 0) {
                return false;
            }
        }
        return true;
    }

    public List<TermTable> simplify() {
        boolean complete = true;

        List<TermTable> ret = new ArrayList();

        ret.add(this);
        TermTable simplified = new TermTable(length, depth + 1);

        simplified.setPos(pos);

        for (int i = 0; i < groups.size() - 1; i++) {
            simplifyGroup(simplified, i);
        }

        ret.add(simplified);

        return ret;
    }
}
