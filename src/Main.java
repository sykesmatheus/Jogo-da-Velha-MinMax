import java.util.List;
import java.util.Scanner;

public class Main {

    //Metodo principal que controla o fluxo do jogo
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Tabuleiro tabuleiro = new Tabuleiro();
        char jogadorAtual = 'X'; // Começa com o jogador
        boolean jogoAtivo = true;

        System.out.println("Jogo da Velha - Você é X vs Computador O");

        while (jogoAtivo) {
            if (jogadorAtual == 'X') { // Turno do jogador
                tabuleiro.exibirTabuleiro();

                int linha, coluna;
                do {
                    System.out.print("Sua vez (linha 1-3 e coluna 1-3): ");
                    linha = scanner.nextInt() - 1; // Ajusta para índice 0-based
                    coluna = scanner.nextInt() - 1;
                } while (!tabuleiro.adicionarElemento(linha, coluna, 'X')); // Valida a entrada
            }
            else {
                // Turno da IA
                System.out.println("\nVez do computador...");

                // Gera todas as jogadas possíveis para 'O'
                List<Tabuleiro> candidatos = tabuleiro.gerarCandidatos('O');

                // Inicializa o melhor valor com o menor inteiro possível (busca de maximização)
                int melhorValor = Integer.MIN_VALUE;
                Tabuleiro melhorJogada = candidatos.get(0); // Inicializa com a primeira jogada

                // Avalia cada jogada candidata
                for (Tabuleiro candidato : candidatos) {
                    // Simula a resposta do jogador humano (X) usando Minimax com minimização
                    int valorAtual = candidato.minMax(false);

                    // Atualiza a melhor jogada se encontrar um valor maior
                    if (valorAtual > melhorValor) {
                        melhorValor = valorAtual;
                        melhorJogada = candidato;
                    }
                }

                // Aplica a jogada escolhida ao tabuleiro real
                boolean jogadaAplicada = false;
                for (int i = 0; i < 3 && !jogadaAplicada; i++) {
                    for (int j = 0; j < 3 && !jogadaAplicada; j++) {
                        // Encontra a posição onde o candidato difere do tabuleiro atual
                        if (tabuleiro.tabuleiro[i][j] != melhorJogada.tabuleiro[i][j]) {
                            tabuleiro.adicionarElemento(i, j, 'O'); // Coloca 'O' na posição correta
                            jogadaAplicada = true; // Garante que só uma jogada seja aplicada
                        }
                    }
                }
            }

            // Verifica condições de término
            if (tabuleiro.verificarGanhador(jogadorAtual)) {
                tabuleiro.exibirTabuleiro();
                System.out.println(jogadorAtual == 'X' ? "Você venceu!" : "Computador venceu!");
                jogoAtivo = false;
            }
            else if (tabuleiro.verificarEmpate()) {
                tabuleiro.exibirTabuleiro();
                System.out.println("Empate!");
                jogoAtivo = false;
            }

            jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X'; // Alterna jogadores
        }
        scanner.close();
    }
}
