
package ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;


public class AlgoritmoGenetico {
    private final ArrayList <Gene> conjuntoGenesBase;
    private final String codigoEntrada;
    private final int tamanhoPopulacao;
    private final int tamanhoConjuntoGenesBase;
    private final double probabilidadeMutacao;

    public AlgoritmoGenetico(ArrayList<Gene> conjuntoGenesBase, String codigoEntrada, int tamanhoPopulacao, int tamanhoConjuntoGenesBase, double probabilidadeMutacao) {
        this.conjuntoGenesBase = conjuntoGenesBase;
        this.codigoEntrada = codigoEntrada;
        this.tamanhoPopulacao = tamanhoPopulacao;
        this.tamanhoConjuntoGenesBase = tamanhoConjuntoGenesBase;
        this.probabilidadeMutacao = probabilidadeMutacao;
    }

    public ArrayList<Gene> getConjuntoGenesBase() {
        return conjuntoGenesBase;
    }
    
    public String getCodigoEntrada() {
        return codigoEntrada;
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public int getTamanhoConjuntoGenesBase() {
        return tamanhoConjuntoGenesBase;
    }

    //Gera uma populacao aleatoria com base no conjunto de genes base
    public Populacao gerarPopulacaoAleatoria(int id)
    {
        
        ArrayList<Fenotipo> fenotiposAleatorios;
        fenotiposAleatorios = new ArrayList<>();
        
        for(int i = 0; i < tamanhoPopulacao; i++)
        {
            ArrayList<Gene> permutacao;
            permutacao = new ArrayList<>();
            for(int j = 0; j < tamanhoConjuntoGenesBase; j++)
            {
                permutacao.add(conjuntoGenesBase.get(j));
            }
            
            //Realiza uma permutacao aleatoria do conjunto base, gerando um fenotipo aleatorio
            Collections.shuffle(permutacao);
            
            //Adiociona o novo fenotipo ao array
            Fenotipo fenotipoAleatorio = new Fenotipo(permutacao, i, tamanhoConjuntoGenesBase);
            fenotiposAleatorios.add(fenotipoAleatorio);
        }
        
        //Cria a populacao
        Populacao populacaoAleatoria;
        populacaoAleatoria = new Populacao(fenotiposAleatorios, id, this.tamanhoPopulacao);
        
        return populacaoAleatoria;
    
    }
    
    //Calcula a aptidao de cada fenotipo da populacao
    public void calcularAptidaoPopulacao(Populacao populacao)
    {
        for(int i = 0; i < this.tamanhoPopulacao; i++)
        {
            populacao.getFenotipos().get(i).calcularAptidao();
        }
    }

    //Seleciona os pais da populacao pelo metodo da roleta (OBS: um mesmo fenotipo pode ser escolhido mais de uma vez)
    public ArrayList<Fenotipo> selecionarFenotiposPopulacao(Populacao populacao)
    {
        ArrayList<Fenotipo> cestaSorteio;
        cestaSorteio = new ArrayList<>();
        
        double somaAptidoes = 0;
        for(int i = 0; i < this.tamanhoPopulacao; i++)
        {
            somaAptidoes += populacao.getFenotipos().get(i).getAptidao();
        }
        
        //adiciona os "bilhetes" na cesta de sorteio
        for(int i = 0; i < this.tamanhoPopulacao; i++)
        {
            double probabilidade = populacao.getFenotipos().get(i).getAptidao()/somaAptidoes;
            int quantidade = (int)(probabilidade*100);
            
            for(int j = 0; j < quantidade; j++)
            {
                cestaSorteio.add(populacao.getFenotipos().get(i));
            }
        }
        
        ArrayList<Fenotipo> paisSelecionados;
        paisSelecionados = new ArrayList<>();
        Random geradorAleatorio = new Random();
        
        //Sorteia um bilhete e adiciona na lista de pais selecionados
        for(int i = 0; i < this.tamanhoPopulacao; i++)
        {
            int indice = geradorAleatorio.nextInt(cestaSorteio.size());
            paisSelecionados.add(cestaSorteio.get(indice));
        }
        
        return paisSelecionados;
    }
    
    //Realiza o pareamento dos pais e a reproducao dos fenotipos
    public ArrayList<Fenotipo> parearEReproduzir(ArrayList<Fenotipo> paisSelecionados)
    {
        //Faz uma permutacao aleatoria da lista de pais selecionados
        Collections.shuffle(paisSelecionados);
        ArrayList<Fenotipo> filhosGerados;
        filhosGerados = new ArrayList<>();
        Random geradorAleatorio = new Random();
                
        //Crossover eh feito com pais dois a dois
        for(int i = 0; i + 1 < this.tamanhoPopulacao; i = i + 2)
        {
            Fenotipo pai1 = paisSelecionados.get(i);
            Fenotipo pai2 = paisSelecionados.get(i + 1);
            
            ArrayList<Gene> genesFilho1;
            genesFilho1 = new ArrayList<>();
            HashSet<Gene> conjuntoGenesFilho1;
            conjuntoGenesFilho1 = new HashSet<>();
            
            ArrayList<Gene> genesFilho2;
            genesFilho2 = new ArrayList<>();
            HashSet<Gene> conjuntoGenesFilho2;
            conjuntoGenesFilho2 = new HashSet<>();
                        
            //Se os pais forem diferentes
            if (pai1.getId() != pai2.getId())
            {
                
                //Um ponto de crossover aleatorio eh escolhido
                int pontoCrossover = geradorAleatorio.nextInt(tamanhoConjuntoGenesBase);
                
                //Cada filho pega a primeira parte de um pai
                for(int j = 0; j < pontoCrossover; j++)
                {
                    genesFilho1.add(pai1.getGenes().get(j));
                    conjuntoGenesFilho1.add(pai1.getGenes().get(j));
                    
                    genesFilho2.add(pai2.getGenes().get(j));
                    conjuntoGenesFilho2.add(pai2.getGenes().get(j));
                }
                
                //O restante eh retirado do outro pai, mantendo a ordem da sequencia
                for(int j = 0; j < this.tamanhoConjuntoGenesBase; j++)
                {
                    if(!conjuntoGenesFilho1.contains(pai2.getGenes().get(j)))
                    {
                        genesFilho1.add(pai2.getGenes().get(j));  
                    }
                    
                    if(!conjuntoGenesFilho2.contains(pai1.getGenes().get(j)))
                    {
                        genesFilho2.add(pai1.getGenes().get(j));  
                    }
                }
            }
            //Se for o mesmo pai, filhos identicos sao gerados
            else 
            {
                for(int j = 0; j < this.tamanhoConjuntoGenesBase; j++)
                {
                    genesFilho1.add(pai1.getGenes().get(j));
                    genesFilho2.add(pai2.getGenes().get(j));
                }
            }
            
            filhosGerados.add(new Fenotipo(genesFilho1, i, this.tamanhoConjuntoGenesBase));
            filhosGerados.add(new Fenotipo(genesFilho2, i+1, this.tamanhoConjuntoGenesBase));
        }
        
        //Se o tamanho da populacao for impar, o ultimo pai gera um filho identico
        if(this.tamanhoPopulacao % 2 == 1)
        {
            filhosGerados.add(new Fenotipo(paisSelecionados.get(this.tamanhoPopulacao-1).getGenes(), this.tamanhoPopulacao - 1, this.tamanhoConjuntoGenesBase));
        }
        
        return filhosGerados;
    }
    
    //Realiza a mutacao dos filhos gerados
    public void mutarFilhos(ArrayList<Fenotipo> filhosGerados)
    {
        for(int i = 0; i < tamanhoPopulacao; i++)
        {
            filhosGerados.get(i).mutacao(this.probabilidadeMutacao);
        }
    }
    
    //Substitui a populacao de acordo com as maiores aptidoes da antiga populacao + filhos gerados
    public Populacao substituirPopulacao(Populacao populacaoAnterior, ArrayList<Fenotipo> filhosGerados)
    {
        //Calcula aptidao dos filhos
        for(int i = 0; i < this.tamanhoPopulacao; i++)
        {
            filhosGerados.get(i).calcularAptidao();
        }
        
        ArrayList<Fenotipo> novaPopulacao;
        novaPopulacao = new ArrayList<>();
        
        //Junta os pais com os filhos
        for(int i = 0; i < this.tamanhoPopulacao; i++)
        {
            novaPopulacao.add(populacaoAnterior.getFenotipos().get(i));
        }
        
        for(int i = 0; i < this.tamanhoPopulacao; i++)
        {
            novaPopulacao.add(filhosGerados.get(i));   
        }
        
        //Ordena por aptidao
        Collections.sort(novaPopulacao, (Fenotipo f1, Fenotipo f2) -> Double.valueOf(f1.getAptidao()).compareTo(f2.getAptidao()));
        
        //Remove fenotipos repetidos
        for(int i = 0; i < novaPopulacao.size(); i++)
        {
            for(int j = i+1; novaPopulacao.get(j).getAptidao() == novaPopulacao.get(i).getAptidao(); j++)
            {
                boolean iguais = true;
                for(int k = 0; k < this.tamanhoConjuntoGenesBase; k++)
                {
                    if(novaPopulacao.get(i).getGenes().get(k) != novaPopulacao.get(j).getGenes().get(k)){
                        iguais = false;
                    }
                }
                
                if(iguais)
                {
                    novaPopulacao.remove(j);
                    j--;
                }
            }
        }
        
        //Remove o excesso de fenotipos
        while(novaPopulacao.size() > this.tamanhoPopulacao)
        {
            novaPopulacao.remove(novaPopulacao.size()-1);
        }
        
        Populacao populacao;
        populacao = new Populacao(novaPopulacao, populacaoAnterior.getId() + 1, this.tamanhoPopulacao);
        
        return populacao;
    }

}
