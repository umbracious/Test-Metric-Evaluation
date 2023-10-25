// Counts lines of code

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class cloc {
    public static int calculateCLOC(String fileName) {
        int cloc = 0;

        // Read the lines of the file one by one
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read next line and trim the empty spaces in the indentation
            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (!line.isEmpty()) {
                    cloc++;
                }
            }

        } catch (IOException e) {

            // Problem with reading file
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            System.exit(1);
        }

        return cloc;
    }
}
