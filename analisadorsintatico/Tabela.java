package com.mycompany.analisadorsintatico;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mille
 */
public class Tabela {
    private final Map<NaoTerminais, Map<Terminais, Producao>> tabela = new HashMap<>();
    
    public Tabela(){
    //adicionando linhas 
    tabela.put(NaoTerminais.Prog, new HashMap<>());
    tabela.put(NaoTerminais.LD, new HashMap<>());
    tabela.put(NaoTerminais.LDlinha, new HashMap<>());
    tabela.put(NaoTerminais.Decl, new HashMap<>());
    tabela.put(NaoTerminais.TipoVar, new HashMap<>());
    tabela.put(NaoTerminais.EA, new HashMap<>());
    tabela.put(NaoTerminais.EAlinha, new HashMap<>());
    tabela.put(NaoTerminais.TA, new HashMap<>());
    tabela.put(NaoTerminais.TAlinha, new HashMap<>());
    tabela.put(NaoTerminais.FA, new HashMap<>());
    tabela.put(NaoTerminais.ER, new HashMap<>());
    tabela.put(NaoTerminais.ERlinha, new HashMap<>());
    tabela.put(NaoTerminais.TR, new HashMap<>());
    tabela.put(NaoTerminais.OPBol, new HashMap<>());
    tabela.put(NaoTerminais.LC, new HashMap<>());
    tabela.put(NaoTerminais.LClinha, new HashMap<>());
    tabela.put(NaoTerminais.CMD, new HashMap<>());
    tabela.put(NaoTerminais.CMDAtrib, new HashMap<>());
    tabela.put(NaoTerminais.CMDEnt, new HashMap<>());
    tabela.put(NaoTerminais.CMDSaida, new HashMap<>());
    tabela.put(NaoTerminais.CMDCond, new HashMap<>());
    tabela.put(NaoTerminais.CMDCondlinha, new HashMap<>());
    tabela.put(NaoTerminais.CMDRep, new HashMap<>());
    tabela.put(NaoTerminais.SUB, new HashMap<>());
    tabela.put(NaoTerminais.Saida, new HashMap<>());

    tabela.get(NaoTerminais.Prog).put(Terminais.DOIS_PONTOS, Producao.R1); // :DEC inicia o programa
    tabela.get(NaoTerminais.LD).put(Terminais.Var, Producao.R2);
    tabela.get(NaoTerminais.LDlinha).put(Terminais.Var, Producao.R3a);
    tabela.get(NaoTerminais.LDlinha).put(Terminais.DOIS_PONTOS, Producao.R3b);
    tabela.get(NaoTerminais.Decl).put(Terminais.Var, Producao.R4);
    tabela.get(NaoTerminais.TipoVar).put(Terminais.PCInt, Producao.R5a);
    tabela.get(NaoTerminais.TipoVar).put(Terminais.PCReal, Producao.R5b);
    tabela.get(NaoTerminais.EA).put(Terminais.NumInt, Producao.R6);
    tabela.get(NaoTerminais.EA).put(Terminais.NumReal, Producao.R6);
    tabela.get(NaoTerminais.EA).put(Terminais.Var, Producao.R6);
    tabela.get(NaoTerminais.EA).put(Terminais.ABRE_PAR, Producao.R6);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.MAIS, Producao.R7a);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.MENOS, Producao.R7b);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.OPRel, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.FECHA_PAR, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.E, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.OU, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCEntao, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.Var, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCLer, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCImprimir, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCSe, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCEnqto, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCIni, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.Ecomercial, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCFim, Producao.R7c);
    tabela.get(NaoTerminais.EAlinha).put(Terminais.PCSenao, Producao.R7c);
    tabela.get(NaoTerminais.TA).put(Terminais.NumInt, Producao.R8);
    tabela.get(NaoTerminais.TA).put(Terminais.NumReal, Producao.R8);
    tabela.get(NaoTerminais.TA).put(Terminais.Var, Producao.R8);
    tabela.get(NaoTerminais.TA).put(Terminais.ABRE_PAR, Producao.R8);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.MULT, Producao.R9a);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.DIV, Producao.R9b);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.MAIS, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.MENOS, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.OPRel, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.FECHA_PAR, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.E, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.OU, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCEntao, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.Var, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCLer, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCImprimir, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCSe, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCEnqto, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCIni, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.Ecomercial, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCFim, Producao.R9c);
    tabela.get(NaoTerminais.TAlinha).put(Terminais.PCSenao, Producao.R9c);
    tabela.get(NaoTerminais.FA).put(Terminais.NumInt, Producao.R10a);
    tabela.get(NaoTerminais.FA).put(Terminais.NumReal, Producao.R10b);
    tabela.get(NaoTerminais.FA).put(Terminais.Var, Producao.R10c);
    tabela.get(NaoTerminais.FA).put(Terminais.ABRE_PAR, Producao.R10d);
    tabela.get(NaoTerminais.ER).put(Terminais.NumInt, Producao.R11);
    tabela.get(NaoTerminais.ER).put(Terminais.NumReal, Producao.R11);
    tabela.get(NaoTerminais.ER).put(Terminais.Var, Producao.R11);
    tabela.get(NaoTerminais.ER).put(Terminais.ABRE_PAR, Producao.R11);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.E, Producao.R12a);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.OU, Producao.R12a);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.PCEntao, Producao.R12b);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.Var, Producao.R12b);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.PCLer, Producao.R12b);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.PCImprimir, Producao.R12b);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.PCSe, Producao.R12b);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.PCEnqto, Producao.R12b);
    tabela.get(NaoTerminais.ERlinha).put(Terminais.PCIni, Producao.R12b);
    tabela.get(NaoTerminais.TR).put(Terminais.NumInt, Producao.R13);
    tabela.get(NaoTerminais.TR).put(Terminais.NumReal, Producao.R13);
    tabela.get(NaoTerminais.TR).put(Terminais.Var, Producao.R13);
    tabela.get(NaoTerminais.TR).put(Terminais.ABRE_PAR, Producao.R13);
    tabela.get(NaoTerminais.OPBol).put(Terminais.E, Producao.R14a);
    tabela.get(NaoTerminais.OPBol).put(Terminais.OU, Producao.R14b);
    tabela.get(NaoTerminais.LC).put(Terminais.Var, Producao.R15);
    tabela.get(NaoTerminais.LC).put(Terminais.PCLer, Producao.R15);
    tabela.get(NaoTerminais.LC).put(Terminais.PCImprimir, Producao.R15);
    tabela.get(NaoTerminais.LC).put(Terminais.PCSe, Producao.R15);
    tabela.get(NaoTerminais.LC).put(Terminais.PCEnqto, Producao.R15);
    tabela.get(NaoTerminais.LC).put(Terminais.PCIni, Producao.R15);
    tabela.get(NaoTerminais.LClinha).put(Terminais.Var, Producao.R16a);
    tabela.get(NaoTerminais.LClinha).put(Terminais.PCLer, Producao.R16a);
    tabela.get(NaoTerminais.LClinha).put(Terminais.PCImprimir, Producao.R16a);
    tabela.get(NaoTerminais.LClinha).put(Terminais.PCSe, Producao.R16a);
    tabela.get(NaoTerminais.LClinha).put(Terminais.PCEnqto, Producao.R16a);
    tabela.get(NaoTerminais.LClinha).put(Terminais.PCIni, Producao.R16a);
    tabela.get(NaoTerminais.LClinha).put(Terminais.Ecomercial, Producao.R16b);
    tabela.get(NaoTerminais.LClinha).put(Terminais.PCFim, Producao.R16b);
    tabela.get(NaoTerminais.CMD).put(Terminais.Var, Producao.R17a);
    tabela.get(NaoTerminais.CMD).put(Terminais.PCLer, Producao.R17b); 
    tabela.get(NaoTerminais.CMD).put(Terminais.PCImprimir, Producao.R17c); 
    tabela.get(NaoTerminais.CMD).put(Terminais.PCSe, Producao.R17d); 
    tabela.get(NaoTerminais.CMD).put(Terminais.PCEnqto, Producao.R17e); 
    tabela.get(NaoTerminais.CMD).put(Terminais.PCIni, Producao.R17f); 
    tabela.get(NaoTerminais.CMDAtrib).put(Terminais.Var, Producao.R18);
    tabela.get(NaoTerminais.CMDEnt).put(Terminais.PCLer, Producao.R19);
    tabela.get(NaoTerminais.CMDSaida).put(Terminais.PCImprimir, Producao.R20);
    tabela.get(NaoTerminais.Saida).put(Terminais.Var, Producao.R21a);
    tabela.get(NaoTerminais.Saida).put(Terminais.Cadeia, Producao.R21b);
    tabela.get(NaoTerminais.CMDCond).put(Terminais.PCSe, Producao.R22);
    tabela.get(NaoTerminais.CMDCondlinha).put(Terminais.PCSenao, Producao.R23a);
    tabela.get(NaoTerminais.CMDCondlinha).put(Terminais.PCFim, Producao.R23b); 
    tabela.get(NaoTerminais.CMDRep).put(Terminais.PCEnqto, Producao.R24);
    tabela.get(NaoTerminais.SUB).put(Terminais.PCIni, Producao.R25);
    
    }

    /**
     * Consulta a tabela LL(1).
     *
     * @param nt  não-terminal no topo da pilha
     * @param t   terminal do token corrente
     * @return    produção a aplicar, ou null se não há entrada
     */
    public Producao get(NaoTerminais nt, Terminais t) {
        java.util.Map<Terminais, Producao> linha = tabela.get(nt);
        if (linha == null) return null;
        return linha.get(t);
    }
    
}