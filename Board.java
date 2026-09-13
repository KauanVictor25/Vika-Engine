public class Board {
    Piece[] pecas = new Piece[9]; // No total 9 peças X ou O serão jogadas
    int[][] tabuleiro = new int[3][3];
    String[][] tabuleiroVisivel = new String[3][3];

    // Cria as matrizes do tabuleiro (1. a armazenada na máquina; 2. a visível aos jogadores)
    public void incicializaTabuleiro() {
        String[] lancesIniciais = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        int contador = 0;
        for (int lin = 0; lin < 3; lin++) {
            for (int col = 0; col < 3; col++) {
                tabuleiro[lin][col] = 0;
                tabuleiroVisivel[lin][col] = lancesIniciais[contador];
                contador++;
            }
        }
    }

    // Mostra os lances jogáveis
    public void lancesDisponiveis() {
        System.out.println("Lances disponíveis");
        for (int lin = 0; lin < 3; lin++) {
            for (int col = 0; col < 3; col++) {
                System.out.print(tabuleiroVisivel[lin][col] + "  ");
            }
            System.out.println();
        }

    }

    // Armazena o valor da jogada X(+1) ou por O(-1)
    public void atualizaTabuleiro() {

    }
}
