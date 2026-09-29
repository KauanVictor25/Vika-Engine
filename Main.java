import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static char lerCaractere() {
        return scanner.nextLine().trim().charAt(0);
    }

    public static String lerString() {
        return scanner.nextLine().trim();
    }

    public static int lerInteiro() {
        return Integer.parseInt(scanner.nextLine().trim());
    }
    public static void main(String[] args) {

        // Cria o tabuleiro na memória
        Board jogoDaVelha = new Board();
        jogoDaVelha.incicializaTabuleiro();

        boolean jogoAtivo = true;
        int acao = 0;
        while (jogoAtivo) {
            System.out.println("------ Jogo da Velha ------");
            System.out.println("1. Iniciar novo jogo");
            System.out.println("2. Fechar programa");
            System.out.print("Ação: ");
            acao = lerInteiro();
            switch (acao) {
                case 1:
                    // Inicializa os dois jogadores
                    int qntdJogadores = 2;
                    Player[] jogador = new Player[qntdJogadores];

                    for (int i = 0; i < qntdJogadores; i++) {
                            System.out.println("--- Jogador " + (i+1) + " ---");
                            System.out.print("Nome: ");
                            String nome = lerString();
                            System.out.print("Simbolo (o / x): ");
                            char simbolo = lerCaractere();

                            jogador[i] = new Player(nome, simbolo);
                        }
                    System.out.println("--- Jogadores definidos! ---");
                    break;

                case 2:
                    System.out.println("Fechando o programa...");
                    jogoAtivo = false;
                    break;
            
                default:
                    System.out.println("Opção inválida! Digite o número de uma das ações.");
                    break;
            }
        }
        // Ainda não implementado na lógica do programa
        jogoDaVelha.lancesDisponiveis();
    }
}