import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public void main(String[] args){
        String path = args[0];
        File directory = new File(path);

        // Invalid directory argument
        if (!directory.exists() || !directory.isDirectory()){
            System.err.println("Incorrect directory: java <entry-dir>");
            System.exit(1);
        }

        // Find all test files within directory
        List<File> files = findFiles(directory);
    }

    public static List<File> findFiles(File directory){
        List<File> files = new ArrayList<>();

        findFilesRec(directory,files);

        return files;
    }

    // Recursive in place function that adds java files from every sub-directory to the list
    public static void findFilesRec(File directory, List<File> files){

        File[] dirFiles = directory.listFiles();

        if (files != null){
            for (File file: dirFiles){

                // If files is a directory, recursive call for all files inside it
                if (file.isDirectory()){
                    findFilesRec(file, files);
                }

                // Adds file to list if it's a .java file with test somewhere in the name
                else if (file.getName().toLowerCase().endsWith(".java")){
                    files.add(file);
                }
            }
        }

    }

}
