import java.io.File;
import java.io.IOException;

public class tpp {
    public static int MethodCountPackage(String packageName) {

        // Insert le package directory dans un File reçu par la ligne de commande
        File packageDir = new File(packageName);

        // Initialisation de la variable du nombre de @test total parmis les classes dans le package
        int totalTestCount = 0;
        
        // List tout les fichiers dans le package finissant par .java seulement , donc l'array javaFiles contient
        // Tout les fichiers dans le package

        File[] javaFiles = packageDir.listFiles((dir, name) -> name.endsWith(".java"));

        if (javaFiles != null) {
            for (File javaFile : javaFiles) {
                // Utilise TPC pour calculer le nombre de @test dans le fichier et retourne le total pour ce ficher java
                int testCount = tpc.methodCount(javaFile.getAbsolutePath());
                // System.out.println("File: " + javaFile.getName() + " - nombre de methode @Test : " + testCount); //afficher nom fichier et nb de test dans le fichier
                totalTestCount += testCount;
            }
        }

        return totalTestCount;
        }
    }

//source : https://stackoverflow.com/questions/28678026/how-can-i-get-all-class-files-in-a-specific-package-in-java?rq=3
//par mythbu.
//ressources : https://www.baeldung.com/java-find-all-classes-in-package