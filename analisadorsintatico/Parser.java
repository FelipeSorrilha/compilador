package com.mycompany.analisadorsintatico;



import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Analisador sintático LL(1) preditivo não-recursivo.
 *
 * Algoritmo:
 *   1. Empilha $ e o símbolo inicial (Prog).
 *   2. Repete até aceitar ou erro:
 *      a) Se topo == terminal:
 *           - Topo == token atual → casamento; desempilha e avança.
 *           - Senão → ERRO (token inesperado).
 *      b) Se topo == não-terminal:
 *           - Consulta tabela[topo][token atual].
 *           - Produção encontrada → desempilha topo, empilha lado direito
 *             (na ordem inversa, para que o primeiro símbolo fique no topo).
 *           - Senão → ERRO (nenhuma produção aplicável).
 *      c) Se topo == $ e token == $ → ACEITO.
 */
public class Parser {

    private final List<Token> tokens;
    private int pos;
    private final Tabela tabela;

    private boolean verbose = true; // exibe cada passo quando true

    public Parser(List<Token> tokens, Tabela tabela) {
        this.tokens = tokens;
        this.tabela = tabela;
        this.pos    = 0;
    }

    /** Define se os passos da derivação devem ser exibidos no console. */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    // -----------------------------------------------------------------------
    // Token corrente e avanço
    // -----------------------------------------------------------------------
    private Token tokenAtual() {
        return tokens.get(pos);
    }

    private void avancar() {
        if (pos < tokens.size() - 1) pos++;
    }

    // -----------------------------------------------------------------------
    // Loop principal LL(1)
    // -----------------------------------------------------------------------
    public boolean analisar() {
        Deque<Symbol> pilha = new ArrayDeque<>();
        pilha.push(Terminais.Ecomercial); // marcador de fundo
        pilha.push(NaoTerminais.Prog);    // símbolo inicial

        if (verbose) {
            System.out.println("=== Análise Sintática LL(1) ===\n");
        }

        while (true) {
            Symbol topo  = pilha.peek();
            Token  atual = tokenAtual();

            // ── Condição de aceitação ──────────────────────────────────────
            if (topo == Terminais.Ecomercial) {
                if (atual.tipo == Terminais.Ecomercial) {
                    if (verbose) System.out.println("\n>>> ACEITO <<<");
                    return true;
                } else {
                    erroSintatico("Tokens inesperados após fim do programa", atual, null);
                    return false;
                }
            }

            // ── Topo é terminal ──────────────────────────────────────────
            if (topo instanceof Terminais) {
                Terminais topoT = (Terminais) topo;

                if (topoT == atual.tipo) {
                    //if (verbose) {
                        //System.out.printf("  Casou:   %-18s com  \"%s\"%n",
                                          //topoT, atual.lexema);
                   // }
                    pilha.pop();
                    avancar();

                } else {
                    erroSintatico(
                        "Esperado terminal '" + topoT + "'",
                        atual, null
                    );
                    return false;
                }

            // ── Topo é não-terminal ──────────────────────────────────────
            } else {
                NaoTerminais topoNT = (NaoTerminais) topo;
                Producao prod = tabela.get(topoNT, atual.tipo);

                if (prod == null) {
                    erroSintatico(
                        "Nenhuma produção para " + topoNT + " com token '" + atual.tipo + "'",
                        atual, topoNT
                    );
                    return false;
                }

                //if (verbose) {
                //    System.out.printf("  Aplicou: %s%n", prod);
                //}

                pilha.pop();

                // Empilha o lado direito em ordem inversa
                List<Symbol> ladoDireito = prod.getRight();
                for (int i = ladoDireito.size() - 1; i >= 0; i--) {
                    pilha.push(ladoDireito.get(i));
                }
            }
        }
    }

    // -----------------------------------------------------------------------
    // Formata e exibe a mensagem de erro
    // -----------------------------------------------------------------------
    private void erroSintatico(String motivo, Token token, NaoTerminais esperado) {
        System.err.println();
        System.err.println("=== ERRO SINTÁTICO ===");
        System.err.println("  Linha    : " + token.linha);
        System.err.println("  Token    : " + token.tipo);
        System.err.println("  Lexema   : \"" + token.lexema + "\"");
        if (esperado != null) {
            System.err.println("  Esperado : algum símbolo inicial de " + esperado);
        }
        System.err.println("  Detalhe  : " + motivo);
        System.err.println("======================");
        System.exit(1);
    }
}