// Calculate cyclomatic complexity

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cc {

    // Get method count of a file
    // Regex from: https://stackoverflow.com/questions/68633/regex-that-will-match-a-java-method-declaration
    public static int methodCount(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder javaCode = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                javaCode.append(line).append("\n");
            }

            reader.close();

            // Define a regular expression pattern to match Java method declarations
            String regex = "(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
            Pattern pattern = Pattern.compile(regex);
            Matcher methodMatcher = pattern.matcher(javaCode.toString());

            int methodCount = 0;
            while (methodMatcher.find()) {
                methodCount++;
            }

            return methodCount;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return -1; // error
    }

}
