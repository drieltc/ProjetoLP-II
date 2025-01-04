package br.ufrn.imd.projetofinal.dao;

import br.ufrn.imd.projetofinal.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Repo {
    private HashMap<String, Usuario> rep;

    public Repo(){
        rep = new HashMap<String, Usuario>();
    }

    public void add(String c, Usuario p){
        rep.put(c, p);
    }

    public Usuario get(String c){
        return rep.get(c);
    }

    public boolean autenticar(String username, String senha){
        Usuario p = rep.get(username);
        return p != null && p.getSenha().equals(senha);
    }

    public void preencher() throws ParseException {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dt = sdf.parse("31-11-2024");
        Professor p = new Professor("prof", "prof", dt, "Professor da Silva Sauro");
        rep.put(p.getUsername(), p);
    }
}
