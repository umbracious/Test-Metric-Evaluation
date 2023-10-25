public class tcmp {
    public static float calculateTCMP(String fileName){
        int TLOC = tloc.calculateTLOC(fileName);
        int TASSERT = tassert.calculateTASSERT(fileName);
        
        float tcmp = (float) TLOC/(float) TASSERT;
        return tcmp;
    }
}
