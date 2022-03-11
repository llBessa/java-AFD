public class App {
    public static void main(String[] args) throws Exception {
        mensagemEntrada();

        // cria uma nova instancia de um AFD
        AFD automato = new AFD();

        // recebe a entrada dos estados
        automato.addEstados();

        // recebe a entrada do alfabeto
        automato.addAlfabeto();

        // recebe as entradas das transicoes
        automato.addTransicoes();

        // recebe o estado incial do automato
        automato.setEstadoInicial();

        // recebe os estados finais do automato
        automato.addFinais();

        // recebe as entradas de teste do automato e as executa
        // retornando os seus respectivo resultados
        automato.executar();
    }

    public static void mensagemEntrada(){
        System.out.println("=====================");
        System.out.println("==    Testa AFDs   ==");
        System.out.println("=====================");
    }
}