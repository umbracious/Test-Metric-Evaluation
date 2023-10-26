// Calculate the density of comments

public class dc {
    public static float calculateDC(String fileName){
        int linesOfCode = cloc.calculateCLOC(fileName);
        int commentLines = linesOfCode - tloc.calculateTLOC(fileName);

        if (linesOfCode == 0){
            System.err.println("Erreur: aucun ligne de code");
            System.exit(1);
        }

        float commentDensity = (float) commentLines/(float) linesOfCode;
        return commentDensity;
    }

}
