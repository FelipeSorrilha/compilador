/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetocompiladores;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Millena
 */
public class AnaliseLexica {
    private Map<String, TipoToken> palavrasReservadas = new HashMap<>();
    private final Map<Character, TipoToken> operadoresReservados = new HashMap<>();
    
    public LeArquivo ldat;
    
    public AnaliseLexica(String nome) throws IOException{
            ldat = new LeArquivo(nome);
        
        palavrasReservadas = new HashMap<>();

        palavrasReservadas.put("DEC", TipoToken.PCDec);
        palavrasReservadas.put("PROG", TipoToken.PCProg);
        palavrasReservadas.put("INT", TipoToken.PCInt);
        palavrasReservadas.put("LER", TipoToken.PCLer);
        palavrasReservadas.put("REAL", TipoToken.PCReal);
        palavrasReservadas.put("IMPRIMIR", TipoToken.PCImprimir);
        palavrasReservadas.put("SE", TipoToken.PCSe);
        palavrasReservadas.put("SENAO", TipoToken.PCSenao);
        palavrasReservadas.put("ENTAO", TipoToken.PCEntao);
        palavrasReservadas.put("ENQTO", TipoToken.PCEnqto);
        palavrasReservadas.put("INI", TipoToken.PCIni);
        palavrasReservadas.put("FIM", TipoToken.PCFim);
        palavrasReservadas.put("E", TipoToken.OpBoolE);
        palavrasReservadas.put("OU", TipoToken.OpBoolOu);
        
        operadoresReservados.put('(', TipoToken.AbrePar);
        operadoresReservados.put(')', TipoToken.FechaPar);
        operadoresReservados.put('+', TipoToken.OpAritSoma);
        operadoresReservados.put('-', TipoToken.OpAritSub);
        operadoresReservados.put('*', TipoToken.OpAritMult);
        operadoresReservados.put('/', TipoToken.OpAritDiv);
    }
    
    public boolean ehLetra(char c){
            return Character.isLetter(c);
    }
    
    public boolean ehNumero(char c){
            return Character.isDigit(c);
    }
    
    public boolean ehInicioOperador(char c){
        //vê apenas se pode vir a ser um operador
        //só verifica se eh operador mesmo em outro método --> reconheceOperador()
        return c == '=' || c == '>' || c == '<' || c == '!' || c == ':' || c == '(' || c == ')' 
                || c == '+' || c == '-' || c == '*' || c == '/';
    }
    
    public Token reconheceOperador(char c) throws IOException{
        if (c == '<'){
            ldat.lendo();
            char prox = ldat.getCaractere();
            
            if(prox == '='){
                ldat.lendo();
                return new Token("<=", TipoToken.OpRelMenorIgual);
            }else{
                //se for qualquer outro caractere, então se trata de <+[alguma coisa dps] -->OpRelMenor
                return new Token("<", TipoToken.OpRelMenor);
            }
        }
        
        if (c == '='){
            ldat.lendo();
            char prox = ldat.getCaractere();
            
            if(prox == '='){
                ldat.lendo();
                return new Token("==", TipoToken.OpRelIgual); //OpRelIgual
            }else{
                //apenas um = nao existe aqui. erro lexico
                throw new RuntimeException("Erro léxico: '=' isolado não é válido"); 
            }
        }
        
        if (c == '>'){
            ldat.lendo();
            char prox = ldat.getCaractere();
            
            if(prox == '='){
                ldat.lendo();
                return new Token(">=", TipoToken.OpRelMaiorIgual); //OpRelMaiorIgual
            }else{
                return new Token(">", TipoToken.OpRelMaior); //OpRelMaior
            }
        }
        
        if (c == '!'){
            ldat.lendo();
            char prox = ldat.getCaractere();
            
            if(prox == '='){
                ldat.lendo();
                return new Token("!=", TipoToken.OpRelDif); //OpRelDif
            }else {
                throw new RuntimeException("Erro léxico: '!' deve ser seguido de '='"); 
            }
        }
        
        if (c == ':'){
            ldat.lendo(); 
            char prox = ldat.getCaractere();
            
            if(prox == '='){
                ldat.lendo();
                return new Token(":=", TipoToken.Atrib); //Atrib
            }else{
                return new Token(":", TipoToken.Delim); //Delim
            }
        }
        
        TipoToken tipo = operadoresReservados.get(c);
        if (tipo != null){
            ldat.lendo();
            return new Token(String.valueOf(c), tipo);
        }
        
        return null; 
    }
    
    //o buffer acumula letras que receber para que possa analisar se eh identificador/palavra-chave
    public Token agrupaLetras(char c) throws IOException{

        StringBuilder lexema = new StringBuilder();
        
        while(ehLetra(c) || ehNumero(c)){ //como variaveis podem ter numero, tem que entrar na condicao tb!
            lexema.append(c);
            ldat.lendo(); 
            c = ldat.getCaractere();
        }
        
        String palavra = lexema.toString();
        
        //verificar se eh identificador ou palavra chave:
        //if (pertence a palavras reservadas) palavra chave
        //else identificador
        
        TipoToken tipo = palavrasReservadas.get(palavra);
        
        if (tipo != null){
            return new Token(palavra, tipo);
        }
        
        if (Character.isLowerCase(palavra.charAt(0))){
            return new Token(palavra, TipoToken.Var);
        }
        
        throw new RuntimeException("Erro léxico: identificador inválido -> " + palavra); 
    }
    
    public Token analisaCadeia(char c) throws IOException{
        StringBuilder lexema = new StringBuilder();
        
        if(c == '"'){
            ldat.lendo();
            c = ldat.getCaractere();
            
            while(c != '"'){
                if(ldat.verificaEOF()){
                    throw new RuntimeException("Erro léxico: cadeia não fechada");
                }
                
                lexema.append(c);
                ldat.lendo();
                c = ldat.getCaractere();
            }
            
            ldat.lendo();
            
            String cadeia = lexema.toString();
            
            return new Token(cadeia, TipoToken.Cadeia);
        }
        
        return null;
    }
    
    public Token analisaNumero(char c) throws IOException{
        //System.out.println("ENTROU NUMERO");
        StringBuilder lexema = new StringBuilder();
        boolean ehReal = false; 
        
        //verifica a parte inteira do numero
        while (ehNumero(c)){
            lexema.append(c);
            ldat.lendo();
            c = ldat.getCaractere();
        }
        
        //parte decimal
        if (c == '.'){
            ehReal = true; 
            lexema.append(c);
            ldat.lendo();
            c = ldat.getCaractere();
            
            if(!ehNumero(c)){
                throw new RuntimeException("Erro léxico: número real mal formado");
            }
            
            //ve dps do ponto
            while (ehNumero(c)){
                lexema.append(c);
                ldat.lendo();
                c = ldat.getCaractere();
            }
        }
        
        String numero = lexema.toString();
        
        if(ehReal){
            return new Token(numero, TipoToken.NumReal);
        }else{
            return new Token(numero, TipoToken.NumInt);
        }
    }
    
    public Token analisaCaractere() throws IOException{
        //enquanto nao tiver acabado o arquivo, continua rodando
        while(!ldat.verificaEOF()){
            char c = ldat.getCaractere();
           
            //usei pra debugar prof kkkk
            //System.out.println("DEBUG CHAR: [" + c + "] ASCII: " + (int)c);
            
            //trata espaço
            if(Character.isWhitespace(c)){
                ldat.lendo();
                continue; 
            }
            
            //trata numero
            if (ehNumero(c)){
                return analisaNumero(c);
            }
            
            //trata letra
            if (ehLetra(c)){
                return agrupaLetras(c);
            }
            
            //trata cadeia de caracteres 
            if (c == '"'){
                return analisaCadeia(c);
            }
            
            //trata operador
            if (ehInicioOperador(c)){
                return reconheceOperador(c);
            }
            
            //erro léxico
            System.out.println("Erro léxico: " + c);
            ldat.lendo();
        }
        return null;
    }
}

