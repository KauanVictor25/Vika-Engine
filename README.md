# Board Game Engine - Checkpoint 1 (Jogo da Velha)

Este projeto representa o **Checkpoint 1** da disciplina de Linguagem de Programação Orientada a Objetos (LPOO) - 2026.2. 

O objetivo desta etapa inicial é desenvolver uma primeira versão funcional de um jogo de tabuleiro executado via terminal, utilizando os conceitos fundamentais de Programação Orientada a Objetos (POO) como classes, objetos e encapsulamento, servindo de base para a futura *Game Engine*.

O jogo escolhido e implementado para este checkpoint foi o **Jogo da Velha (Tic-Tac-Toe)**.

## 🚀 Funcionalidades Atuais

- Tabuleiro 3x3 gerenciado por uma classe própria (`Tabuleiro`).
- Suporte a 2 jogadores humanos (`Jogador`), com definição de nomes personalizados.
- Controle de turnos automático.
- Validação de jogadas (impede jogar fora dos limites ou em posições já ocupadas).
- Verificação automática de condições de vitória (linhas, colunas e diagonais) e empate.
- Interface inteiramente textual via terminal.

## 🛠️ Estrutura do Projeto (Classes)

O sistema foi modelado para evitar a centralização da lógica, dividindo as responsabilidades da seguinte forma:

1. **`Tabuleiro`**: Responsável por manter o estado da grade do jogo, validar limites, aplicar peças e checar condições de vitória.
2. **`Jogador`**: Classe que encapsula os dados de quem está jogando (nome e símbolo).
3. **`Main`**: Atua como o controlador do jogo. Gerencia o loop principal, a interação com o usuário (I/O) e o fluxo dos turnos.

## ⚙️ Como Executar

**Pré-requisitos:** É necessário ter o [Java JDK (versão 8 ou superior)](https://www.oracle.com/br/java/technologies/javase-downloads.html) instalado em sua máquina.

1. Abra o seu terminal (Prompt de Comando, PowerShell, bash, etc.).
2. Navegue até o diretório onde os arquivos `.java` estão salvos.
3. Compile os arquivos do projeto executando o comando:
   ```bash
   javac Main.java Jogador.java Tabuleiro.java
   ```
4. Após a compilação (arquivos `.class` gerados), inicie o jogo com o comando:
   ```bash
   java Main
   ```
5. Siga as instruções exibidas na tela para jogar! As jogadas devem ser inseridas no formato `LC` (Linha e Coluna juntos, ex: `11`, `23`, `33`).

---
*Nota: Este README foi gerado com o auxílio de Inteligência Artificial para fins de organização e estruturação do projeto acadêmico.*