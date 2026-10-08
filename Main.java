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
        Tabuleiro jogoDaVelha = new Tabuleiro();

        boolean jogoAtivo = true;
        int acao = 0;
        while (jogoAtivo) {
            System.out.println("------ Jogo da Velha ------");
            System.out.println("1. Iniciar novo jogo");
            System.out.println("2. Fechar programa");
            System.out.print("Ação: ");
            acao = lerInteiro();
            switch (acao) {
                // 1. Iniciar novo jogo
                case 1:
                    // Recebe os dois novos jogadores
                    int qntdJogadores = 2;
                    Player[] jogador = new Player[qntdJogadores];
                    // Jogador 1 (X)
                    System.out.println("--- Jogador X ---");
                    System.out.print("Nome: ");
                    String nome = lerString();            
                    jogador[0] = new Player(nome, "X".charAt(0));
                    
                    // Jogador 2 (O)
                    System.out.println("--- Jogador O ---");
                    System.out.print("Nome: ");
                    nome = lerString();                   
                    jogador[1] = new Player(nome, "O".charAt(0));
                        
                    System.out.println("--- Jogadores definidos! ---");

                    // Inicia loop do jogo
                    // 1) 
                    break;
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