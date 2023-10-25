import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//imported from devoir 1

public class tassert {
     public static int calculateTASSERT(String fileName) {
        int tassert = 0;

        //le pattern.compile cherche pour des strings qui commence avec "assert" + des lettres entre A-z et finalment
        //qui fini avec une parenthese. OU fail() qui est aussi un assert mais ne commenece pas par assert.
        Pattern assertionPattern = Pattern.compile("(assert[A-Za-z]+\\()|fail\\(\\)");

        //lit ligne par ligne tout les lignes du fichier entré en parametre
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                //define matcher comme une expression pour chercher les ASSERTS
                Matcher matcher = assertionPattern.matcher(line);

                //cherche le pattern des ASSERTS et lorsqu'on trouve
                //incremente tassert.
                while (matcher.find()) {
                    tassert++;
                }
            }
        } catch (IOException e) {
            //probleme de lecture
            System.err.println("Erreur lecture du fichier : " + e.getMessage());
            System.exit(1);
        }

        return tassert;
    }
}

//sources (Regex-Matcher et Pattern) :
//pour Pattern  https://www.geeksforgeeks.org/pattern-compilestring-method-in-java-with-examples
//pour Matcher , incluant matcher.fin() : https://jenkov.com/tutorials/java-regex/matcher.html