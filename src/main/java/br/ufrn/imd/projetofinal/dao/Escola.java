package br.ufrn.imd.projetofinal.dao;

import br.ufrn.imd.projetofinal.model.AdmEscola;

import java.util.ArrayList;

public class Escola {
    private String nome;
    private ArrayList<Turma> turmas;
    private AdmEscola adm;

    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return this.nome;
    }

    public void addTurma(Turma turma){
        this.turmas.add(turma);
    }
    public void removeTurma(Turma turma){
        this.turmas.remove(turma);
    }

    public void setAdm(AdmEscola adm){
        this.adm = adm;
    }
    public AdmEscola getAdm(){
        return this.adm;
    }
}
