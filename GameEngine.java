import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;



public class GameEngine {
    private Tabuleiro tabuleiro;
    List<Jogador> jogadores = new ArrayList<Jogador>();
    private PlacementRule placementRule;
    private WinCondition winCondition;
    private String nomeJogo;

    // Construtor usado pelo GameLoader para injetar as configurações do JSON
    public GameEngine(Tabuleiro tabuleiro, List<Jogador> jogadores, PlacementRule placementRule, WinCondition winCondition, String nomeJogo) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        this.placementRule = placementRule;
        this.winCondition = winCondition;
        this.nomeJogo = nomeJogo;

    }
    public GameEngine(Tabuleiro tabuleiro, PlacementRule placementRule, WinCondition winCondition, String nomeJogo) {
        this.tabuleiro = tabuleiro;
        this.placementRule = placementRule;
        this.winCondition = winCondition;
        this.nomeJogo = nomeJogo;
    }
    public void iniciarPartida() {
        Scanner scanner = new Scanner(System.in);
        int indiceAtual = 0;
        boolean jogoAtivo = true;

        System.out.println("--- Iniciando partida de: " + nomeJogo + " ---");

        while (jogoAtivo) {
            tabuleiro.exibirTabuleiro();
                        
            Jogador jogadorAtual = jogadores.get(indiceAtual);
            System.out.println("Vez de " + jogadorAtual.getNome() + " com a peça '" + jogadorAtual.getSimbolo() + "'");
            
            boolean jogadaConcluida = false;
            
            // Loop para garantir que o jogador faça um movimento válido neste turno
            while (!jogadaConcluida) {
                System.out.print("Digite a jogada LC(Ex.: 12, 33): ");
                String input = scanner.nextLine().trim();

                try {
                    // Leitura das coordenadas
                    int linhaJogada = Character.getNumericValue(input.charAt(0)) - 1;
                    int colunaJogada = Character.getNumericValue(input.charAt(1)) - 1;
                    
                    // Recebe a jogada realizada pelo jogadorAtual
                    Jogada jogada = new Jogada(linhaJogada, colunaJogada, jogadorAtual);
                    
                    // Aplica a jogada (a verificação dela é feita pelo arquivo das regras)
                    Jogada jogadaAplicada = placementRule.aplicarJogada(tabuleiro, jogada);
                    
                    // Se passou da linha acima sem lançar exceção, a jogada foi um sucesso
                    jogadaConcluida = true; 

                    // Verifica a vitória
                    if (winCondition.verificarVitoria(tabuleiro, jogadaAplicada)) {
                        tabuleiro.exibirTabuleiro();
                        System.out.println("--> " + jogadorAtual.getNome() + " venceu!\n");
                        jogoAtivo = false;
                    }
                    // Se não houver vitória, verificar se foi empate
                    else if (tabuleiro.estaCheio()) {
                        tabuleiro.exibirTabuleiro();
                        System.out.println("--> Empate!\n");
                        jogoAtivo = false;
                    }
                    // Se ninguém venceu ainda nem houve empate, o jogo continua e a vez vai para outro jogador
                    else {
                        indiceAtual = (indiceAtual + 1) % jogadores.size();
                    }

                // Caso o usuário digite algo fora do padrão (ex: "A", "1")
                } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("--> Formato inválido! Digite dois números sem espaço (Ex: 12).");
                    
                // Exceção personalizada que vai ser criada por na Trilha 2 (casa ocupada, fora do tabuleiro, etc)
                } catch (InvalidMoveException e) {
                    System.out.println("--> Jogada inválida: " + e.getMessage());
                }
            }
        }
    }

}
