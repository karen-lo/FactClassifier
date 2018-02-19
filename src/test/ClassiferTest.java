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
        for(Record r : records) {
            System.out.println(r.getFile_id());
            r.setPlaintiffsAndDefendants();
            ArrayList<Party> p = r.getPlaintiffs();
            ArrayList<Party> d = r.getDefendants();

            System.out.println("Plaintiffs: ");
            for(Party person : p) {
                System.out.print(person.getName() + " AKA ");
                person.setAliases();

                HashSet<String> aliases = person.getAliases();
                for(String a : aliases) {
                    System.out.print(a + ", ");
                }
                System.out.println();
            }

            System.out.println("Defendants: ");
            for(Party person : d) {
                System.out.print(person.getName() + " AKA ");
                person.setAliases();

                HashSet<String> aliases = person.getAliases();
                for(String a : aliases) {
                    System.out.print(a + ", ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }
}
