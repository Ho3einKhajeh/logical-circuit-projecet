package com.example.demo22;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class controller {
    @FXML
    private AnchorPane profile;
    @FXML
    private AnchorPane three;
    @FXML
    private AnchorPane four;
    @FXML
    private AnchorPane five;
    @FXML
    private GridPane threegrid ;
    @FXML
    private  GridPane fourgrid;
    @FXML
    private  GridPane fivegrid1;
    @FXML
    private  GridPane fivegrid2;

    @FXML
    private TextField out3;
    @FXML
    private TextField out4;
    @FXML
    private TextField out5;
    @FXML
    private TextField out32;
    @FXML
    private TextField out42;
    @FXML
    private TextField out52;
    public static int[] lastlist1;

//    @FXML
    public static int len = 3;
    @FXML
    private int radif = 2 ;
    @FXML
    private int soton = 4 ;
    String[] ar = new String[radif*soton];
    String[] ar2 = new String[radif*soton];
    @FXML
    private String position = "three";
    @FXML
    private String[] arrayS = new String[radif*soton];

    //    =============================
    public void initialize(){
        profile.setVisible(true);
        three.setVisible(true);
        hidefive();
        hidefour();
    }
//    =================================
    @FXML
    private void hidefour(){
        four.setVisible(false);
    }
    @FXML
    private void hidefive(){
        five.setVisible(false);
    }
//================================== handles
    @FXML
    private void  handlethreebutton(){
    radif = 2;
    soton=4;
    len=3;
    position = "three";
        System.out.println(len);
    four.setVisible(false);
    three.setVisible(true);
    five.setVisible(false);
    profile.setVisible(true);
}
    @FXML
    private void  handlefourbutton(){
        radif = 4;
        soton = 4;
        len = 4;
        System.out.println(len);
        position = "four";
        four.setVisible(true);
        three.setVisible(false);
        five.setVisible(false);
        profile.setVisible(true);
    }

    @FXML
    private void  handlefivebutton(){
//        num = 5;
        len = 5;
        position = "five";
        System.out.println(len);
        four.setVisible(false);
        three.setVisible(false);
        five.setVisible(true);
        profile.setVisible(true);
    }
//    =====================================

    public void get_id(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        get_id1();

    }
    public static String[] merge(String a[], String b[]) {
    String[] c = new String[a.length+b.length];
    for (int i = 0 ; i<a.length;i++){
        c[i]=a[i];
    }for (int j = 0 ; j<a.length;j++){
        c[a.length+j]=b[j];
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
//    =====================================get id function get abcd of midterms
public int[] get_id1() {

    String[] ar = new String[radif * soton];
    String[] ar2 = new String[radif * soton];

    switch (len) {
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

//=========================================
    public  void mn() {
    TermTable t = new TermTable();
    List<TermTable> termTables = new ArrayList();
    termTables.add(t);
//        printTables(termTables);
    simplify(termTables);

}

    public static void simplify(List<TermTable> t) {
        List<TermTable> newTerms = t.get(t.size() - 1).simplify();
        if (!newTerms.get(1).isEmpty()) {
            t.remove(t.size() - 1);
            t.addAll(newTerms);
//            printTables(t);
            simplify(t);
        } else {
            List<TermGroup> implicants = new ArrayList();
            List<Function> functions = new ArrayList();
            for (int i = t.size() - 1; i >= 0; i--) {
                TermGroup group = new TermGroup();
                TermTable table = t.get(i);

                for (TermGroup termGroup : table.getGroups()) {
                    for(Term term: termGroup.getTerms()){
                        if(!term.isChecked())
                            group.addTerm(term);
                    }
                }

                implicants.add(group);
                functions = table.getFunctions();
            }
//            System.out.println();
            ImplicantTable implicantTable = new ImplicantTable(functions, implicants, t.get(0).pos);
//            implicantTable.printTable();
            implicantTable.simplifyTable();

            implicantTable.printFinalFunctions();
        }
    }
    public void print(){
        print1();
    }
    public void print1(){

        switch (len){

            case 3:
                out32.setText(Arrays.toString(TermTable.lastlist));
                out3.setText(ImplicantTable.Lastout);

                break;
            case 4:
                out42.setText(Arrays.toString(TermTable.lastlist));
                out4.setText(ImplicantTable.Lastout);
                break;
            case 5:
                out52.setText(Arrays.toString(TermTable.lastlist));
                out5.setText(ImplicantTable.Lastout);
                break;

        }

    }
}


