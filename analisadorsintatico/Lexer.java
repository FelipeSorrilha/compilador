package com.mycompany.analisadorsintatico;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String src;
    private int pos;
    private int linha;

    public Lexer(String src) {
        this.src  = src;
        this.pos  = 0;
        this.linha = 1;
    }

    public List<Token> tokenizar() {
        List<Token> tokens = new ArrayList<>();

        while (pos < src.length()) {
            pularEspacosEComentarios();
            if (pos >= src.length()) break;

            Token t = proximoToken();
            if (t != null) {
                tokens.add(t);
            }
        }

        tokens.add(new Token(Terminais.Ecomercial, "$", linha));
        return tokens;
    }

    private void pularEspacosEComentarios() {
        while (pos < src.length()) {
            char c = src.charAt(pos);

            if (c == '\n') {
                pos++;
                linha++;
            } else if (c == ' ' || c == '\t' || c == '\r') {
                pos++;
            } else if (c == '#') {
                // Comentário: ignora até fim da linha
                while (pos < src.length() && src.charAt(pos) != '\n') {
                    pos++;
                }
            } else {
                break;
            }
        }
    }

    private Token proximoToken() {
        int linhaInicio = linha;
        char c = src.charAt(pos);

        if (c == ':') {
            if (pos + 1 < src.length() && src.charAt(pos + 1) == '=') {
                pos += 2;
                return new Token(Terminais.ATRIB, ":=", linhaInicio);
            }
            pos++;
            return new Token(Terminais.DOIS_PONTOS, ":", linhaInicio);
        }

        if (c == '"') {
            return lerCadeia(linhaInicio);
        }

        if (Character.isDigit(c)) {
            return lerNumero(linhaInicio);
        }

        if (Character.isLetter(c) || c == '_') {
            return lerPalavra(linhaInicio);
        }

        if (c == '=') {
            if (pos + 1 < src.length() && src.charAt(pos + 1) == '=') {
                pos += 2;
                return new Token(Terminais.OPRel, "==", linhaInicio);
            }
            pos++;
            return new Token(Terminais.ATRIB, "=", linhaInicio);
        }
        if (c == '<') {
            if (pos + 1 < src.length() && src.charAt(pos + 1) == '=') {
                pos += 2; return new Token(Terminais.OPRel, "<=", linhaInicio);
            }
            if (pos + 1 < src.length() && src.charAt(pos + 1) == '>') {
                pos += 2; return new Token(Terminais.OPRel, "<>", linhaInicio);
            }
            pos++;
            return new Token(Terminais.OPRel, "<", linhaInicio);
        }
        if (c == '!') {
            if (pos + 1 < src.length() && src.charAt(pos + 1) == '=') {
                pos += 2; return new Token(Terminais.OPRel, "!=", linhaInicio);
            }
            System.err.println("Erro léxico: '!' sem '=' na linha " + linha);
            pos++;
            return null;
        }
        if (c == '>') {
            if (pos + 1 < src.length() && src.charAt(pos + 1) == '=') {
                pos += 2; return new Token(Terminais.OPRel, ">=", linhaInicio);
            }
            pos++;
            return new Token(Terminais.OPRel, ">", linhaInicio);
        }

        switch (c) {
            case '+': pos++; return new Token(Terminais.MAIS,      "+", linhaInicio);
            case '-': pos++; return new Token(Terminais.MENOS,     "-", linhaInicio);
            case '*': pos++; return new Token(Terminais.MULT,      "*", linhaInicio);
            case '/': pos++; return new Token(Terminais.DIV,       "/", linhaInicio);
            case '(': pos++; return new Token(Terminais.ABRE_PAR,  "(", linhaInicio);
            case ')': pos++; return new Token(Terminais.FECHA_PAR, ")", linhaInicio);
        }

        System.err.println("Erro léxico: caractere inesperado '" + c + "' na linha " + linha);
        pos++;
        return null;
    }

    private Token lerCadeia(int linhaInicio) {
        pos++; 
        StringBuilder sb = new StringBuilder();
        while (pos < src.length() && src.charAt(pos) != '"') {
            if (src.charAt(pos) == '\n') linha++;
            sb.append(src.charAt(pos++));
        }
        if (pos < src.length()) pos++; 
        return new Token(Terminais.Cadeia, "\"" + sb + "\"", linhaInicio);
    }

    private Token lerNumero(int linhaInicio) {
        StringBuilder sb = new StringBuilder();
        boolean isReal = false;
        while (pos < src.length() && (Character.isDigit(src.charAt(pos)) || src.charAt(pos) == '.')) {
            if (src.charAt(pos) == '.') isReal = true;
            sb.append(src.charAt(pos++));
        }
        String s = sb.toString();
        return new Token(isReal ? Terminais.NumReal : Terminais.NumInt, s, linhaInicio);
    }

    private Token lerPalavra(int linhaInicio) {
        StringBuilder sb = new StringBuilder();
        while (pos < src.length()
               && (Character.isLetterOrDigit(src.charAt(pos)) || src.charAt(pos) == '_')) {
            sb.append(src.charAt(pos++));
        }
        String palavra = sb.toString();

        switch (palavra) {
            case "DEC":      return new Token(Terminais.PCDec,       palavra, linhaInicio);
            case "PROG":     return new Token(Terminais.PCProg,      palavra, linhaInicio);
            case "INT":      return new Token(Terminais.PCInt,       palavra, linhaInicio);
            case "REAL":     return new Token(Terminais.PCReal,      palavra, linhaInicio);
            case "LER":      return new Token(Terminais.PCLer,       palavra, linhaInicio);
            case "IMPRIMIR": return new Token(Terminais.PCImprimir,  palavra, linhaInicio);
            case "SE":       return new Token(Terminais.PCSe,        palavra, linhaInicio);
            case "ENTAO":    return new Token(Terminais.PCEntao,     palavra, linhaInicio);
            case "SENAO":    return new Token(Terminais.PCSenao,     palavra, linhaInicio);
            case "ENQTO":    return new Token(Terminais.PCEnqto,     palavra, linhaInicio);
            case "INI":      return new Token(Terminais.PCIni,       palavra, linhaInicio);
            case "FIM":      return new Token(Terminais.PCFim,       palavra, linhaInicio);
            case "E":        return new Token(Terminais.E,           palavra, linhaInicio);
            case "OU":       return new Token(Terminais.OU,          palavra, linhaInicio);
            default:         return new Token(Terminais.Var,         palavra, linhaInicio);
        }
    }
}