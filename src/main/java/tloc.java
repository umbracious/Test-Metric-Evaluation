import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Imported from devoir 1

// This code doesn't work if the java file being read uses an indicator of comment as a string
// For example, if the file uses a String s = "/*"; the code will not work properly

public class tloc {
     public static int calculateTLOC(String fileName) {
        int tloc = 0;

        // Read the lines of the file one by one
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            Boolean isComment = false;

            // Read next line and trim the empty spaces in the indentation
            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (!isComment) {

                    // Ignore empty lines and comment lines that start with "//"
                    if (line.isEmpty() || line.startsWith("//")) {
                        continue;
                    }

                    // Check if line contains start of comment "/*"
                    if (line.contains("/*")) {

                        // Count current line if comment doesn't start at the beginning of line
                        if (!line.startsWith("/*")) {
                            tloc++;
                        }

                        isComment = true;

                        // If comment line closes in the same line then next line isn't a comment
                        if (line.contains("*/") && (line.indexOf("*/")>line.indexOf("/*"))){
                            isComment = false;
                        }

                        continue;
                    }

                    tloc++;

                } else {
                    if (line.endsWith("*/")){ // End of comment -> next line isn't comment
                        isComment = false;
                    }
                }
            }

        } catch (IOException e) {

            // Problem with reading file
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            System.exit(1);
        }

        return tloc;
    }
}

