package br.ufrn.imd.projetofinal.model;

import java.util.Date;

public abstract class Usuario {
    protected String username;
    protected String senha;
    protected Date dataNascimento;
    protected String nome;

    public Usuario(String username, String senha, Date dataNascimento, String nome){
        this.username= username;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataNascimento(Date dataNascimento){
        this.dataNascimento = dataNascimento;
    }

    public Date getDataNascimento(){
        return this.dataNascimento;
    }
}
