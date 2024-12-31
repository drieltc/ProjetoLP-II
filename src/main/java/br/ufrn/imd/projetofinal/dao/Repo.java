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
        dt = sdf.parse("31-12-2024");

        Aluno a = new Aluno("al", "al", dt, "Aluno da Silva Sauro");

        dt = sdf.parse("31-11-2024");
        Professor p = new Professor("prof", "prof", dt, "Professor da Silva Sauro");

        dt = sdf.parse("30-11-2024");
        AdmRoot r = new AdmRoot("root", "root", dt,  "AdmRoot da Silva Sauro");

        dt = sdf.parse("30-10-2024");
        AdmEscola e = new AdmEscola("es", "es", dt, "AdmEscola da Silva Sauro");

        dt = sdf.parse("29-10-2024");
        AdmSistema s = new AdmSistema("sis", "sis", dt, "AdmSistema da Silva Sauro");

        rep.put(a.getUsername(), a);
        rep.put(p.getUsername(), p);
        rep.put(r.getUsername(), r);
        rep.put(e.getUsername(), e);
        rep.put(s.getUsername(), s);
    }
}
