package com.example.demo22;

import com.example.demo22.ImplicantTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;
import org.controlsfx.control.spreadsheet.Grid;

import java.util.ArrayList;
import java.util.Arrays;


public class controller1 {

    @FXML
    private int radif;
    @FXML
    private int soton;


    @FXML
    private TextField out3;
    @FXML
    private TextField out4;

    @FXML
    private TextField out5;



    @FXML
    private TabPane fulltab;


    @FXML
    private AnchorPane tab3;



    @FXML
    private GridPane threegrid;
    @FXML
    private GridPane fourgrid;

    @FXML
    private GridPane fivegrid1;
    @FXML
    private GridPane fivegrid2;
    //    @FXML
    public static int len;

    public static int[] lastlist1;

    @FXML
    public    static  int tab;
    @FXML
    private String[] arrayS = new String[radif * soton];

    //make each tab active
    public void start() {

        fulltab.setVisible(true);
        if (tab3.isVisible() == true) {
            tab = 3;
            System.out.println(3);
        }
    }

    public void activate3() {
        tab = 3;
        System.out.println(3);
        radif = 2;
        soton = 4;
    }

    public void activate4() {
        tab = 4;
        radif = 4;
        soton = 4;
        System.out.println(4);
    }

    public void chektab(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        chektab1();

    }

    public void activate5() {
        tab = 5;
        radif = 4;
        soton = 4;
        System.out.println(5);
    }

    public static String[] merge(String a[], String b[]) {
        String[] c = new String[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }
        for (int j = 0; j < a.length; j++) {
            c[a.length + j] = b[j];
        }
        return c;
    }

    //    =====================================
    public static ArrayList<String> removeNull(String[] array) {
        ArrayList<String> newArray = new ArrayList<>();

        for (String s : array) {
            if (s != null && !s.equals("")) {
                newArray.add(s);
            }
        }

        return newArray;
    }

    public int[] getLastlist1() {
        return lastlist1;
    }

    public int[] chektab1() {

        String[] ar = new String[radif * soton];
        String[] ar2 = new String[radif * soton];

        switch (tab) {
            case 3:
                int count = 0;
                for (javafx.scene.Node node : threegrid.getChildren()) {
                    if (node instanceof TextField textField) {

                        try {

                            if (1 == Integer.parseInt(textField.getText())) {
                                String value = textField.getId();

                                ar[count] = value;
                            }

                            count++;

                            if (count > (radif * soton)) {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                arrayS = ar;
                arrayS = removeNull(arrayS).toArray(new String[0]);
                break;
            case 4:
                count = 0;

                for (javafx.scene.Node node : fourgrid.getChildren()) {
                    if (node instanceof TextField textField) {

                        try {

                            if (1 == Integer.parseInt(textField.getText())) {
                                String value = textField.getId();
                                System.out.println(value);
                                ar[count] = value;
                            }

                            count++;

                            if (count > (radif * soton)) {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                arrayS = ar;
                arrayS = removeNull(arrayS).toArray(new String[0]);

                break;
            case 5:
                count = 0;

                for (javafx.scene.Node node: fivegrid1.getChildren()) {
                    if (node instanceof TextField) {
                        TextField textField = (TextField) node;
                        try {
                            String value = textField.getId();
                            if (1 == Integer.parseInt(textField.getText()))
                                ar[count] = value;
                            count++;

                            if (count > (radif * soton)) {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (javafx.scene.Node node : fivegrid2.getChildren()) {
                    if (node instanceof TextField) {
                        TextField textField = (TextField) node;
                        try {
                            String value = textField.getId();
                            if (1 == Integer.parseInt(textField.getText()))
                                ar2[count] = value;
                            count++;

                            if (count > (radif * soton)) {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                arrayS = merge(ar, ar2);
                arrayS = removeNull(arrayS).toArray(new String[0]);
                break;
        }
        System.out.println(Arrays.deepToString(arrayS));
        String[] s = arrayS;
        System.out.println(Arrays.deepToString(s));

        String[] newArray = new String[s.length];
        for (int i = 0; i < s.length; i++) {
            String g = "";
            for (int j = 0; j < s[i].length(); j++) {
                if (Character.isUpperCase(s[i].charAt(j))) {
                    g += "1";
                } else {
                    g += "0";
                }
            }
            newArray[i] = g;
        }

        QM qm = new QM();
        bintodec obj = new bintodec();

        int[] a = obj.bintodec(newArray);
        System.out.println((Arrays.toString(obj.bintodec(newArray))));
        TermTable.lastlist = a;
        QM.main();


        return a;


    }


    public void print(){
        print1();
    }

    public void print1(){

        switch (tab){

            case 3:
                out3.setText(ImplicantTable.Lastout);

                break;
            case 4:
                out4.setText(ImplicantTable.Lastout);
                break;
            case 5:
                out5.setText(ImplicantTable.Lastout);
                break;

        }

    }

}







