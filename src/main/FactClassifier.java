package main;

import java.io.*;
import java.util.*;

public class FactClassifier {

    public static void main(String[] args) throws java.io.FileNotFoundException {
        Scanner input = new Scanner(new File ("data/admin_lit.aql"));
        String answer = input.nextLine();
        System.out.println(answer);
    }

}