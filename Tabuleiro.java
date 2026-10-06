public class Tabuleiro {
    private static final int LINHAS = 3;
    private static final int COLUNAS=3;
    private static final char VAZIO= ' ';   
    

    //Inicío do encapsulamento : Quem tiver fora nao acessa diretamente 
    private char[][] grade; //Matriz que representa o tabuleiro

    public Tabuleiro() {
        this.grade = new char[LINHAS][COLUNAS];
        limparTabuleiro();
    }

    //Método para limpar o tabuleiro, preenchendo com espaços vazios
    private void limparTabuleiro(){
        for (int i=0; i < LINHAS; i++){
            for (int j= 0; j< COLUNAS; j++){
                this.grade[i][j] = VAZIO;
            }
        }
    }

    //Método de verificação dos indices , se estao dentro dos limites daa matriz
    public boolean posicaoValida(int linha, int coluna){
        return linha >= 0 && linha < LINHAS && coluna>=0 && coluna < COLUNAS; 
    }

    //Método de verificação da posição, se está vazia ou ocupada
    public boolean estaVazia(int linha, int coluna) {
        if (!posicaoValida(linha, coluna)){
            return false; //Se a posição não for válida, retorna falso
        }
        return this.grade[linha][coluna] == VAZIO; //Retorna verdadeiro se a posição estiver vazia
    }

    //Método que posiciona a peça se a jogada for válida, caso contrário retorna falso
    public boolean posicionarPeca(int linha, int coluna, char simbolo){
        if (posicaoValida(linha, coluna)&& estaVazia(linha, coluna)){
            this.grade[linha][coluna] = simbolo;
            return true; //Posição válida e vazia, peça posicionada com sucesso
        }
        return false; //Posição inválida ou ocupada, peça não posicionada
    }

    public char getSimbolo(int linha, int coluna){
        if (!posicaoValida(linha, coluna)){
            return VAZIO;
        }
        return this.grade[linha][coluna];
    }

    public void exibir(){
        System.out.println();
        System.out.println("  1   2   3");
        System.out.println("------------------------");
        for (int i=0; i < LINHAS; i++){
            System.out.print((i+1)+"|");
            for (int j=0; j< COLUNAS; j++){
                System.out.print(this.grade[i][j]);
                System.out.print("|"); 
            }
            System.out.println();
            System.out.println("---------------------------");
        }
        System.out.println();
    }
    
    
}

    
