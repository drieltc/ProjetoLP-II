package br.ufrn.imd.projetofinal.dao;

import br.ufrn.imd.projetofinal.model.Aluno;
import br.ufrn.imd.projetofinal.model.Professor;

import java.util.ArrayList;

public class Turma {
    private String nome;
    private Escola escola;
    private ArrayList<Aluno> alunos;
    private Professor professor;

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome(){
        return this.nome;
    }

    public void setEscola(Escola escola){
        this.escola = escola;
    }
    public Escola getEscola(){
        return this.escola;
    }

    public void setProfessor(Professor professor){
        this.professor = professor;
    }
    public Professor getProfessor(){
        return this.professor;
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
