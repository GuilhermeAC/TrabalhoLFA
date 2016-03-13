import java.util.ArrayList;

public class Mat {

    private ArrayList<String> value = new ArrayList<String>();
    private int vet[];
    private int tam;

    public Mat(int tam) {
        this.tam = tam;
        vet = new int[tam];
    }


    public ArrayList<String> getValue() {
        return value;
    }

    public int getTam() {
        return tam;
    }

    public int[] getVet() {
        return vet;
    }

}
