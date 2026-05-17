package com.mycompany.analisadorsintatico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Analisador léxico: usa o algoritmo ll1
 *
 */
public class AnaliseLexica {

    private final Map<String, Terminais> palavrasReservadas = new HashMap<>();
    private final Map<Character, Terminais> operadoresSimples  = new HashMap<>();

    public final LeArquivo ldat;
    private int linha = 1;

    public AnaliseLexica(String nome) throws IOException {
        ldat = new LeArquivo(nome);

        // Palavras-chave
        palavrasReservadas.put("DEC",      Terminais.PCDec);
        palavrasReservadas.put("PROG",     Terminais.PCProg);
        palavrasReservadas.put("INT",      Terminais.PCInt);
        palavrasReservadas.put("REAL",     Terminais.PCReal);
        palavrasReservadas.put("LER",      Terminais.PCLer);
        palavrasReservadas.put("IMPRIMIR", Terminais.PCImprimir);
        palavrasReservadas.put("SE",       Terminais.PCSe);
        palavrasReservadas.put("ENTAO",    Terminais.PCEntao);
        palavrasReservadas.put("SENAO",    Terminais.PCSenao);
        palavrasReservadas.put("ENQTO",    Terminais.PCEnqto);
        palavrasReservadas.put("INI",      Terminais.PCIni);
        palavrasReservadas.put("FIM",      Terminais.PCFim);
        palavrasReservadas.put("E",        Terminais.E);
        palavrasReservadas.put("OU",       Terminais.OU);

        // Operadores de um único caractere
        operadoresSimples.put('(', Terminais.ABRE_PAR);
        operadoresSimples.put(')', Terminais.FECHA_PAR);
        operadoresSimples.put('+', Terminais.MAIS);
        operadoresSimples.put('-', Terminais.MENOS);
        operadoresSimples.put('*', Terminais.MULT);
        operadoresSimples.put('/', Terminais.DIV);
    }

    // Retorna a lista completa de tokens 
    public List<Token> tokenizarTodos() throws IOException {
        List<Token> tokens = new ArrayList<>();
        Token t;
        while ((t = analisaCaractere()) != null) {
            tokens.add(t);
        }
        tokens.add(new Token(Terminais.Ecomercial, "$", linha));
        return tokens;
    }

    // Classificação de caractere
    public boolean ehLetra(char c) { return Character.isLetter(c); }
    public boolean ehNumero(char c) { return Character.isDigit(c); }

    public boolean ehInicioOperador(char c) {
        return c == '=' || c == '>' || c == '<' || c == '!' || c == ':' || c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Reconhece operadores
    public Token reconheceOperador(char c) throws IOException {
        int linhaInicio = linha;

        if (c == '<') {
            ldat.lendo();
            char prox = ldat.getCaractere();
            if (prox == '=') { ldat.lendo(); return new Token(Terminais.OPRel, "<=", linhaInicio); }
            if (prox == '>') { ldat.lendo(); return new Token(Terminais.OPRel, "<>", linhaInicio); }
            return new Token(Terminais.OPRel, "<", linhaInicio);
        }

        if (c == '=') {
            ldat.lendo();
            char prox = ldat.getCaractere();
            if (prox == '=') { ldat.lendo(); return new Token(Terminais.OPRel, "==", linhaInicio); }
            throw new RuntimeException("Erro léxico na linha " + linha + ": '=' isolado não é válido");
        }

        if (c == '>') {
            ldat.lendo();
            char prox = ldat.getCaractere();
            if (prox == '=') { ldat.lendo(); return new Token(Terminais.OPRel, ">=", linhaInicio); }
            return new Token(Terminais.OPRel, ">", linhaInicio);
        }

        if (c == '!') {
            ldat.lendo();
            char prox = ldat.getCaractere();
            if (prox == '=') { ldat.lendo(); return new Token(Terminais.OPRel, "!=", linhaInicio); }
            throw new RuntimeException("Erro léxico na linha " + linha + ": '!' deve ser seguido de '='");
        }

        if (c == ':') {
            ldat.lendo();
            char prox = ldat.getCaractere();
            if (prox == '=') { ldat.lendo(); return new Token(Terminais.ATRIB, ":=", linhaInicio); }
            return new Token(Terminais.DOIS_PONTOS, ":", linhaInicio);
        }

        // Operadores simples 
        Terminais tipo = operadoresSimples.get(c);
        if (tipo != null) {
            ldat.lendo();
            return new Token(tipo, String.valueOf(c), linhaInicio);
        }

        return null;
    }

    // Lê identificadores e palavras-chave
    public Token agrupaLetras(char c) throws IOException {
        int linhaInicio = linha;
        StringBuilder lexema = new StringBuilder();

        while (ehLetra(c) || ehNumero(c)) {
            lexema.append(c);
            ldat.lendo();
            c = ldat.getCaractere();
        }

        String palavra = lexema.toString();

        // Verifica se é palavra-chave
        Terminais tipo = palavrasReservadas.get(palavra);
        if (tipo != null) {
            return new Token(tipo, palavra, linhaInicio);
        }

        // Identificador deve começar com letra minúscula
        if (Character.isLowerCase(palavra.charAt(0))) {
            return new Token(Terminais.Var, palavra, linhaInicio);
        }

        throw new RuntimeException(
            "Erro léxico na linha " + linhaInicio + ": identificador inválido -> " + palavra
        );
    }

    // Lê cadeias entre aspas duplas
    public Token analisaCadeia(char c) throws IOException {
        int linhaInicio = linha;
        StringBuilder lexema = new StringBuilder();
        lexema.append('"');

        ldat.lendo();
        c = ldat.getCaractere();

        while (c != '"') {
            if (ldat.verificaEOF()) {
                throw new RuntimeException("Erro léxico na linha " + linhaInicio + ": cadeia não fechada");
            }
            if (c == '\n') linha++;
            lexema.append(c);
            ldat.lendo();
            c = ldat.getCaractere();
        }

        lexema.append('"');
        ldat.lendo(); // consome '"' de fechamento

        return new Token(Terminais.Cadeia, lexema.toString(), linhaInicio);
    }

    // Lê números inteiros e reais
    public Token analisaNumero(char c) throws IOException {
        int linhaInicio = linha;
        StringBuilder lexema = new StringBuilder();
        boolean ehReal = false;

        while (ehNumero(c)) {
            lexema.append(c);
            ldat.lendo();
            c = ldat.getCaractere();
        }

        if (c == '.') {
            ehReal = true;
            lexema.append(c);
            ldat.lendo();
            c = ldat.getCaractere();

            if (!ehNumero(c)) {
                throw new RuntimeException(
                    "Erro léxico na linha " + linhaInicio + ": número real mal formado"
                );
            }

            while (ehNumero(c)) {
                lexema.append(c);
                ldat.lendo();
                c = ldat.getCaractere();
            }
        }

        String numero = lexema.toString();
        return new Token(ehReal ? Terminais.NumReal : Terminais.NumInt, numero, linhaInicio);
    }

    // Loop principal: lê e retorna um token por chamada 
    public Token analisaCaractere() throws IOException {
        while (!ldat.verificaEOF()) {
            char c = ldat.getCaractere();

            // Quebra de linha: incrementa contador
            if (c == '\n') {
                linha++;
                ldat.lendo();
                continue;
            }

            // Espaços e tabulações
            if (Character.isWhitespace(c)) {
                ldat.lendo();
                continue;
            }

            // Comentário: ignora tudo até o fim da linha
            if (c == '#') {
                while (!ldat.verificaEOF() && ldat.getCaractere() != '\n') {
                    ldat.lendo();
                }
                continue;
            }

            if (ehNumero(c)) return analisaNumero(c);
            if (ehLetra(c)) return agrupaLetras(c);
            if (c == '"') return analisaCadeia(c);
            if (ehInicioOperador(c)) return reconheceOperador(c);

            System.err.println("Erro léxico na linha " + linha + ": caractere inesperado '" + c + "'");
            ldat.lendo();
        }
        return null;
    }
}
