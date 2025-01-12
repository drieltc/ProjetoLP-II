package br.ufrn.imd.projetofinal.model;

import java.util.Date;

/**
 * Classe abstrata que representa um usuário do sistema.
 * Um usuário possui um nome de usuário, senha, data de nascimento e nome completo.
 */
public abstract class Usuario {
    protected String username;
    protected String senha;
    protected Date dataNascimento;
    protected String nome;

    /**
     * Construtor que inicializa os atributos principais do usuário.
     *
     * @param username      o nome de usuário.
     * @param senha         a senha do usuário.
     * @param dataNascimento a data de nascimento do usuário.
     * @param nome          o nome completo do usuário.
     */
    public Usuario(String username, String senha, Date dataNascimento, String nome) {
        this.username = username;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
    }

    /**
     * Obtém o nome de usuário.
     *
     * @return o nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define o nome de usuário.
     *
     * @param username o novo nome de usuário.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return a senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     *
     * @param senha a nova senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Define a data de nascimento do usuário.
     *
     * @param dataNascimento a nova data de nascimento.
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Obtém a data de nascimento do usuário.
     *
     * @return a data de nascimento.
     */
    public Date getDataNascimento() {
        return this.dataNascimento;
    }
}
