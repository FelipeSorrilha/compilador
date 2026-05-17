/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analisadorsintatico;

/**
 *
 * @author Mille
 */
public enum Terminais implements Symbol {
    PCProg, PCDec, PCInt, PCReal, PCLer, PCImprimir,
    PCSe, PCEntao, PCSenao, PCEnqto, PCIni, PCFim,

    MAIS, MENOS, MULT, DIV,
    OPRel,
    ATRIB, // =
    E, OU,
    DOIS_PONTOS, // :
    ABRE_PAR, // (
    FECHA_PAR, // )
    
    Var, NumInt, NumReal, Cadeia,
    Ecomercial // $
}
