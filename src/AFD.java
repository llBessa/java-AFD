import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Erros possiveis:
// Erro 01 : Uso de simbolos que não fazem parte do alfabeto nas transicoes
// Erro 02 : Uso de símbolos que não fazem parte do alfabeto nas palavras
// Erro 03 : Uso de estados que não fazem parte do conjunto de estados nas transições
// Erro 04 : Uso de autômato cujas transições não determinam um ADF

public class AFD {
    // guarda os estados do automato em um mapa de chave -> valor
    // em que o caracter que representa o estado aponta
    // para a sua respectiva instancia do estado
    // estados : 'A' -> (instancia de A)
    public Map<Character, Estado> estados;

    // guarda os elementos do alfabeto em uma lista de caracteres
    public ArrayList<Character> alfabeto;

    // guarda as transicoes do automato em uma lista de Strings
    public ArrayList<String> transicoes;

    // guarda o caracter que representa o estado inicial
    public char estadoInicial;

    // guarda a instancia do estado em que o automato se encontra durante a execucao
    public Estado estadoAtual;

    // guarda os estados finais do automato em uma lista de caracteres
    public ArrayList<Character> finais;

    // leitor para receber entradas no terminal
    public Scanner leitor;

    // Construtor
    AFD(){
        // instanciacoes
        this.leitor = new Scanner(System.in);
        this.alfabeto = new ArrayList<>();
        this.transicoes = new ArrayList<>();
        this.finais = new ArrayList<>();
        this.estados = new HashMap<>();
    }

    // metodos

    // chama a entrada de estados no automato e guarda os estados no mapa "estados"
    public void addEstados(){
        System.out.println("Adicione os estados :");

        // guarda a string contento os estados passados em um array de caracteres
        // com cada caracter sendo a representacao de um estado
        String estados = cortaEspacos();
        char conjEstados[] = estados.toCharArray();

        // cria uma nova instancia de Estado para cada caracter encontrado no array
        // e adiciona no mapa "estados", conjuntos de chave -> valor
        // sendo
        // chave : o nome do estado
        // valor : a instancia do respectivo estado
        for(int i = 0; i < conjEstados.length; i++){
            Estado estadoNovo = new Estado(conjEstados[i]);
            this.estados.put(estadoNovo.nome, estadoNovo);
        }
    }

    // chama a entrada de elementos do alfabeto numa lista de caracteres
    // contento cada elemento indexado
    public void addAlfabeto(){
        System.out.println("Adicione o alfabeto :");
        
        // guarda a string contento os elementos do alfabeto em um array de caracteres
        String alfabeto = cortaEspacos();
        char conjAlfabeto[] = alfabeto.toCharArray();

        // adiciona os caracteres do array na lista "alfabeto"
        for(int i = 0; i < conjAlfabeto.length; i++){
            char elemento = conjAlfabeto[i];
            this.alfabeto.add(elemento);
        }
    }

    // adiciona as transicoes do automato em uma lista de Strings
    // em que cada String representa uma transicao.
    // o caracter "0" da String representa o estado de origem da transicao
    // o caracter "1" da String representa o destino da transicao
    // e o caracter "2" da String representa a entrada consumida
    public void addTransicoes(){
        System.out.println("Adicione as transicoes :");

        String transicao;
        char origem;
        char destino;
        char entrada;
        while(true){
            // guarda a string de transicao
            transicao = cortaEspacos();

            // guarda a origem da transicao
            // se o valor for "#" significa que nao devem ser adicionadas novas transicoes
            origem = transicao.charAt(0);
            if(origem == '#') break;

            // se a string de transicao tiver um tamanho diferente de 3,
            // significa que existe uma transicao vazia sendo passada ou
            // existe uma transicao com mais de um destino sendo passada.
            // essas nao sao caracteristicas de um AFD, logo e exibida uma
            // mensagem de erro e o programa e encerrado.
            if(transicao.length() != 3){
                System.out.println("Erro 04");
                System.exit(4);
            }
            
            // guarda a entrada da transicao
            // se a entrada nao for parte do alfabeto do automato uma mensagem de erro
            // e exibida e o programa e finalizado
            entrada = transicao.charAt(2);
            if(this.alfabeto.indexOf(entrada) == -1){
                System.out.println("Erro 01");
                System.exit(1);
            }
            
            // guarda o destino da transicao 
            destino = transicao.charAt(1);

            // se a entrada ou o destino da transicao nao fazem parte do conjunto
            // de estados do automato uma mensagem de erro e exibida e o programa e finalizado
            if(this.estados.get(origem) == null || this.estados.get(destino) == null){
                System.out.println("Erro 03");
                System.exit(3);
            }

            // Adiciona uma transicao no estado de origem da transicao
            // e adiciona a string de transicao na lista das transicoes
            Estado o = this.estados.get(origem);
            o.addTransicao(transicao);
            this.transicoes.add(transicao);
        }

        // se a quantidade de transicoes do automato for diferente da quantidade de
        // estados do automato multiplicado pela quantidade de elementos do alfabeto
        // significa que o automato tem mais ou menos transicoes do que e necessario
        // em um AFD, logo as transicoes nao determinam um AFD
        // e por isso uma menssagem de erro e exibia e o programa e finalizado
        if(this.transicoes.size() != this.estados.size()*this.alfabeto.size()){
            System.out.println("Erro 04");
                System.exit(4);
        }

        System.out.println("As transicoes acabaram!\n");
    }

    // Adiciona os estados finais do automato em uma lista de caracteres
    public void addFinais(){
        System.out.println("Adicione os estados finais :");
        
        // guarda em conjFinais um array de caracteres contendo os estados finais do automato
        String finais = cortaEspacos();
        char conjFinais[] = finais.toCharArray();

        // Sinaliza nos estados finais a flag estadoFinal como true
        for(int i = 0; i < conjFinais.length; i++){
            this.estados.get(conjFinais[i]).estadoFinal = true;
        }
    }
    
    // Recebe o estado inicial do automato
    // e atribui o estado inicial ao atributo estadoInicial
    // e ao atributo estadoAtual.
    // Desta maneira, ao executar o automato o estado atual comeca sempre
    // com o inicial
    public void setEstadoInicial(){
        System.out.println("Estado inicial : ");
        char inicial = cortaEspacos().charAt(0);
        this.estadoInicial = inicial;
        this.estadoAtual = this.estados.get(inicial);
    }

    // Recebe as entradas teste do automato,
    // realiza as trnasicoes e exibe os resultados dos testes
    public void executar(){
        System.out.println("Digite as entradas de teste :");

        // guarda em testes um array de Strings contendo todos os casos de teste
        String[] testes = leitor.nextLine().split(" ");

        // percorre os casos de teste
        for(String key : testes){
            // percorre as entradas de cada teste
            for(char entrada : key.toCharArray()){

                // Se a entrada possuir um elemento nao pertencente ao
                // alfabeto, uma mensagem de erro e exibida e o programa e finalizado
                if(this.alfabeto.indexOf(entrada) == -1){
                    System.out.println("Erro 02");
                    System.exit(2);
                }

                // guarda o estado em que a execucao se encontra depois de realizar uma transicao
                char estadoPosTransicao = estados.get(estadoAtual.nome).realizarTrasicao(entrada);
                this.estadoAtual = estados.get(estadoPosTransicao);
            }
            
            // apos as entradas serem percorridas verifica se a execucao parou em um estado de aceitacao
            // se sim, exibe uma mensagem dizendo que a cadeia foi aceita
            // se nao, exibe uma mensagem dizendo que a cadeia nao foi aceita
            if(this.estadoAtual.estadoFinal == true) System.out.println(key + " sim");
            else System.out.println(key + " nao");

            // reinicializa o estadoAtual para estadoInicial para que seja
            // realizado um novo teste
            this.estadoAtual = this.estados.get(this.estadoInicial);
        }
    }

    // Leitor que ignora espacos no terminal
    public String cortaEspacos(){
        String elemLido = leitor.nextLine().replace(" ", "");
        return elemLido;
    }
}