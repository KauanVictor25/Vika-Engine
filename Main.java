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
                    jogoDaVelha.limparTabuleiro();
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

                    // Inicia loop do jogo
                    int jogadasDisponiveis = 9;
                    char vezDeJogar = 0; // Primeira jogada do jogador X (id 0)
                    while (jogadasDisponiveis > 0) {
                        int linhaJogada = 0;
                        int colunaJogada = 0;
                        boolean jogadaValida = false;
                        while (!jogadaValida) {
                            jogoDaVelha.exibirTabuleiro();
                            System.out.println("Vez de " + jogador[vezDeJogar].getNome() + " com " + jogador[vezDeJogar].getSimbolo());
                            System.out.print("Digite a jogada LC(Ex.: 12, 33, ...)): ");
                            String jogada = lerString();
                            System.out.println();
                            linhaJogada = Character.getNumericValue(jogada.charAt(0)) - 1;
                            colunaJogada = Character.getNumericValue(jogada.charAt(1)) - 1;
                            
                            if (jogoDaVelha.posicaoValida(linhaJogada, colunaJogada)) {
                                jogadaValida = true;
                                jogoDaVelha.posicionarPeca(linhaJogada, colunaJogada, jogador[vezDeJogar].getSimbolo());
                            }
                            else System.out.println("-> Jogada inválida!");
                        }
                        if (jogoDaVelha.verificaVitoria(linhaJogada, colunaJogada, jogador[vezDeJogar].getSimbolo())) {
                            jogoDaVelha.exibirTabuleiro();
                            System.out.println("-> " + jogador[vezDeJogar].getNome() + " venceu!\n");
                            break;
                        }
                        if (jogadaValida) { // Só passa a vez se a jogada for válida
                            if (vezDeJogar == 0) vezDeJogar = 1;
                            else if (vezDeJogar == 1) vezDeJogar = 0;
                            jogadasDisponiveis--;
                        }
                    }
                    if (jogadasDisponiveis == 0) {
                        jogoDaVelha.exibirTabuleiro();
                        System.out.println("-> Empate!\n");
                    }
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