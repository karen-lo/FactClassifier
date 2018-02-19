import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import main.FactClassifier;
import main.Party;
import main.Record;

public class ClassiferTest {
    public static void main(String[] args) throws FileNotFoundException {
        FactClassifier fc = new FactClassifier();
        fc.setRecords(args[0]);

        Record[] records = fc.getRecords();
        for(Record rec : records) {
            System.out.println(rec.getFile_id());
            rec.setParties();
            ArrayList<Party> c = rec.getComplainants();
            ArrayList<Party> r = rec.getRespondants();

            System.out.println("Those suing: ");
            for(Party person : c) {
                System.out.print(person.getName() + " is a " + person.getRole() + ", AKA ");
                person.setAliases();

                HashSet<String> aliases = person.getAliases();
                for(String a : aliases) {
                    System.out.print(a + ", ");
                }
                System.out.println();
            }

            System.out.println("Those being sued: ");
            for(Party person : r) {
                System.out.print(person.getName() + " is a " + person.getRole() + ", AKA ");
                person.setAliases();

                HashSet<String> aliases = person.getAliases();
                for(String a : aliases) {
                    System.out.print(a + ", ");
                }
                System.out.println();
            }
            System.out.println("\n");

            rec.classifyHolding();
            String[] h_class = rec.getHoldingClassification();
            System.out.println("Holding Classifications:");
            for(String sent_class : h_class) {
                System.out.println(sent_class);
            }

            rec.classifyFacts();
            String[] f_class = rec.getFactsClassification();
            System.out.println("Facts Classifications:");
            for(String fact_class : f_class) {
                System.out.println(fact_class);
            }
        }
    }
}
