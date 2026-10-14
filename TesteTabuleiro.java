public class TesteTabuleiro {
    public static void main(String[] args) {
        System.out.println("=== TESTE DA TRILHA A: TABULEIRO ===");

        Tabuleiro tabuleiro = new Tabuleiro();

        // 1. Exibir tabuleiro limpo inicial
        System.out.println("1. Tabuleiro inicial (deve estar vazio):");
        tabuleiro.exibir();

        // 2. Testar posicionamento de peças válidas
        System.out.println("2. Jogando 'X' no centro (linha 1, coluna 1):");
        boolean jogada1 = tabuleiro.posicionarPeca(1, 1, 'X');
        System.out.println("Jogada aceita? " + jogada1);
        tabuleiro.exibir();

        // 3. Testar regra de sobreposição (não pode permitir jogar onde já tem peça)
        System.out.println("3. Tentando jogar 'O' na mesma casa ocupada (1, 1):");
        boolean jogadaInvalida = tabuleiro.posicionarPeca(1, 1, 'O');
        System.out.println("Jogada aceita (esperado: false)? " + jogadaInvalida);

        // 4. Testar limites fora do tabuleiro 3x3
        System.out.println("\n4. Tentando jogar fora da matriz (índice 5, 5):");
        boolean jogadaFora = tabuleiro.posicionarPeca(5, 5, 'O');
        System.out.println("Jogada aceita (esperado: false)? " + jogadaFora);

        // 5. Testar consulta de símbolo (método que Vicente usará para checar vitória)
        System.out.println("\n5. Consultando símbolo na posição (1, 1): " + tabuleiro.getSimbolo(1, 1));
    }
}