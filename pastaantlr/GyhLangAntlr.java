/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.paludeto.gyh.lang.antlr;

import gyhlangantlr.GyhGrammarLexer;
import gyhlangantlr.GyhGrammarParser;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

/**
 *
    * @author a2605643
 */

public class GyhLangAntlr {

    public static void main(String[] args) throws IOException {
        CharStream cs = CharStreams.fromFileName("programa.gyh");
        GyhGrammarLexer lexer = new GyhGrammarLexer(cs);
        Token t; 
        t = lexer.nextToken(); 
        while(t.getType() != Token.EOF){
            System.out.println("<" + t.getText() + " , " + GyhGrammarLexer.VOCABULARY.getSymbolicName(t.getType()) + ">");
            t = lexer.nextToken();
        } 
        
        CharStream css = CharStreams.fromFileName("programa.gyh");
        GyhGrammarLexer lexer2 = new GyhGrammarLexer(css);
        CommonTokenStream token = new CommonTokenStream(lexer2);
        GyhGrammarParser parser = new GyhGrammarParser(token);
        
        parser.programa();
    } // Closed the main method bracket
} // Closed the class bracket