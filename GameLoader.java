
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameLoader {

    // Método principal responsável por carregar as configurações do jogo.
    // Recebe o caminho do arquivo JSON e utiliza as informações dele
    // para criar o tabuleiro, os jogadores e as regras do jogo.
    //
    // Caso alguma configuração esteja incorreta, lança uma exceção
    // informando o problema encontrado.
    public GameEngine carregarJogo(String caminhoArquivo) throws InvalidConfigurationException {

        String conteudoJson;

        // 1. LEITURA DO ARQUIVO JSON

        // Tenta abrir e ler todo o conteúdo do arquivo JSON.
        // O caminho do arquivo é recebido pelo parâmetro caminhoArquivo.
        // O conteúdo lido é armazenado na variável conteudoJson.
        try {
            conteudoJson = Files.readString(Path.of(caminhoArquivo));

        } catch (IOException e) {

            // Se ocorrer algum erro na leitura (arquivo inexistente,
            // caminho incorreto ou problema de acesso), informa o erro.
            throw new InvalidConfigurationException(
                "Não foi possível ler o arquivo de configuração: " + caminhoArquivo
            );
        }


        // 2. EXTRAÇÃO E VALIDAÇÃO DO NOME DO JOGO

        // Procura dentro do JSON o campo chamado "game".
        // Extrai o texto que estiver associado a esse campo.
        // Exemplo: "game": "Jogo da Velha"
        String nomeJogo = extrairString(conteudoJson, "game");

        // Verifica se o nome foi encontrado e se não está vazio.
        // isBlank() verifica se a string está vazia ou contém apenas espaços.
        if (nomeJogo == null || nomeJogo.isBlank()) {

            // Se não existir um nome válido, interrompe o carregamento.
            throw new InvalidConfigurationException(
                "O nome do jogo ('game') não foi definido no JSON."
            );
        }


        // 3. EXTRAÇÃO E VALIDAÇÃO DAS DIMENSÕES DO TABULEIRO

        // Busca no JSON os valores numéricos correspondentes às linhas
        // e às colunas do tabuleiro.
        // Exemplo: "rows": 3 e "columns": 3.
        int linhas = extrairInteiro(conteudoJson, "rows");
        int colunas = extrairInteiro(conteudoJson, "columns");

        // Verifica se as dimensões são válidas.
        // Um tabuleiro precisa ter pelo menos uma linha e uma coluna.
        // Portanto, os dois valores devem ser maiores que zero.
        if (linhas <= 0 || colunas <= 0) {

            // Se alguma dimensão for zero ou negativa, informa o erro.
            // O texto também apresenta as dimensões encontradas.
            throw new InvalidConfigurationException(
                "Dimensões de tabuleiro inválidas (" + linhas + "x" + colunas +
                "). Linhas e colunas devem ser maiores que zero."
            );
        }


        // 4. EXTRAÇÃO E VALIDAÇÃO DOS SÍMBOLOS DOS JOGADORES

        // Procura no JSON o campo "symbols".
        // Esse campo contém uma lista de símbolos utilizados pelos jogadores.
        // Exemplo: "symbols": ["X", "O"]
        List<String> simbolos = extrairListaStrings(conteudoJson, "symbols");

        // Verifica se existem pelo menos dois símbolos.
        // Isso é necessário porque o jogo precisa de pelo menos dois jogadores.
        if (simbolos.size() < 2) {

            throw new InvalidConfigurationException(
                "O jogo precisa de pelo menos 2 símbolos de jogadores no JSON."
            );
        }

        // Cria uma lista vazia para armazenar os objetos Jogador.
        // Cada jogador será criado utilizando um símbolo encontrado no JSON.
        List<Jogador> jogadores = new ArrayList<>();

        // Percorre todos os símbolos encontrados na lista.
        // O índice i começa em zero e aumenta até alcançar o tamanho da lista.
        for (int i = 0; i < simbolos.size(); i++) {

            // Obtém o símbolo atual e remove possíveis espaços
            // que estejam antes ou depois do texto.
            String simboloStr = simbolos.get(i).trim();

            // Verifica se o símbolo está vazio após remover os espaços.
            if (simboloStr.isEmpty()) {

                throw new InvalidConfigurationException(
                    "Símbolo de jogador vazio encontrado na configuração."
                );
            }

            // Cria um novo objeto Jogador.
            //
            // "Jogador " + (i + 1) gera nomes automáticos:
            // Índice 0 = Jogador 1
            // Índice 1 = Jogador 2
            // Índice 2 = Jogador 3
            //
            // charAt(0) pega o primeiro caractere do símbolo.
            // Exemplo: se o símbolo for "X", retorna o caractere 'X'.
            //
            // O objeto criado é adicionado à lista de jogadores.
            jogadores.add(new Jogador("Jogador " + (i + 1), simboloStr.charAt(0)));
        }


        // 5. INSTANCIAÇÃO POLIMÓRFICA DA REGRA DE POSICIONAMENTO (PlacementRule)

        // Procura no JSON o campo "placement".
        // Esse campo informa como as peças podem ser posicionadas no tabuleiro.
        // Exemplo: "placement": "EMPTY_POSITION"
        String tipoPosicionamento = extrairString(conteudoJson, "placement");

        // Chama o método criarPlacementRule para identificar qual regra
        // deve ser utilizada e criar o objeto correspondente.
        //
        // O retorno é armazenado em uma variável do tipo PlacementRule.
        // Isso permite utilizar diferentes implementações da mesma regra
        // por meio do polimorfismo.
        PlacementRule placementRule = criarPlacementRule(tipoPosicionamento);


        // 6. INSTANCIAÇÃO POLIMÓRFICA DA CONDIÇÃO DE VITÓRIA (WinCondition)

        // Extrai do JSON o tipo de condição necessária para vencer.
        // Exemplo: "type": "ALIGNMENT"
        String tipoVitoria = extrairString(conteudoJson, "type");

        // Extrai a quantidade necessária para vencer.
        // Exemplo: "quantity": 3 significa que são necessários três
        // símbolos alinhados para vencer.
        int quantidadeParaVencer = extrairInteiro(conteudoJson, "quantity");

        // Cria a condição de vitória correspondente ao tipo informado.
        // O método recebe o tipo de vitória e a quantidade necessária.
        WinCondition winCondition = criarWinCondition(tipoVitoria, quantidadeParaVencer);


        // 7. MONTAGEM DO TABULEIRO E CRIAÇÃO DA GAME ENGINE

        // Cria o objeto Tabuleiro utilizando as dimensões extraídas do JSON.
        // Exemplo: se linhas = 3 e colunas = 3, cria um tabuleiro 3x3.
        Tabuleiro tabuleiro = new Tabuleiro(linhas, colunas);

        // Retorna um novo objeto GameEngine.
        //
        // A GameEngine recebe todas as configurações necessárias:
        // 1. Tabuleiro criado.
        // 2. Lista de jogadores.
        // 3. Regra de posicionamento.
        // 4. Condição de vitória.
        // 5. Nome do jogo.
        //
        // Assim, o jogo é entregue já configurado para ser executado.
        return new GameEngine(tabuleiro, jogadores, placementRule, winCondition, nomeJogo);
    }


    // MÉTODO AUXILIAR: CRIAÇÃO DA REGRA DE POSICIONAMENTO

    // Recebe o nome da regra encontrada no JSON e decide
    // qual classe será utilizada para controlar o posicionamento das peças.
    private PlacementRule criarPlacementRule(String tipo) throws InvalidConfigurationException {

        // Verifica se o campo placement não foi encontrado no JSON.
        if (tipo == null) {

            throw new InvalidConfigurationException(
                "Regra de posicionamento ('placement') ausente no JSON."
            );
        }

        // Converte o texto para letras maiúsculas e compara
        // com as regras de posicionamento disponíveis.
        // O switch permite escolher qual objeto será criado.
        switch (tipo.toUpperCase()) {

            // Se o JSON indicar EMPTY_POSITION,
            // cria uma regra que permite posicionar peças em posições vazias.
            case "EMPTY_POSITION":
                return new EmptyPositionRule();

            // Se o JSON indicar GRAVITY_COLUMN,
            // cria uma regra de posicionamento baseada em gravidade.
            // As peças devem seguir a lógica de queda nas colunas.
            case "GRAVITY_COLUMN":
                return new GravityColumnRule();

            // Se o tipo não corresponder a nenhuma regra conhecida,
            // interrompe a execução e informa que a configuração é inválida.
            default:
                throw new InvalidConfigurationException(
                    "Regra de posicionamento '" + tipo + "' não reconhecida."
                );
        }
    }


    // MÉTODO AUXILIAR: CRIAÇÃO DA CONDIÇÃO DE VITÓRIA

    // Recebe o tipo de vitória e a quantidade necessária para vencer.
    // Retorna o objeto que representa a condição de vitória escolhida.
    private WinCondition criarWinCondition(String tipo, int quantidade)
            throws InvalidConfigurationException {

        // Verifica se o tipo de condição de vitória foi informado no JSON.
        if (tipo == null) {

            throw new InvalidConfigurationException(
                "Condição de vitória ('winCondition.type') ausente no JSON."
            );
        }

        // Analisa qual condição de vitória foi solicitada.
        switch (tipo.toUpperCase()) {

            // ALIGNMENT representa uma condição de alinhamento.
            // Exemplo: alinhar 3 símbolos na horizontal, vertical ou diagonal.
            case "ALIGNMENT":

                // Verifica se a quantidade necessária para vencer
                // é um número válido, ou seja, maior que zero.
                if (quantidade <= 0) {

                    throw new InvalidConfigurationException(
                        "A quantidade para vencer ('quantity') deve ser maior que zero."
                    );
                }

                // Cria um objeto AlignmentWinCondition.
                // A quantidade é enviada ao construtor para definir
                // quantos símbolos precisam estar alinhados para vencer.
                return new AlignmentWinCondition(quantidade);

            // Caso o tipo de vitória não seja reconhecido,
            // informa que a configuração não é válida.
            default:
                throw new InvalidConfigurationException(
                    "Regra de vitória '" + tipo + "' não reconhecida."
                );
        }
    }


    // MÉTODOS AUXILIARES PARA LEITURA DO JSON

    // MÉTODO 1: EXTRAIR UMA STRING

    // Recebe o conteúdo completo do JSON e o nome de uma chave.
    // Procura o valor textual associado à chave e retorna esse valor.
    //
    // Exemplo:
    // JSON: "game": "Jogo da Velha"
    // Chave recebida: "game"
    // Retorno: "Jogo da Velha"
    //
    // Se não encontrar a chave, retorna null.
    private String extrairString(String json, String chave) {

        // Pattern.compile cria um padrão de busca (expressão regular).
        //
        // O padrão procura:
        // 1. O nome da chave entre aspas.
        // 2. Dois-pontos após a chave.
        // 3. Um valor também entre aspas.
        //
        // \\s* permite a existência de espaços entre os elementos.
        // ([^"]*) captura o texto que está dentro das aspas.
        Pattern pattern = Pattern.compile(
            "\"" + chave + "\"\\s*:\\s*\"([^\"]*)\""
        );

        // Aplica o padrão ao conteúdo completo do JSON.
        Matcher matcher = pattern.matcher(json);

        // Verifica se encontrou alguma correspondência.
        if (matcher.find()) {

            // group(1) retorna o texto capturado dentro das aspas.
            return matcher.group(1);
        }

        // Se não encontrar a chave ou seu valor, retorna null.
        return null;
    }


    // MÉTODO 2: EXTRAIR UM NÚMERO INTEIRO

    // Recebe o JSON e o nome de uma chave que deve possuir um número inteiro.
    // Retorna o número encontrado como um int.
    //
    // Exemplo:
    // JSON: "rows": 3
    // Chave recebida: "rows"
    // Retorno: 3
    //
    // Se o campo não existir ou não possuir um número válido,
    // lança uma exceção informando o problema.
    private int extrairInteiro(String json, String chave)
            throws InvalidConfigurationException {

        // Cria um padrão de busca para localizar números inteiros.
        //
        // -? permite que o número tenha sinal negativo.
        // \\d+ representa um ou mais algarismos.
        //
        // Exemplo de valores encontrados: 3, 5, -2.
        Pattern pattern = Pattern.compile(
            "\"" + chave + "\"\\s*:\\s*(-?\\d+)"
        );

        // Procura o número correspondente à chave no JSON.
        Matcher matcher = pattern.matcher(json);

        // Se encontrar o valor numérico:
        if (matcher.find()) {

            // group(1) retorna o número encontrado como texto.
            // Integer.parseInt converte esse texto para o tipo int.
            //
            // Exemplo: "3" passa a ser o número inteiro 3.
            return Integer.parseInt(matcher.group(1));
        }

        // Se não encontrar um número válido,
        // interrompe a execução informando que o campo está ausente ou inválido.
        throw new InvalidConfigurationException(
            "Campo numérico obrigatório '" + chave + "' ausente ou inválido no JSON."
        );
    }


    // MÉTODO 3: EXTRAIR UMA LISTA DE STRINGS

    // Recebe o JSON e o nome de uma chave que contém uma lista de textos.
    // Retorna uma lista contendo todos os textos encontrados.
    //
    // Exemplo:
    // JSON: "symbols": ["X", "O"]
    // Chave recebida: "symbols"
    // Retorno: lista contendo "X" e "O".
    private List<String> extrairListaStrings(String json, String chave) {

        // Cria uma lista vazia que armazenará os textos encontrados.
        List<String> resultado = new ArrayList<>();

        // Cria um padrão para localizar o conteúdo de um array no JSON.
        //
        // \\[ e \\] representam os colchetes que delimitam a lista.
        // (.*?) captura o conteúdo que está dentro desses colchetes.
        // DOTALL permite que a busca também funcione quando existem
        // quebras de linha dentro do array.
        Pattern patternArray = Pattern.compile(
            "\"" + chave + "\"\\s*:\\s*\\[(.*?)\\]",
            Pattern.DOTALL
        );

        // Aplica o padrão de busca ao conteúdo do JSON.
        Matcher matcherArray = patternArray.matcher(json);

        // Verifica se encontrou o array correspondente à chave.
        if (matcherArray.find()) {

            // group(1) recupera tudo que está dentro dos colchetes.
            //
            // Exemplo:
            // ["X", "O"]
            // Conteúdo recuperado: "X", "O"
            String conteudoArray = matcherArray.group(1);

            // Cria outro padrão para localizar cada texto entre aspas.
            // ([^"]+) captura um ou mais caracteres que não sejam aspas.
            Pattern patternItem = Pattern.compile("\"([^\"]+)\"");

            // Procura os textos individualmente dentro do array encontrado.
            Matcher matcherItem = patternItem.matcher(conteudoArray);

            // Enquanto encontrar novos textos dentro da lista:
            while (matcherItem.find()) {

                // group(1) recupera o texto encontrado.
                // Adiciona esse texto à lista resultado.
                resultado.add(matcherItem.group(1));
            }
        }

        // Retorna a lista com todos os textos encontrados.
        // Se nenhum elemento for encontrado, retorna uma lista vazia.
        return resultado;
    }
}
