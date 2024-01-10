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
public class ImplicantTable {

    public  static String  Lastout;

    private List<Function> functions;
    private List<TermGroup> implicants;
    private List<Term> usedImplicants;
    private int size;
    private boolean pos;

    public ImplicantTable(List<Function> functions, List<TermGroup> implicants, boolean pos) {
        this.functions = functions;
        this.implicants = implicants;
        usedImplicants = new ArrayList();
        this.pos = pos;
        setLength();
    }

    public boolean isPos() {
        return pos;
    }

    public void setPos(boolean pos) {
        this.pos = pos;
    }

    private PetrickGroup generatePetrick() {
        List<PetrickGroup> petricks = new ArrayList();
        for (int i = 0; i < functions.size(); i++) {
            for (int value : functions.get(i).getTerms()) {
                List<Term> termsInFunctionMember = new ArrayList();
                for (int groupNum = implicants.size() - 1; groupNum >= 0; groupNum--) {
                    for (Term implicant : implicants.get(groupNum).getTerms()) {
                        if (implicant.fillsTerm(i, value)) {
                            termsInFunctionMember.add(implicant);
                        }
                    }
                }
                TermGroup t = new TermGroup(termsInFunctionMember);
                petricks.add(new PetrickGroup(t));
            }
        }
        return new PetrickGroup(petricks);
    }

    private void setLength() {
        size = 0;
        for (Function f : functions) {
            size += f.getLength() * 3 + 1;
        }
    }

    public void printTable() {
        for (Function function : functions) {
            for (int term : function.getTerms()) {
                System.out.print(term);
                if (term < 10) {
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.print("|");
        }
        System.out.println();

        printDivide();

        for (TermGroup group : implicants) {
            for (Term implicant : group.getTerms()) {
                printRow(implicant);
            }
            printDivide();
        }
    }

    private void printDivide() {
        for (int i = 0; i < size; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private void printRow(Term implicant) {
        for (int i = 0; i < functions.size(); i++) {
            Function function = functions.get(i);
            for (int term : function.getTerms()) {
                if (implicant.fillsTerm(i, term)) {
                    System.out.print("X |");
                } else {
                    System.out.print("  |");
                }
            }
            System.out.print("|");
        }
        System.out.println("  " + implicant.getBytes());

        printDivide();
    }

    public boolean simplifyTable() {
        boolean changed = false;

//        while (removePrimeImplicant()) {
//            changed = true;
//            System.out.println();
//            System.out.println();
//            System.out.println(usedImplicants.get(usedImplicants.size() - 1).getBytes());
//            printTable();
//        }

//        while (removeCoveredRows()) {
//            changed = true;
//            System.out.println("\nRow Removed");
//            printTable();
//        }
        removeCoveredRows();
//        while (removeCoveringColumns()) {
//            changed = true;
//        }
//        System.out.println("\nColumns Removed");
//        printTable();

        System.out.println("Necessary Implicants:");
        for (Term t : usedImplicants) {
            System.out.println(t.getBytes());
        }
        addToFunctions();
//        for (TermGroup group : implicants) {
////            for (Term t : group.getTerms()) {
////                addToFunctions(t);
////                System.out.println(t.getBytes());
////            }
////        }

        return changed;
    }

    private boolean removeCoveringColumns() {
        for (int i = 0; i < functions.size(); i++) {
            Function function = functions.get(i);
            int indexCounter = 0;
            for (int value : function.getTerms()) {
                boolean covered = true;
                for (int value2 : function.getTerms()) {
                    for (TermGroup group : implicants) {
                        for (Term term : group.getTerms()) {
                            if ((value == value2) || (term.fillsTerm(i, value2) && !term.fillsTerm(i, value))) {
                                covered = false;
                            }
                        }
                    }
                }
                if (covered) {
                    function.deleteTerm(indexCounter);
                    return true;
                }
                indexCounter++;
            }
        }

        return false;
    }

    private void deleteColumns(Term term) {
        List<Function> newFunctions = new ArrayList();
        for (int i = 0; i < functions.size(); i++) {
            Function f = new Function();
            f.setNecessaryImplicants(functions.get(i).getNecessaryImplicants());
            newFunctions.add(f);

            for (int value : functions.get(i).getTerms()) {
                if (!term.fillsTerm(i, value)) {
                    newFunctions.get(newFunctions.size() - 1).addTerm(value);
                }
            }
        }
        functions = newFunctions;
        setLength();
    }

    private boolean removePrimeImplicant() {
        for (int i = 0; i < functions.size(); i++) {
            for (int value : functions.get(i).getTerms()) {
                for (TermGroup group : implicants) {
                    for (Term implicant : group.getTerms()) {
                        if (implicant.fillsTerm(i, value)) {
                            boolean prime = true;
                            for (TermGroup group2 : implicants) {
                                for (Term implicant2 : group2.getTerms()) {
                                    if (implicant != implicant2 && implicant2.fillsTerm(i, value)) {
                                        prime = false;
                                    }
                                }
                            }
                            if (prime) {
                                group.removeTerm(implicant);
//                                addToFunctions(implicant);
                                deleteColumns(implicant);
                                usedImplicants.add(implicant);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void addToFunctions() {
        for (Term implicant : usedImplicants) {
            for (int i = 0; i < functions.size(); i++) {
                boolean functionCovered = true;
                for (int value : functions.get(i).getTerms()) {
                    boolean columnCovered = true;
                    if (implicant.fillsTerm(i, value)) {
                        columnCovered = false;
                        for (Term implicant2 : usedImplicants) {
                            if (implicant != implicant2 && implicant2.fillsTerm(i, value)) {
                                columnCovered = true;
                            }
                        }
                        if (!columnCovered) {
                            functionCovered = false;
                        }
                    }
                }
                if (!functionCovered) {
                    functions.get(i).addImplicant(implicant);
                }
            }
        }
//        for (int i = 0; i < functions.size(); i++) {
//            boolean added = false;
//            for (int y = 0; y < functions.get(i).getTerms().size() && !added; y++) {
//                int term = functions.get(i).getTerms().get(y);
//                if (t.fillsTerm(i, term)) {
//                    added = true;
//                    functions.get(i).addImplicant(t);
//                }
//            }
//        }
    }

    private void removeCoveredRows() {
        PetrickGroup petrick = generatePetrick();
        usedImplicants.addAll(petrick.getSmallest().getTerms());
    }

//    private boolean removeCoveredRows() {
//        for (int groupNum = implicants.size() - 1; groupNum >= 0; groupNum--) {
//            for (int numFunctions = 1; numFunctions <= functions.size(); numFunctions++) {
//                for (Term implicant : implicants.get(groupNum).getTerms()) {
//                    if (implicant.getFunctions().size() == numFunctions) {
//                        boolean covered = true;
//                        for (int i = 0; i < functions.size(); i++) {
//                            for (int value : functions.get(i).getTerms()) {
//                                boolean columnCovered = true;
//                                if (implicant.fillsTerm(i, value)) {
//                                    columnCovered = false;
//                                    for (TermGroup group2 : implicants) {
//                                        for (Term implicant2 : group2.getTerms()) {
//                                            if (implicant != implicant2 && implicant2.fillsTerm(i, value)) {
//                                                columnCovered = true;
//                                            }
//                                        }
//                                    }
//                                    if (!columnCovered) {
//                                        covered = false;
//                                    }
//                                }
//                            }
//                        }
//                        if (covered) {
//                            implicants.get(groupNum).removeTerm(implicant);
//                            System.out.println();
//                            System.out.println();
//                            System.out.println(implicant.getBytes());
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//}
//    private boolean removeCoveredRows() {
//        for (int groupNum = implicants.size() - 1; groupNum >= 0; groupNum--) {
//            for (Term implicant : implicants.get(groupNum).getTerms()) {
//                boolean covered = true;
//                for (int i = 0; i < functions.size(); i++) {
//                    for (int value : functions.get(i).getTerms()) {
//                        boolean columnCovered = true;
//                        if (implicant.fillsTerm(i, value)) {
//                            columnCovered = false;
//                            for (TermGroup group2 : implicants) {
//                                for (Term implicant2 : group2.getTerms()) {
//                                    if (implicant != implicant2 && implicant2.fillsTerm(i, value)) {
//                                        columnCovered = true;
//                                    }
//                                }
//                            }
//                            if (!columnCovered) {
//                                covered = false;
//                            }
//                        }
//                    }
//                }
//                if (covered) {
//                    implicants.get(groupNum).removeTerm(implicant);
//                    System.out.println();
//                    System.out.println();
//                    System.out.println(implicant.getBytes());
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//        private boolean removeCoveredRows() {
//            for (int groupNum = implicants.size() - 1; groupNum >= 0; groupNum--) {
//                for (Term implicant : implicants.get(groupNum).getTerms()) {
//                    for (TermGroup group2 : implicants) {
//                        for (Term implicant2 : group2.getTerms()) {
//                            boolean covered = true;
//                            for (int i = 0; i < functions.size(); i++) {
//                                for (int value : functions.get(i).getTerms()) {
//                                    if (implicant == implicant2 || (implicant.fillsTerm(i, value) && !implicant2.fillsTerm(i, value))) {
//                                        covered = false;
//                                    }
//                                }
//                            }
//                            if (covered) {
//                                implicants.get(groupNum).removeTerm(implicant);
//                                System.out.println();
//                                System.out.println();
//                                System.out.println(implicant.getBytes());
//                                return true;
//                            }
//                        }
//                    }
//                }
//            }
//            return false;
//        }
    public void printFinalFunctions() {
        System.out.println("\n");
        int length = functions.size();
        if (pos) {
          //  printPosFromBinary();
        } else {
            printSopFromBinary();
        }
    }

//    public void printPosFromBinary(String binary) {
//        for (int y = 0; y < functions.get(i).getNecessaryImplicants().size(); y++) {
//            boolean entered = false;
//            for (int i = 0; i < binary.length(); i++) {
//                char c = binary.toCharArray()[i];
//                if (c != '-') {
//                    if (entered) {
//                        System.out.print("*");
//                    }
//                    if (c == '0') {
//                        System.out.print("!" + getChar(i, true));
//                    } else {
//                        System.out.print(getChar(i, true));
//                    }
//                    entered = true;
//                }
//            }
//        }
//    }
    public void printSopFromBinary() {
     //   for (int i = 0; i < functions.size(); i++) {
            System.out.print("f" + " = ");
            StringBuilder s= new StringBuilder();
            for (int y = 0; y < functions.get(0).getNecessaryImplicants().size(); y++) {
                String binary = functions.get(0).getNecessaryImplicants().get(y).getBytes();
                boolean entered = false;
                for (int x = 0; x < binary.length(); x++) {
                    char c = binary.toCharArray()[x];
                    if (c != '-') {
                        if (entered) {
                            System.out.print("*");
                            s.append("*");
                        }
                        if (c != '1') {
                            System.out.print("!" + getChar(x, true));
                            s.append("!"+getChar(x, true));
                        } else {
                            System.out.print(getChar(x, true));
                            s.append(getChar(x, true));
                        }
                        entered = true;
                    }
                }
                if (y < functions.get(0).getNecessaryImplicants().size() - 1) {
                    System.out.print(" + ");
                    s.append(" + ");
                }
            }
            System.out.println();
        System.out.println(s);
            Lastout=s.toString();

    }


    public String getChar(int term, boolean upperCase) {
        char[] upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] lowerAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        if (upperCase) {
            return Character.toString(upperAlphabet[term]);
        } else {
            return Character.toString(lowerAlphabet[term]);
        }
    }

}
