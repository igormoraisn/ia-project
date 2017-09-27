
package ia;

import java.util.ArrayList;
import java.util.Random;


public class Fenotipo {
    private ArrayList<Gene> genes;
    private final int id;
    private final int tamanho;
    private double aptidao;

    public Fenotipo(ArrayList<Gene> genes, int id, int tamanho) {
        this.genes = genes;
        this.id = id;
        this.tamanho = tamanho;
    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Gene> genes) {
        this.genes = genes;
    }

    public void setAptidao(double aptidao) {
        this.aptidao = aptidao;
    }

    public double getAptidao() {
        return aptidao;
    }

    public int getId() {
        return id;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void calcularAptidao() {
        //calcular a aptidao com base na funcao objetivo
        //utilizar o metodo setAptidao para modificar o valor
    }

    //De acordo com a probabilidade fornecida, um fenotipo pode ter dois genes trocados de posicao
    public boolean mutacao(double probabilidade) {
        boolean retorno = false;  
        Random geradorAleatorio = new Random();
        if (geradorAleatorio.nextDouble() < probabilidade) {  
            int indice1 = geradorAleatorio.nextInt(this.tamanho);
            int indice2 = geradorAleatorio.nextInt(this.tamanho);
            Gene geneAux = this.genes.get(indice1);
            this.genes.set(indice1, this.genes.get(indice2));
            this.genes.set(indice2, geneAux);
        }
        return retorno;
    }
}
