/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetocompiladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Millena
 */

//le o programa escrito na linguagem GYH
public class LeArquivo {
    private final InputStream arquivoInput; 
    private int charAtual; 
    
    public LeArquivo(String nome) throws IOException{
       arquivoInput = new FileInputStream(nome);
       lendo(); //ja vai pro primeiro caractere
    }
    
    public final void lendo() throws IOException{
        charAtual = arquivoInput.read();
    }
    
    //retorna o char atual 
    public char getCaractere(){
        return (char) charAtual; 
    }
    
    //ve se eh o fim do arquivo --> se eh -1, ent acabou
    public boolean verificaEOF(){
        return charAtual == -1; 
    }
    
    //fecha o arquivo
    public void close() throws IOException {
        arquivoInput.close();
    }

}
