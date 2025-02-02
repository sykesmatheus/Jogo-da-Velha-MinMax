import java.util.List;
import java.util.Scanner;

public class Main {

    // Metodo principal que controla o fluxo do jogo
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
            else { // Turno da IA
                System.out.println("\nVez do computador...");

                List<Tabuleiro> candidatos = tabuleiro.gerarCandidatos('O');
                int melhorValor = Integer.MAX_VALUE;
                Tabuleiro melhorJogada = candidatos.get(0);

                // Avalia todas as jogadas possíveis
                for (Tabuleiro candidato : candidatos) {
                    int valorAtual = candidato.minMax(true); // Simula jogadas futuras
                    if (valorAtual < melhorValor) {
                        melhorValor = valorAtual;
                        melhorJogada = candidato;
                    }
                }

                // Aplica a melhor jogada encontrada
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (tabuleiro.tabuleiro[i][j] != melhorJogada.tabuleiro[i][j]) {
                            tabuleiro.adicionarElemento(i, j, 'O');
                            break;
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
