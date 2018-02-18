import java.io.*;
import main.FactClassifier;

public class ClassiferTest {
    public static void main(String[] args) throws FileNotFoundException {
        FactClassifier fc = new FactClassifier();
        fc.setRecords(args[0]);
    }
}
