package br.ufrn.imd.projetofinal.model;

import java.util.Date;

public class AdmSistema extends Usuario {
    public AdmSistema(String username, String senha, Date dataNascimento, String nome) {
        super(username, senha, dataNascimento, nome);
    }
}
