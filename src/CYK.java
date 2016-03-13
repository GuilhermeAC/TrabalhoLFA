public class CYK {

    public static void main(String[] args) {
        int tam = 6;
        int i,j;
        int limite = tam;
        int matCYK[][] = new int[tam][tam];


        for (i=0;i<tam ;i++ ) {
            for (j=0;j<tam ;j++ ) {
                if(i == 0) {
                    matCYK[i][j] = 3;
                }
                else {
                    matCYK[i][j] = 0;
                }
            }
        }

        for (i=0;i<tam ;i++ ) {
            for (j=0;j<limite ;j++ ) {
                System.out.print(matCYK[i][j] + " ");
            }
            limite--;

            System.out.println();
        }

        System.out.println();
        limite = tam-1;
        int sobe;
        int desce1;
        int desce2;

        for (i=1;i<tam ;i++ ) {
            for (j=0;j<limite ;j++ ) {
                sobe = 0;
                desce1 = i - 1;
                desce2 = j + 1;
                System.out.println("i "+i + " j "+j);
                while(desce1 >= 0 && desce1 < i) {

                        matCYK[i][j] = matCYK[i][j] + matCYK[sobe][j] + matCYK[desce1][desce2];
                        System.out.print(desce1 + " desce1 ");
                        System.out.println(desce2 + " desce2 "+ limite +" limite");
                        sobe++;
                        desce1--;
                        desce2++;

                }
            }
            limite--;
        }

        System.out.println();
        limite = tam;

        for (i=0;i<tam ;i++ ) {
            for (j=0;j<limite ;j++ ) {
                System.out.print(matCYK[i][j] + " ");
            }
            limite--;

            System.out.println();
        }
    }



}

