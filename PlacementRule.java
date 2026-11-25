public interface PlacementRule {
    Jogada aplicarJogada(Tabuleiro tabuleiro, Jogada jogada) throws InvalidMoveException;
}