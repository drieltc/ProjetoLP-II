package br.ufrn.imd.projetofinal.dao;

import br.ufrn.imd.projetofinal.model.Aluno;

import java.util.ArrayList;

public class Turma {
    private String nome;
    private ArrayList<Aluno> alunos;

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome(){
        return this.nome;
    }

    public void addAluno(Aluno a){
        alunos.add(a);
    }
    public void removeAluno(Aluno a){
        alunos.remove(a);
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }
    public ArrayList<Aluno> getAlunos(){
        return this.alunos;
    }
}
