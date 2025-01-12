package br.ufrn.imd.projetofinal.dao;

import br.ufrn.imd.projetofinal.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Classe Repo - Repositório de usuários.
 *
 * Gerencia usuários do sistema, fornecendo métodos para adicionar, buscar, autenticar e preencher com dados iniciais.
 */
public class Repo {
    private HashMap<String, Usuario> rep;

    /**
     * Construtor padrão. Inicializa o repositório.
     */
    public Repo() {
        rep = new HashMap<>();
    }

    /**
     * Adiciona um usuário ao repositório.
     *
     * @param username O nome de usuário (chave).
     * @param usuario O objeto Usuario a ser adicionado.
     */
    public void add(String username, Usuario usuario) {
        rep.put(username, usuario);
    }

    /**
     * Recupera um usuário do repositório pelo username.
     *
     * @param username O nome de usuário a ser buscado.
     * @return O objeto Usuario associado ao username, ou null se não encontrado.
     */
    public Usuario get(String username) {
        return rep.get(username);
    }

    /**
     * Autentica um usuário com base no username e senha.
     *
     * @param username O nome de usuário.
     * @param senha A senha do usuário.
     * @return true se o par username/senha for válido, false caso contrário.
     */
    public boolean autenticar(String username, String senha) {
        Usuario usuario = rep.get(username);
        return usuario != null && senha != null && senha.equals(usuario.getSenha());
    }

    /**
     * Preenche o repositório com dados iniciais.
     *
     * @throws ParseException Se houver erro ao processar a data.
     */
    public void preencher() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = sdf.parse("30-11-2024"); // Data corrigida para um valor válido.
        Professor professor = new Professor("prof", "prof", dt, "Professor da Silva Sauro");
        rep.put(professor.getUsername(), professor);
    }
}
