//Imported from TLS of TP 1

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main (String[] args){

        // Only 1 argument, prints to command line if valid path
        if (args.length == 1) {
            String path = args[0];
            File directory = new File(path);

            // Invalid directory argument
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.println("Incorrect directory: java <entry-dir>");
                System.exit(1);
            }

            // Find every .java file and every test file in directory and sub-directories
            List<File> javaFiles = findJavaFiles(directory);
            List<File> testFiles = findTestFiles(directory);


            List<Integer> tlocList = new ArrayList<>();
            List<Float> tcmpList = new ArrayList<>();
            Set<String> packages = new HashSet<>();


            float testFileQuantity = (float) testFiles.size();
            float dcSum = 0;
            float tassertSum = 0;
            int tropCompCounter = 0;
            int totalMethodcount = 0;
            float totalAvgCC = 0;

            for (File file : testFiles) {

                if (file.isFile()){
                    String currFilePath = file.getPath();
                    String pkgName=currFilePath.substring(currFilePath.indexOf("src"), currFilePath.lastIndexOf('\\'));
                    packages.add(pkgName);
                }

                HashMap info = getInfo(file, path);
                int currTLOC = (int) info.get("tloc");
                float currTCMP = (float) info.get("tcmp");

                tlocList.add(currTLOC);
                tcmpList.add(currTCMP);

            }

            // Calculate TPP
            int packageQuantity = packages.size();
            int totalTPP = 0;
            for (String pkg: packages){
                totalTPP = totalTPP + tpp.MethodCountPackage(path + '\\' + pkg);
            }
            float avgTPP = (float) totalTPP/packageQuantity;

            Collections.sort(tlocList);
            Collections.sort(tcmpList);

            for (File file: testFiles){

                HashMap info = getInfo(file, path);
                int currTLOC = (int) info.get("tloc");
                int currTASSERT = (int) info.get("tassert");
                float currTCMP = (float) currTLOC/ (float) currTASSERT;
                float currDC = (float) info.get("dc");
                int currMethodCount = (int) info.get("methodCount");
                float currAvgCC = (float) info.get("averageCC");

                float tlocPercentile = (float) ((tlocList.indexOf(currTLOC)+1)*100)/ testFileQuantity;
                float tcmpPercentile = (float) ((tcmpList.indexOf(currTCMP)+1)*100)/ testFileQuantity;

                // If both percentiles exceed the threshold, write the files info to the .csv file
                float percentileThreshold = 75;

                // If both percentiles exceed the threshold, print out the file in question
                if (tlocPercentile >= percentileThreshold && tcmpPercentile >= percentileThreshold){
                    tropCompCounter++;
                }

                dcSum = dcSum + currDC;
                tassertSum = tassertSum + currTASSERT;
                totalMethodcount = totalMethodcount + currMethodCount;
                totalAvgCC = totalAvgCC + currAvgCC;

            }
            System.out.println("Average TPP: " + avgTPP);
            System.out.println("Average CC: " + totalAvgCC/testFileQuantity);
            System.out.println("Average TPC: " + totalMethodcount/testFileQuantity);
            System.out.println("TASSERT moyen: " + tassertSum/testFileQuantity);
            System.out.println("% des fichiers test: " + 100*(float)(testFiles.size())/javaFiles.size() + "%");
            System.out.println("% des fichiers trop compliqués: " +  100*(float)tropCompCounter/testFileQuantity + "%");
            System.out.println("Densité de commentaires moyenne: " + 100*dcSum/testFileQuantity + "%");

        }

    }

    // Find every test java file within the directory and its sub-directories
    public static List<File> findTestFiles(File directory){
        List<File> files = new ArrayList<>();

        testFilesRec(directory,files);

        return files;
    }

    // Recursive in place function that adds test java files from every sub-directory to the list
    public static void testFilesRec(File directory, List<File> files){

        File[] dirFiles = directory.listFiles();

        if (files != null){
            for (File file: dirFiles){

                // If files is a directory, recursive call for all files inside it
                if (file.isDirectory()){
                    testFilesRec(file, files);
                }

                // Adds file to list if it's a .java file with test somewhere in the name
                else if (file.getName().toLowerCase().endsWith(".java")
                        && tassert.calculateTASSERT(file.getPath()) > 0){
                    files.add(file);
                }
            }
        }

    }

    // Same as findTestFiles but finds every file in directory
    public static List<File> findJavaFiles(File directory){
        List<File> files = new ArrayList<>();

        javaFilesRec(directory,files);

        return files;
    }

    // Recursion for findJavaFiles method
    public static void javaFilesRec(File directory, List<File> files){
        File[] dirFiles = directory.listFiles();

        if (files != null){
            for (File file: dirFiles){

                // If files is a directory, recursive call for all files inside it
                if (file.isDirectory()){
                    javaFilesRec(file, files);
                }

                // Adds file to list if it's a .java file with test somewhere in the name
                else if (file.getName().toLowerCase().endsWith(".java")){
                    files.add(file);
                }
            }
        }
    }

    public static String getRelativePath(String origin, String filePath){
        String relativePath = filePath.substring(origin.length());

        return relativePath;
    }

    public static HashMap getInfo(File file, String origin){
        HashMap dictionary= new HashMap();

        // Get relative path
        String filePath = file.getAbsolutePath();
        dictionary.put("relativePath", getRelativePath(origin,filePath));

        // Get package and class name
        String pkgName = getPkgName(file);
        dictionary.put("package", getPkgName(file));
        dictionary.put("className",removeExtension(file.getName()));

        // Get tloc
        int tlocVal = tloc.calculateTLOC(file.getPath());
        dictionary.put("tloc",tlocVal);

        // Get tassert
        int tassertVal = tassert.calculateTASSERT(file.getPath());
        dictionary.put("tassert",tassertVal);

        // Get tcmp
        float tcmpVal = tcmp.calculateTCMP(file.getPath());
        dictionary.put("tcmp",tcmpVal);

        float dcVal = dc.calculateDC(file.getPath());
        dictionary.put("dc",dcVal);

        int methodCountVal = tpc.methodCount(file.getPath());
        dictionary.put("methodCount",methodCountVal);

        float averageCC = cc.calculateAvgCC(file.getPath());
        dictionary.put("averageCC",averageCC);

        return dictionary;
    }

    public static String removeExtension(String file){
        return file.replaceFirst("[.][^.]+$", "");
    }

    // Takes a file and returns the package name within a file
    public static String getPkgName(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Pattern packagePattern = Pattern.compile("^\\s*package\\s+([^\\s;]+)\\s*;");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = packagePattern.matcher(line);
                if (matcher.find()) {
                    String packageName = matcher.group(1);
                    return packageName.replace('/', '.');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}

// Source for CSV Writer: https://springhow.com/java-write-csv/
// Source for getting Class name: https://stackoverflow.com/questions/23017557/how-to-get-class-name-of-any-java-file
// Used chatGPT for help to write getPkgName function because I couldn't figure out the regex