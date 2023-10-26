import java.io.File;

public class TPP {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Erreur : java TPP <PackageDirectory>");
            System.exit(1);
        }

        //insere le package directory dans un File recu par la ligne de commande
        String packageDirectory = args[0];
        File packageDir = new File(packageDirectory);

        //validation du directory
        if (!packageDir.isDirectory()) {
            System.err.println("Package Invalid");
            System.exit(1);
        }
        //initialisation de la variable du nombre de @test total parmis les classes dans le package
        int totalTestCount = 0;
        
        //list tout les fichiers dans le package finissant par .java seulement , donc l'array javaFiles contient
        //tout les fichiers dans le package

        File[] javaFiles = packageDir.listFiles((dir, name) -> name.endsWith(".java"));

        if (javaFiles != null) {
            for (File javaFile : javaFiles) {
                //Utilise TPC pour calculer le nombre de @test dans le fichier et retourne le total pour ce ficher java
                int testCount = tpc.MethodCount(javaFile.getAbsolutePath());
                //System.out.println("File: " + javaFile.getName() + " - nombre de methode @Test : " + testCount); //afficher nom fichier et nb de test dans le fichier
                totalTestCount += testCount;
            }
        }

        System.out.println("Nombre Total de methodes @Test dans le package: " + totalTestCount);
    }
}

//source : https://stackoverflow.com/questions/28678026/how-can-i-get-all-class-files-in-a-specific-package-in-java?rq=3
//par mythbu.
//ressources : https://www.baeldung.com/java-find-all-classes-in-package