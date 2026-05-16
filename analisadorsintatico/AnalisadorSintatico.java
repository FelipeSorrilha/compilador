package com.mycompany.analisadorsintatico;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Ponto de entrada do analisador sintático LL(1) para a linguagem GYH.
 *
 * Uso:
 *   java AnalisadorSintatico <arquivo.gyh>
 *
 * Formato esperado do arquivo .gyh:
 *   :DEC
 *     variavel : INT
 *     variavel : REAL
 *   :PROG
 *     LER variavel
 *     variavel := expressao
 *     SE condicao ENTAO comando FIM
 *     ENQTO condicao comando
 *     INI lista_de_comandos FIM
 *     IMPRIMIR variavel | "cadeia"
 */
public class AnalisadorSintatico {

    public static void main(String[] args) {

        String caminhoArquivo = "programa1.gyh";

        // ── 2. Lê o conteúdo do arquivo ────────────────────────────────────
        String conteudo;
        try {
            conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo '" + caminhoArquivo + "': " + e.getMessage());
            System.exit(1);
            return;
        }

        System.out.println("Arquivo: " + caminhoArquivo);
        System.out.println("─".repeat(45));

        // ── 3. Análise léxica ──────────────────────────────────────────────
        Lexer lexer = new Lexer(conteudo);
        List<Token> tokens = lexer.tokenizar();

        System.out.println("Tokens gerados:");
        for (Token t : tokens) {
            System.out.printf("  L%-4d  %-18s \"%s\"%n", t.linha, t.tipo, t.lexema);
        }
        System.out.println("─".repeat(45));

        // ── 4. Análise sintática LL(1) ─────────────────────────────────────
        Tabela tabela = new Tabela();
        Parser parser = new Parser(tokens, tabela);
        parser.setVerbose(true);

        boolean aceito = parser.analisar();

        System.out.println("─".repeat(45));
        if (aceito) {
            System.out.println("Resultado: PROGRAMA ACEITO");
        } else {
            System.out.println("Resultado: PROGRAMA REJEITADO (erro sintático)");
            System.exit(2);
        }
    }
}