public class GravityColumnRule implements PlacementRule {
    @Override
    public Jogada aplicarJogada(Tabuleiro tabuleiro, Jogada jogada) throws InvalidMoveException {
        int coluna = jogada.getColuna();

        if (coluna < 0 || coluna >= tabuleiro.getColunas()) {
            throw new InvalidMoveException("Coluna fora dos limites do tabuleiro.");
        }

        // Procura de baixo para cima a primeira linha vazia nessa coluna
        for (int linha = tabuleiro.getLinhas() - 1; linha >= 0; linha--) {
            if (tabuleiro.posicaoValida(linha, coluna)) {
                Jogador jogador = jogada.getJogador();
                tabuleiro.posicionarPeca(linha, coluna, jogador.getSimbolo());
            
                return new Jogada(linha, coluna, jogador);
            }
        }

        //Se saiu do loop sem encontrar espaço, a coluna está cheia
        throw new InvalidMoveException("A coluna " + (coluna + 1) + " já está cheia!");
    }
}
