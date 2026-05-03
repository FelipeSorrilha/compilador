/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projetocompiladores;

import java.io.IOException;

/**
 *
 * @author Millena
 */

//obs: Prof, caso vc queira add seus proprios testes, pros testes rodarem o .txt tem que ser colocado na raiz do projeto, dentro do src não roda 
public class ProjetoCompiladores {
    
    public static void main(String[] args) {
        try {
            AnaliseLexica lexer = new AnaliseLexica("teste.txt");
            
            Token token;
            
            while ((token = lexer.analisaCaractere()) != null) {
                System.out.println(token);
            }
            
        } catch (IOException ex) {
            System.out.println("Erro ao abrir ou ler o arquivo!");
        }
    }
}
