public class Tabuleiro {
    private int linhas;
    private int colunas;
    private static final char VAZIO = ' ';   
    
    private char[][] grade; //Matriz que representa o tabuleiro

    public Tabuleiro(int linhas, int colunas) {
        this.grade = new char[linhas][colunas];
        limparTabuleiro();
    }

    public int getLinhas() {return linhas;}
    public int getColunas() {return colunas;}

    //Método para limpar o tabuleiro, preenchendo com espaços vazios
    public void limparTabuleiro(){
        for (int i = 0; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                this.grade[i][j] = VAZIO;
            }
        }
    }

    //Método de verificação dos indices, se estao dentro dos limites da matriz
    public boolean posicaoNosLimites(int linha, int coluna){
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas; 
    }

    //Método de verificação da posição, se está vazia ou ocupada
    public boolean posicaoValida(int linha, int coluna) {
        if (!posicaoNosLimites(linha, coluna)){
            return false; //Se a posição não for válida, retorna falso
        }
        return this.grade[linha][coluna] == VAZIO; //Retorna verdadeiro se a posição estiver vazia
    }

    //Método que posiciona a peça se a jogada for válida, caso contrário retorna falso
    public boolean posicionarPeca(int linha, int coluna, char simbolo){
        if (posicaoNosLimites(linha, coluna) && posicaoValida(linha, coluna)){
            this.grade[linha][coluna] = simbolo;
            return true; //Posição válida e vazia, peça posicionada com sucesso
        }
        return false; //Posição inválida ou ocupada, peça não posicionada
    }

    //Retorna o símbolo contido na casa especificada
    public char getSimbolo(int linha, int coluna){
        if (!posicaoNosLimites(linha, coluna)){
            return VAZIO;
        }
        return this.grade[linha][coluna];
    }

    public boolean estaCheio() {
        boolean temCasaVazia = false;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (this.grade[i][j] == VAZIO) {
                    temCasaVazia = true;
                    break; }                
            }
        }
        return !temCasaVazia;
    }

    //Exibe o tabuleiro com as peças que foram posicionadas em cada casa até o momento da execução
    public void exibirTabuleiro(){
        System.out.println();
        System.out.println("---- Tabuleiro Atual ----");
        //Mostra índices das colunas
        System.out.print("L\\C ");
        for (int y = 0; y < colunas; y++) {System.out.print((y+1) + " | ");}
        System.out.println();

        //Mostra índice das linhas e casas do tabuleiro
        for (int i = 0; i < linhas; i++){
            System.out.print((i+1) + " | ");
            for (int j = 0; j < colunas; j++){
                System.out.print(this.grade[i][j]);
                System.out.print(" | "); 
            }
            System.out.println();
        }
        System.out.println();
    }

/*  ***Esse método será implementado pela Trilha 2 de Kauan***

    public boolean verificaVitoria(int linha, int coluna, char simbolo) {
        boolean venceLinha = false, venceColuna = false, venceDiagPrincip = false, venceDiagSecund = false;
        // Linha da jogada (Linha constante e Coluna varia)
        for (int j = 0; j < COLUNAS; j++) {
            if (this.grade[linha][j] == simbolo) venceLinha = true;
            else {
                venceLinha = false;
                break;
            }
        }
        // Coluna da jogada (Linha varia e Coluna constante)
        for (int i = 0; i < LINHAS; i++) {
            if (this.grade[i][coluna] == simbolo) venceColuna = true;
            else {
                venceColuna = false;
                break;
            }
        }
        // Se a posicao jogada está na diagonal principal, verificar essa diagonal
        if (linha == coluna) {
            for (int i = 0; i < LINHAS; i++) {
                if (this.grade[i][i] == simbolo) venceDiagPrincip = true;
                else {
                    venceDiagPrincip = false;
                    break;
                }
            }
        }
        // Se a posicao jogada está na diagonal secundária, verificar essa diagonal
        if (linha + coluna == 2) {
            int j = 2;
            for (int i = 0; i < LINHAS; i++) {
                if (this.grade[i][j] == simbolo) venceDiagSecund = true;
                else {
                    venceDiagSecund = false;
                    break;
                }
                j--;
            }
        }
        if (venceLinha == true || venceColuna == true || venceDiagPrincip == true || venceDiagSecund == true) return true;
        else return false;
    }
    */
}

    
