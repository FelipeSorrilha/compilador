package com.mycompany.analisadorsintatico;

public class Token {
    public final Terminais tipo;
    public final String lexema;
    public final int linha;

    public Token(Terminais tipo, String lexema, int linha) {
        this.tipo   = tipo;
        this.lexema = lexema;
        this.linha  = linha;
    }

    @Override
    public String toString() {
        return tipo + "(\"" + lexema + "\")";
    }
}