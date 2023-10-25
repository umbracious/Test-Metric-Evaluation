// Calculate the density of comments

public class dc {
    public float calculateDC(String fileName){
        int linesOfCode = cloc.calculateCLOC(fileName);
        int commentLines = linesOfCode - tloc.calculateTLOC(fileName);

        float commentDensity = (float) commentLines/(float) linesOfCode;
        return commentDensity;
    }

}
