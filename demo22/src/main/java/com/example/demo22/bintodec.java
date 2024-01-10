package com.example.demo22;

import java.util.Arrays;

public class bintodec {
    public   int[]  bintodec(String[] sarray){
        int[] newArray = new int[sarray.length];
        for (int i = 0; i < sarray.length; i++) {
            int decimal = 0;
            int power = sarray[i].length() - 1;

            for (int j = 0; j < sarray[i].length(); j++) {
                if (sarray[i].charAt(j) == '1') {
                    decimal += Math.pow(2, power);
                }
                power--;
            }
            newArray[i] = decimal;
        }
        return newArray;
    }

}
