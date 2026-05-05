/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analisadorsintatico;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Producao {

    private final NaoTerminais left;
    private final List<Symbol> right;

    // Construtor

    public Producao(NaoTerminais left, List<Symbol> right) {
        this.left  = left;
        this.right = Collections.unmodifiableList(right);
    }

    public NaoTerminais getLeft()  { return left; }
    public List<Symbol> getRight() { return right; }

    public boolean isEpsilon() { return right.isEmpty(); }

    @Override
    public String toString() {
        if (right.isEmpty()) return left + " → ε";
        StringBuilder sb = new StringBuilder(left + " → ");
        for (int i = 0; i < right.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(right.get(i));
        }
        return sb.toString();
    }


    // Cria um lado direito com os símbolos fornecidos
    private static List<Symbol> of(Symbol... symbols) {
        return Arrays.asList(symbols);
    }

    // Representa ε: lado direito vazio.
    private static List<Symbol> eps() {
        return Collections.emptyList();
    }


    //  TODAS AS PRODUÇÕES DA GRAMÁTICA GYH

    /** R1 : Prog → PCProg : PCDec LD : PCProg LC */
    public static final Producao R1 = new Producao(
        NaoTerminais.Prog,
        of(
            Terminais.PCProg,
            Terminais.DOIS_PONTOS,
            Terminais.PCDec,
            NaoTerminais.LD,
            Terminais.DOIS_PONTOS,
            Terminais.PCProg,
            NaoTerminais.LC
        )
    );

    /** R2 : LD → Decl LD' */
    public static final Producao R2 = new Producao(
        NaoTerminais.LD,
        of(NaoTerminais.Decl, NaoTerminais.LDlinha)
    );

    /** R3a : LD' → Decl LD' */
    public static final Producao R3a = new Producao(
        NaoTerminais.LDlinha,
        of(NaoTerminais.Decl, NaoTerminais.LDlinha)
    );

    /** R3b : LD' → ε */
    public static final Producao R3b = new Producao(
        NaoTerminais.LDlinha,
        eps()
    );

    /** R4 : Decl → Var : TipoVar */
    public static final Producao R4 = new Producao(
        NaoTerminais.Decl,
        of(Terminais.Var, Terminais.DOIS_PONTOS, NaoTerminais.TipoVar)
    );

    /** R5a : TipoVar → PCInt */
    public static final Producao R5a = new Producao(
        NaoTerminais.TipoVar,
        of(Terminais.PCInt)
    );

    /** R5b : TipoVar → PCReal */
    public static final Producao R5b = new Producao(
        NaoTerminais.TipoVar,
        of(Terminais.PCReal)
    );

    // ── Expressões Aritméticas ─────────────────────────────────────────────────

    /** R6 : EA → TA EA' */
    public static final Producao R6 = new Producao(
        NaoTerminais.EA,
        of(NaoTerminais.TA, NaoTerminais.EAlinha)
    );

    /** R7a : EA' → + TA EA' */
    public static final Producao R7a = new Producao(
        NaoTerminais.EAlinha,
        of(Terminais.MAIS, NaoTerminais.TA, NaoTerminais.EAlinha)
    );

    /** R7b : EA' → - TA EA' */
    public static final Producao R7b = new Producao(
        NaoTerminais.EAlinha,
        of(Terminais.MENOS, NaoTerminais.TA, NaoTerminais.EAlinha)
    );

    /** R7c : EA' → ε */
    public static final Producao R7c = new Producao(
        NaoTerminais.EAlinha,
        eps()
    );

    /** R8 : TA → FA TA' */
    public static final Producao R8 = new Producao(
        NaoTerminais.TA,
        of(NaoTerminais.FA, NaoTerminais.TAlinha)
    );

    /** R9a : TA' → * FA TA' */
    public static final Producao R9a = new Producao(
        NaoTerminais.TAlinha,
        of(Terminais.MULT, NaoTerminais.FA, NaoTerminais.TAlinha)
    );

    /** R9b : TA' → / FA TA' */
    public static final Producao R9b = new Producao(
        NaoTerminais.TAlinha,
        of(Terminais.DIV, NaoTerminais.FA, NaoTerminais.TAlinha)
    );

    /** R9c : TA' → ε */
    public static final Producao R9c = new Producao(
        NaoTerminais.TAlinha,
        eps()
    );

    /** R10a : FA → NumInt */
    public static final Producao R10a = new Producao(
        NaoTerminais.FA,
        of(Terminais.NumInt)
    );

    /** R10b : FA → NumReal */
    public static final Producao R10b = new Producao(
        NaoTerminais.FA,
        of(Terminais.NumReal)
    );

    /** R10c : FA → Var */
    public static final Producao R10c = new Producao(
        NaoTerminais.FA,
        of(Terminais.Var)
    );

    /** R10d : FA → ( EA ) */
    public static final Producao R10d = new Producao(
        NaoTerminais.FA,
        of(Terminais.ABRE_PAR, NaoTerminais.EA, Terminais.FECHA_PAR)
    );

    // ── Expressões Relacionais e Booleanas ────────────────────────────────────

    /** R11 : ER → TR ER' */
    public static final Producao R11 = new Producao(
        NaoTerminais.ER,
        of(NaoTerminais.TR, NaoTerminais.ERlinha)
    );

    /** R12a : ER' → OPBol TR ER' */
    public static final Producao R12a = new Producao(
        NaoTerminais.ERlinha,
        of(NaoTerminais.OPBol, NaoTerminais.TR, NaoTerminais.ERlinha)
    );

    /** R12b : ER' → ε */
    public static final Producao R12b = new Producao(
        NaoTerminais.ERlinha,
        eps()
    );

    /**
     * R13 : TR → EA OPRel EA
     * (única regra de TR — conflito com "TR → ( ER )" foi removido)
     */
    public static final Producao R13 = new Producao(
        NaoTerminais.TR,
        of(NaoTerminais.EA, Terminais.OPRel, NaoTerminais.EA)
    );

    /** R14a : OPBol → E */
    public static final Producao R14a = new Producao(
        NaoTerminais.OPBol,
        of(Terminais.E)
    );

    /** R14b : OPBol → OU */
    public static final Producao R14b = new Producao(
        NaoTerminais.OPBol,
        of(Terminais.OU)
    );

    // ── Comandos ──────────────────────────────────────────────────────────────

    /** R15 : LC → CMD LC' */
    public static final Producao R15 = new Producao(
        NaoTerminais.LC,
        of(NaoTerminais.CMD, NaoTerminais.LClinha)
    );

    /** R16a : LC' → CMD LC' */
    public static final Producao R16a = new Producao(
        NaoTerminais.LClinha,
        of(NaoTerminais.CMD, NaoTerminais.LClinha)
    );

    /** R16b : LC' → ε */
    public static final Producao R16b = new Producao(
        NaoTerminais.LClinha,
        eps()
    );

    /** R17a : CMD → CMDAtrib */
    public static final Producao R17a = new Producao(
        NaoTerminais.CMD,
        of(NaoTerminais.CMDAtrib)
    );

    /** R17b : CMD → CMDEnt */
    public static final Producao R17b = new Producao(
        NaoTerminais.CMD,
        of(NaoTerminais.CMDEnt)
    );

    /** R17c : CMD → CMDSaida */
    public static final Producao R17c = new Producao(
        NaoTerminais.CMD,
        of(NaoTerminais.CMDSaida)
    );

    /** R17d : CMD → CMDCond */
    public static final Producao R17d = new Producao(
        NaoTerminais.CMD,
        of(NaoTerminais.CMDCond)
    );

    /** R17e : CMD → CMDRep */
    public static final Producao R17e = new Producao(
        NaoTerminais.CMD,
        of(NaoTerminais.CMDRep)
    );

    /** R17f : CMD → SUB */
    public static final Producao R17f = new Producao(
        NaoTerminais.CMD,
        of(NaoTerminais.SUB)
    );

    /** R18 : CMDAtrib → Var = EA */
    public static final Producao R18 = new Producao(
        NaoTerminais.CMDAtrib,
        of(Terminais.Var, Terminais.ATRIB, NaoTerminais.EA)
    );

    /** R19 : CMDEnt → PCLer Var */
    public static final Producao R19 = new Producao(
        NaoTerminais.CMDEnt,
        of(Terminais.PCLer, Terminais.Var)
    );

    /** R20 : CMDSaida → PCImprimir Saida */
    public static final Producao R20 = new Producao(
        NaoTerminais.CMDSaida,
        of(Terminais.PCImprimir, NaoTerminais.Saida)
    );

    /** R21a : Saida → Var */
    public static final Producao R21a = new Producao(
        NaoTerminais.Saida,
        of(Terminais.Var)
    );

    /** R21b : Saida → Cadeia */
    public static final Producao R21b = new Producao(
        NaoTerminais.Saida,
        of(Terminais.Cadeia)
    );

    /** R22 : CMDCond → PCSe ER PCEntao CMD CMDCond' */
    public static final Producao R22 = new Producao(
        NaoTerminais.CMDCond,
        of(
            Terminais.PCSe,
            NaoTerminais.ER,
            Terminais.PCEntao,
            NaoTerminais.CMD,
            NaoTerminais.CMDCondlinha
        )
    );

    /**
     * R23a : CMDCond' → PCSenao CMD PCFim
     * (PCFim obrigatório — elimina o dangling else)
     */
    public static final Producao R23a = new Producao(
        NaoTerminais.CMDCondlinha,
        of(Terminais.PCSenao, NaoTerminais.CMD, Terminais.PCFim)
    );

    /**
     * R23b : CMDCond' → PCFim
     * (branch sem else — também fecha com PCFim)
     */
    public static final Producao R23b = new Producao(
        NaoTerminais.CMDCondlinha,
        of(Terminais.PCFim)
    );

    /** R24 : CMDRep → PCEnqto ER CMD */
    public static final Producao R24 = new Producao(
        NaoTerminais.CMDRep,
        of(Terminais.PCEnqto, NaoTerminais.ER, NaoTerminais.CMD)
    );

    /** R25 : SUB → PCIni LC PCFim */
    public static final Producao R25 = new Producao(
        NaoTerminais.SUB,
        of(Terminais.PCIni, NaoTerminais.LC, Terminais.PCFim)
    );


}
