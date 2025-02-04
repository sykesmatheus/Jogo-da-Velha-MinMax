import java.util.ArrayList;
import java.util.List;


public class Tabuleiro {
    public char[][] tabuleiro;  // Matriz 3x3 que representa o tabuleiro

    // Construtor: inicializa o tabuleiro com espaços vazios
    public Tabuleiro() {
        this.tabuleiro = new char[3][3];
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                this.tabuleiro[linha][coluna] = ' ';
            }
        }
    }

    // Tenta adicionar um elemento ao tabuleiro. Retorna true se a jogada foi válida
    public boolean adicionarElemento(int linha, int coluna, char caracter) {
        // Verifica se a posição está dentro dos limites e se está vazia
        if (linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3 && tabuleiro[linha][coluna] == ' ') {
            tabuleiro[linha][coluna] = caracter;
            return true;
        }
        return false;
    }

    // Gera todas as possíveis jogadas para o jogador especificado
    public List<Tabuleiro> gerarCandidatos(char jogador) {
        List<Tabuleiro> candidatos = new ArrayList<>();

        // Percorre todas as células do tabuleiro
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                if (tabuleiro[linha][coluna] == ' ') { // Célula vazia encontrada
                    // Cria uma cópia do tabuleiro atual
                    Tabuleiro candidato = new Tabuleiro();
                    for (int i = 0; i < 3; i++) {
                        System.arraycopy(this.tabuleiro[i], 0, candidato.tabuleiro[i], 0, 3);
                    }
                    // Faz a jogada na cópia
                    candidato.tabuleiro[linha][coluna] = jogador;
                    candidatos.add(candidato);
                }
            }
        }
        return candidatos;
    }

    // Exibe o tabuleiro formatado no console
    public void exibirTabuleiro() {
        System.out.println("\n  1   2   3"); // Cabeçalho das colunas
        for (int i = 0; i < 3; i++) {
            System.out.print((i + 1) + " "); // Número da linha
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < 2) System.out.print(" | "); // Separador vertical
            }
            System.out.println();
            if (i < 2) System.out.println("  ---------"); // Separador horizontal
        }
        System.out.println();
    }

    // Verifica se o jogador especificado venceu
    public boolean verificarGanhador(char jogador) {
        // Verifica vitória em colunas
        for (int coluna = 0; coluna < 3; coluna++) {
            if (tabuleiro[0][coluna] == jogador &&
                    tabuleiro[1][coluna] == jogador &&
                    tabuleiro[2][coluna] == jogador) {
                return true;
            }
        }

        // Verifica vitória em linhas
        for (int linha = 0; linha < 3; linha++) {
            if (tabuleiro[linha][0] == jogador &&
                    tabuleiro[linha][1] == jogador &&
                    tabuleiro[linha][2] == jogador) {
                return true;
            }
        }

        // Verifica diagonal principal (de cima para baixo)
        if (tabuleiro[0][0] == jogador &&
                tabuleiro[1][1] == jogador &&
                tabuleiro[2][2] == jogador) {
            return true;
        }

        // Verifica diagonal secundária (de baixo para cima)
        if (tabuleiro[0][2] == jogador &&
                tabuleiro[1][1] == jogador &&
                tabuleiro[2][0] == jogador) {
            return true;
        }

        return false;
    }

    // Verifica se o jogo terminou em empate
    public boolean verificarEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return false; // Ainda há jogadas possíveis
                }
            }
        }
        return true; // Todas as células estão preenchidas
    }

    // Avalia o estado atual do jogo: 1 para vitória do O, -1 para X, 0 para empate
    public int avaliar() {
        if (verificarGanhador('O')) return 1;
        if (verificarGanhador('X')) return -1;
        return 0;
    }

    // Verifica se o jogo chegou a um estado terminal (vitória ou empate)
    public boolean isTerminal() {
        return verificarGanhador('X') || verificarGanhador('O') || verificarEmpate();
    }

    // Algoritmo Minimax para determinar a melhor jogada
    public int minMax(boolean maximizar) {
        // Caso base: se o jogo terminou (vitória ou empate), retorna a avaliação do estado
        if (isTerminal()) return avaliar();

        // Inicializa o melhor valor:
        // - Se for a vez da IA (maximizar), começa com o menor valor possível para buscar o máximo
        // - Se for a vez do humano (minimizar), começa com o maior valor possível para buscar o mínimo
        int melhorValor = maximizar ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Define o jogador atual com base na estratégia:
        // - maximizar = true: jogador é a máquina (O)
        // - maximizar = false: jogador é o humano (X)
        char jogadorAtual = maximizar ? 'O' : 'X';

        // Gera todas as jogadas possíveis para o jogador atual
        for (Tabuleiro candidato : gerarCandidatos(jogadorAtual)) {
            // Simula recursivamente a jogada do oponente no próximo nível:
            // - Alterna a estratégia (maximizar/minimizar) para o próximo jogador
            int valor = candidato.minMax(!maximizar);

            // Atualiza o melhor valor conforme a estratégia:
            if (maximizar) {
                // Estratégia da IA (O): escolhe o maior valor para maximizar suas chances
                melhorValor = Math.max(melhorValor, valor);
            } else {
                // Estratégia do humano (X): escolhe o menor valor para minimizar as chances da IA
                melhorValor = Math.min(melhorValor, valor);
            }
        }

        // Retorna o melhor valor encontrado para este nível da árvore de decisão
        return melhorValor;
    }



}