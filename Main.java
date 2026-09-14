public class Main {
    public static void main(String[] args) {

        System.out.println("------Jogo da Velha------");
        // Cria o tabuleiro na memória
        Board jogoDaVelha = new Board();

        jogoDaVelha.incicializaTabuleiro();
        jogoDaVelha.lancesDisponiveis();

        /* 
        IMPORTANTE: A declaração de jogadores a seguir se trata apenas de um direcionamento
        do fluxo do programa, não é necessariamente definiva
        */
       
        // Declaração do Jogador 1
        User player1 = new User();
        player1.nome = "Vika";
        player1.simbolo = 0; // Joga com O

        // Declaração do Jogador 2
        User player2 = new User();
        player2.nome = "Kavi";
        player2.simbolo = 1; // Joga com X

        System.out.println("Jogador 1: " + player1.nome + "\nJogador 2: " + player2.nome);

    }
}