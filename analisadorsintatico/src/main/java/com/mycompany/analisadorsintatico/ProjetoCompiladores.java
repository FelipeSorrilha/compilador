package com.mycompany.analisadorsintatico;

import java.io.IOException;
import java.util.List;

/**
 * Execute esse código aqui!!!!
 *
 * Acontece, em sequência:
 * Análise léxica: produz e exibe a lista de tokens
 * Análise sintática LL(1): aceita ou rejeita o programa
 * 
 * Obs: o arquivo .gyh deve estar na raiz do projeto (fora de src/).
 */

public class ProjetoCompiladores {

    private static final String ARQUIVO_PADRAO = "programa12.gyh";

    public static void main(String[] args) {

        String caminhoArquivo = (args.length > 0) ? args[0] : ARQUIVO_PADRAO;

        System.out.println("Arquivo : " + caminhoArquivo);
        System.out.println("=".repeat(50));

        // Análise Léxica
        List<Token> tokens;
        try {
            AnaliseLexica lexer = new AnaliseLexica(caminhoArquivo);
            tokens = lexer.tokenizarTodos();
        } catch (IOException e) {
            System.err.println("Erro ao abrir/ler arquivo '" + caminhoArquivo + "': " + e.getMessage());
            System.exit(2);
            return;
        } catch (RuntimeException e) {
            // Erros léxicos
            System.err.println(e.getMessage());
            System.exit(2);
            return;
        }

        System.out.println("=== Tokens gerados (Análise Léxica) ===");
        for (Token t : tokens) {
            System.out.printf("  L%-4d  %-18s \"%s\"%n", t.linha, t.tipo, t.lexema);
        }
        System.out.println("=".repeat(50));

        // Análise Sintática LL(1) 
        Tabela tabela = new Tabela();
        Parser parser = new Parser(tokens, tabela);
        parser.setVerbose(true);

        boolean aceito = parser.analisar();

        System.out.println("─".repeat(50));
        if (aceito) {
            System.out.println("Resultado: PROGRAMA ACEITO");
        } else {
            System.out.println("Resultado: PROGRAMA REJEITADO (erro sintático)");
            System.exit(2);
        }
    }
}
