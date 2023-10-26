import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TPC {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("erreur <JavaFile.java>");
            System.exit(1);
        }

        String fileName = args[0];
        int testCount = MethodCount(fileName);

        System.out.println("Nombre de methode @Test dans  " + fileName + ": " + testCount);
    }

}
