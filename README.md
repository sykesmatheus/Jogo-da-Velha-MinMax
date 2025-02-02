# Jogo-da-Velha-MinMax
Jogo da Velha com IA invencível usando o algoritmo Minimax. Implementação em Java.

## ✨ Funcionalidades

- **Jogador vs. Computador**: Desafie a IA em partidas clássicas.
- **Algoritmo Minimax**: IA toma decisões ótimas para nunca perder.
- **Interface Simples**: Tabuleiro interativo no terminal.
- **Verificação de Vitória/Empate**: Detecta automaticamente o fim do jogo.

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 11+ instalado.

### Passos:
1. Clone o repositório:
   git clone https://github.com/sykesmatheus/Jogo-da-Velha-MinMax.git
2. Compile e execute:
  cd jogo-da-velha-ia
  javac Tabuleiro.java
  java Tabuleiro

📖 Explicação do Algoritmo Minimax
O Minimax é um algoritmo de decisão que busca minimizar a perda máxima em jogos de adversário. Ele simula todas as jogadas possíveis e escolhe a que maximiza as chances de vitória da IA, assumindo que o oponente também joga de forma ótima.

Implementação-Chave:

  public int minMax(boolean maximizar) {
    if (isTerminal()) return avaliar();
    
    int melhorValor = maximizar ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    char jogadorAtual = maximizar ? 'O' : 'X'; // 'O' é a IA
    
    for (Tabuleiro candidato : gerarCandidatos(jogadorAtual)) {
        int valor = candidato.minMax(!maximizar);
        melhorValor = maximizar ? Math.max(melhorValor, valor) : Math.min(melhorValor, valor);
    }
    
    return melhorValor;
  }
gerarCandidatos(): Gera todos os movimentos válidos para o jogador atual.

avaliar(): Retorna 1 (vitória da IA), -1 (vitória humana) ou 0 (empate).

isTerminal(): Verifica se o jogo terminou.

🧩 Estrutura do Código
Tabuleiro.java: Contém toda a lógica do jogo:

exibirTabuleiro(): Mostra o tabuleiro no terminal.

verificarGanhador(): Checa linhas, colunas e diagonais.

minMax(): Implementação do algoritmo de decisão.


