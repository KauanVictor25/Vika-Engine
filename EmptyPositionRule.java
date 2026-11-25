//Regras de posicionamento relacionado ao jogo da velha

public class EmptyPositionRule  implements PlacementRule{
    @Override 
    public Jogada aplicarJogada(Tabuleiro tabuleiro, Jogada jogada) throws InvalidMoveException {
        int linha = jogada.getLinha();
        int coluna = jogada.getColuna();

        if(!tabuleiro.posicaoNosLimites(linha, coluna)){
            throw new InvalidMoveException("Posição fora dos limites do tabuleiro.");
        }

        if(!tabuleiro.posicaoValida(linha, coluna)){
            throw new InvalidMoveException("Essa casa ja esta ocupada!");
        }

        char simbolo = jogada.getJogador().getSimbolo();
        tabuleiro.posicionarPeca(linha, coluna, simbolo);

        return jogada;
    }
}
