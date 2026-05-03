/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetocompiladores;

/**
 *
 * @author Millena
 */
public class Token {
    public TipoToken padrao;
    public String lexema;
    
    @Override
    public String toString(){
        return "<"+lexema+","+ padrao+">";
    }
    
    
    public Token(String lexema, TipoToken padrao){
        this.lexema=lexema;
        this.padrao=padrao;
    }
}
