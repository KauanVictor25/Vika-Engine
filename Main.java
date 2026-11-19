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
        // Cria o processador do jogo
        GameEngine gameEngine = new GameEngine();

        // O .json deve trazer a linha e a coluna
        int linha = 3;
        int coluna = 3;

        // Cria o tabuleiro na memória
        Tabuleiro tabuleiro = new Tabuleiro(linha, coluna);

        boolean jogoAtivo = true;
        int acao = 0;
        while (jogoAtivo) {
            System.out.println("------ Vika Engine ------");
            System.out.println("1. Iniciar novo jogo");
            System.out.println("2. Fechar programa");
            System.out.print("Ação: ");
            acao = lerInteiro();
            switch (acao) {
                // 1. Iniciar novo jogo
                case 1:
                    tabuleiro.limparTabuleiro();
                    // Recebe os dois novos jogadores
                    int qntdJogadores = 2;
                    Jogador[] jogador = new Jogador[qntdJogadores];
                    for (int i = 0; i < qntdJogadores; i++) {
                        char simbolo = 's';
                        if (i == 0) simbolo = 'X';
                        if (i == 1) simbolo = 'O';
                        System.out.println("--- Jogador " + simbolo + " ---");
                        System.out.print("Nome: ");
                        String nome = lerString();
                        jogador[i] = new Jogador(nome, simbolo);
                    }
                    System.out.println("--- Jogadores definidos! ---");

                    // Loop do jogo
                    gameEngine.iniciarPartida();

                // 2. Fechar programa
                case 2:
                    System.out.println("Fechando o programa...");
                    jogoAtivo = false;
                    break;
            
                default:
                    System.out.println("Opção inválida! Digite o número de uma das ações.");
                    break;
            }
        }
    }
}