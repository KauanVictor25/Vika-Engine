public class Player {
    private String nome;
    private char simbolo;

    public Player(String nome, char simbolo) {
        this.nome = nome;
        this.simbolo = simbolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public void exibirInfo() {
        System.out.println("Nome: " + nome);
        System.out.println("Símbolo: " + simbolo);
    }
}
