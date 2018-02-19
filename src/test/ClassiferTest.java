import java.io.*;
import java.util.ArrayList;

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
            for(int i=0; i<p.size(); i++) {
                System.out.print(p.get(i).getName() + ", ");
            }
            System.out.println("\nDefendants: ");
            for(int i=0; i<d.size(); i++) {
                System.out.print(d.get(i).getName() + ", ");
            }
            System.out.println("\n");
        }
    }
}
