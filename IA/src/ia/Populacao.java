
package ia;

import java.util.ArrayList;


public class Populacao {
    private ArrayList<Fenotipo> fenotipos;
    private final int id;
    private final int tamanho;

    public Populacao(int id, int tamanho) {
        this.id = id;
        this.tamanho = tamanho;
    }    
    
    public Populacao(ArrayList<Fenotipo> fenotipos, int id, int tamanho) {
        this.fenotipos = fenotipos;
        this.id = id;
        this.tamanho = tamanho;
    }

    public ArrayList<Fenotipo> getFenotipos() {
        return fenotipos;
    }

    public void setFenotipos(ArrayList<Fenotipo> fenotipos) {
        this.fenotipos = fenotipos;
    }

    public int getId() {
        return id;
    }

    public int getTamanho() {
        return tamanho;
    }

}
