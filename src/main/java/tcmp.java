public class tcmp {
    public static float calculateTCMP(String fileName){
        int TLOC = tloc.calculateTLOC(fileName);
        int TASSERT = tassert.calculateTASSERT(fileName);

        if (TASSERT == 0){
            System.err.println("Erreur: pas de asserts dans le code");
            System.exit(1);
        }
        float tcmp = (float) TLOC/(float) TASSERT;
        return tcmp;
    }
}
