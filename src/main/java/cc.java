// Calculate cyclomatic complexity

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class cc {

    // Get method count using file name
    // Source: https://stackoverflow.com/questions/63346839/get-method-as-a-file-from-java-file
    public static int methodCount(String fileName) {

        JavaMethodParser jmp = null;

        try {
            Scanner input = new Scanner(new File(fileName));
            String classContents = input.useDelimiter("\\Z").next().trim();

            jmp = new JavaMethodParser(classContents);

            input.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return jmp.countMethods();
    }
}
