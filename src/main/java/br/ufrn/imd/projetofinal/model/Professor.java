package br.ufrn.imd.projetofinal.model;

import java.util.Date;

public class Professor extends Usuario {
    public Professor(String username, String senha, Date dataNascimento, String nome){
        super(username, senha, dataNascimento, nome);
    }
}
