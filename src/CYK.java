import java.util.HashMap;
import java.util.Map;

public class CYK {

    public static void main(String[] args) {
        String word = "aaabbb";
        int sizeWord = 6;
        int i,j;
        int limite = sizeWord;
        Mat matCYK[][] = new Mat[sizeWord][sizeWord];

        for (i=0;i<sizeWord ;i++ ) {
            for (j=0;j<sizeWord ;j++ ) {
                matCYK[i][j] = new Mat(sizeWord);
                if(i == 0) {
                    matCYK[i][j].getValue().add("A");
                }
                else {
                    matCYK[i][j].getValue().add("-");
                }
            }
        }

        for (i=0;i<sizeWord ;i++ ) {
            for (j=0;j<limite ;j++ ) {
                System.out.print(matCYK[i][j].getValue().get(0) + " ");
            }
            limite--;

            System.out.println();
        }

        System.out.println();
        limite = sizeWord-1;
        int sobe;
        int desce1;
        int desce2;
        String mat;

        for (i=1;i<sizeWord ;i++ ) {
            for (j=0;j<limite ;j++ ) {
                sobe = 0;
                desce1 = i - 1;
                desce2 = j + 1;

                while(desce1 >= 0 && desce1 < i) {
                        mat = matCYK[i][j].getValue().get(0) + matCYK[sobe][j].getValue().get(0) + matCYK[desce1][desce2].getValue().get(0);
                        matCYK[i][j].getValue().set(0,mat);
                        //System.out.print(desce1 + " desce1 ");
                        sobe++;
                        desce1--;
                        desce2++;

                }
            }
            limite--;
        }

        System.out.println();
        limite = sizeWord;

        for (i=0;i<sizeWord ;i++ ) {
            for (j=0;j<limite ;j++ ) {
                System.out.print(matCYK[i][j].getValue().get(0) + " ");
            }
            limite--;

            System.out.println();
        }
    }



}
