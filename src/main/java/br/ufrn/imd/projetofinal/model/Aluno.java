package br.ufrn.imd.projetofinal.model;

import java.util.Date;

public class Aluno extends Usuario {
    private int nota1;
    private int nota2;
    private int nota3;
    public Aluno(String username, String senha, Date dataNascimento, String nome) {
        super(username, senha, dataNascimento, nome);
    }

}
