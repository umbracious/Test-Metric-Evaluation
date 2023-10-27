import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tpc {

    public static int methodCount(String fileName) {
        //nombre de test variable initiale
        int testCount = 0;

        //lire ligne par ligne et ajouter le tout dans une StringBuilder nommé codeBlock
        //@bool TestMethod pour presence de @Test dans un code et incremente testcount
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            StringBuilder codeBlock = new StringBuilder();
            boolean TestMethod = false;

            while ((line = br.readLine()) != null) {
                codeBlock.append(line).append("\n");

                //trouve @Test dans le ligne du code
                if (line.contains("@Test")) {
                    TestMethod = true;
                }

                //TestMethod est True cherche la fermeture de la methode test et incremente testCount
                if (TestMethod && line.matches(".*\\)\\s*\\{\\s*")) {
                    TestMethod = false;
                    testCount++;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return testCount;
    }

}

//Source,Ressource :  https://stackoverflow.com/questions/11066552/count-the-amount-of-times-a-string-appears-in-a-file#:~:text=If%20you%20have%20got%20to,to%20get%20your%20total%20occurrence.
// https://stackoverflow.com/questions/9689860/counting-the-occurence-of-a-particular-string-in-a-file?rq=3