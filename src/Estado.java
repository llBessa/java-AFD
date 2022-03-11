import java.util.HashMap;
import java.util.Map;

public class Estado {
    // guarda o nome do estado em forma de caracter
    public char nome;

    // guarda as transicoes em um Mapa de chave -> valor
    public Map<Character,Character> transicao;

    // flag para identificar se o estado e inicial
    public boolean estadoInicial;

    // flag para identificar se o estado e final
    public boolean estadoFinal;

    // construtor
    Estado(char nome){
        this.nome = nome;
        this.transicao = new HashMap<>();
    }

    // Metodos 
    
    // Adiciona uma nova transicao no estado
    public void addTransicao(String transicao){
        // guarda o caracter da entrada consumida
        char entrada = transicao.charAt(2);

        // guarda o caracter do destido da transicao
        char destino = transicao.charAt(1);

        // cria uma nova transicao atraves dupla de chave -> valor
        // utilizando a entrada e o estado de destino
        // transicao : entrada -> destino
        this.transicao.put(entrada,destino);
    }

    // retorna um char que representa o estado em que o automato
    // se encontra depois de realizar uma transicao a partir
    // da entrada passada por parametro
    public char realizarTrasicao(char entrada){
        // retorna para "destino" o caracter que representa o estado atual
        // do automato pos transicao
        char destino = this.transicao.get(entrada);
        return destino;
    }
}