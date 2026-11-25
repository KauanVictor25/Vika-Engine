public class AlignmentWinCondition implements WinCondition {
    private int quantidadeParaVencer;

    public AlignmentWinCondition(int quantidadeParaVencer) {
        this.quantidadeParaVencer = quantidadeParaVencer;
    }

    @Override
    public boolean verificarVitoria(Tabuleiro tabuleiro, Jogada ultimaJogada) {
        int linha = ultimaJogada.getLinha();
        int coluna = ultimaJogada.getColuna();
        char simbolo = ultimaJogada.getJogador().getSimbolo();

        // Verifica os 4 eixos: Horizontal, Vertical, Diagonal Principal e Diagonal Secundária
        return verificarEixo(tabuleiro, linha, coluna, simbolo, 0, 1)   // Horizontal
            || verificarEixo(tabuleiro, linha, coluna, simbolo, 1, 0)   // Vertical
            || verificarEixo(tabuleiro, linha, coluna, simbolo, 1, 1)   // Diagonal \
            || verificarEixo(tabuleiro, linha, coluna, simbolo, 1, -1); // Diagonal /
    }

    private boolean verificarEixo(Tabuleiro tabuleiro, int linha, int coluna, char simbolo, int dLinha, int dColuna) {
        int total = 1; // Conta a própria peça recém-colocada
        total += contarNaDirecao(tabuleiro, linha, coluna, simbolo, dLinha, dColuna);
        total += contarNaDirecao(tabuleiro, linha, coluna, simbolo, -dLinha, -dColuna);
        return total >= quantidadeParaVencer;
    }

    private int contarNaDirecao(Tabuleiro tabuleiro, int linha, int coluna, char simbolo, int dLinha, int dColuna) {
        int contador = 0;
        int l = linha + dLinha;
        int c = coluna + dColuna;

        while (tabuleiro.posicaoNosLimites(l, c) && tabuleiro.getSimbolo(l, c) == simbolo) {
            contador++;
            l += dLinha;
            c += dColuna;
        }
        return contador;
    }
}