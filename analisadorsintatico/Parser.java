package com.mycompany.analisadorsintatico;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos;
    private final Tabela tabela;

    private boolean verbose = true;

    public Parser(List<Token> tokens, Tabela tabela) {
        this.tokens = tokens;
        this.tabela = tabela;
        this.pos    = 0;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    private Token tokenAtual() {
        return tokens.get(pos);
    }

    private void avancar() {
        if (pos < tokens.size() - 1) pos++;
    }

    public boolean analisar() {
        Deque<Symbol> pilha = new ArrayDeque<>();
        pilha.push(Terminais.Ecomercial); 
        pilha.push(NaoTerminais.Prog);    

        if (verbose) {
            System.out.println("=== Análise Sintática LL(1) ===\n");
        }

        while (true) {
            Symbol topo  = pilha.peek();
            Token  atual = tokenAtual();

            if (topo == Terminais.Ecomercial) {
                if (atual.tipo == Terminais.Ecomercial) {
                    if (verbose) System.out.println("\n>>> ACEITO <<<");
                    return true;
                } else {
                    erroSintatico("Tokens inesperados após fim do programa", atual, null);
                    return false;
                }
            }

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
                List<Symbol> ladoDireito = prod.getRight();
                for (int i = ladoDireito.size() - 1; i >= 0; i--) {
                    pilha.push(ladoDireito.get(i));
                }
            }
        }
    }

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