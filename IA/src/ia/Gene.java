
package ia;


public class Gene {
    private final int id;
    private final String nome;

    public Gene(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }   
}
