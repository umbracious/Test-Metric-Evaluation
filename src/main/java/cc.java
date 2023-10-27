// Calculate cyclomatic complexity

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cc {

    // Find average cyclomatic complexity of the methods in a file
    // Variation of method count in tpc class
    // Regex from: https://stackoverflow.com/questions/68633/regex-that-will-match-a-java-method-declaration
    public static float calculateAvgCC(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder code = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                code.append(line).append("\n");
            }

            reader.close();

            // Define a regular expression pattern to match Java method declarations
            String regex = "(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
            Pattern pattern = Pattern.compile(regex);
            Matcher methodMatcher = pattern.matcher(code.toString());



            int methodCount = 0;
            int ifWhileCount = 0;
            while (methodMatcher.find()) {
                methodCount++;

                // Separate the body of the method using its start and end indexes
                int methodStartIndex = methodMatcher.start();
                int methodEndIndex = findMethodEndIndex(code.toString(), methodStartIndex);
                String methodCode = code.substring(methodStartIndex, methodEndIndex);

                // Define a regex pattern to match if and while statements
                // Regex defined using online regex generator
                String regex2 = "(if\\s*\\(|while\\s*\\()";
                Pattern pattern2 = Pattern.compile(regex2);
                Matcher ifWhileMatcher = pattern2.matcher(methodCode);


                while (ifWhileMatcher.find()){
                    ifWhileCount++;

                }

            }

            float complexity = (float) (ifWhileCount + methodCount)/methodCount;
            return complexity;

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return -1; // Should never get this return statement, just put it in to avoid error
    }

    private static int findMethodEndIndex(String code, int startIdx) {
        int bracketCounter = 1;
        int currIdx = startIdx + 1;

        while (bracketCounter > 0 && currIdx < code.length()) {
            char c = code.charAt(currIdx);

            if (c == '{') {
                bracketCounter++;
            } else if (c == '}') {
                bracketCounter--; // If counter goes down to 0, break out of while loop and return end index
            }

            currIdx++;
        }

        return currIdx;
    }

}
