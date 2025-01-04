package br.ufrn.imd.projetofinal.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class Aluno {
    private final SimpleStringProperty nome = new SimpleStringProperty();
    private final SimpleFloatProperty[] notas;
    private final SimpleStringProperty obs = new SimpleStringProperty();

    public Aluno(String nome) {
        this.nome.set(nome);
        notas = new SimpleFloatProperty[3];
        for (int i = 0; i < notas.length; i++) {
            notas[i] = new SimpleFloatProperty(-1);
        }
    }

    // Nome
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getNome() {
        return this.nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    // Notas
    public void addNota(Float nota) {
        for (SimpleFloatProperty n : notas) {
            if (n.get() == -1) {
                n.set(nota);
                return;
            }
        }
    }

    public void alterarNota(Float nota, int indice) {
        if (indice >= 0 && indice < notas.length) {
            notas[indice].set(nota);
        } else {
            throw new IndexOutOfBoundsException("Índice de nota inválido: " + indice);
        }
    }

    public float[] getNotas() {
        float[] valores = new float[notas.length];
        for (int i = 0; i < notas.length; i++) {
            valores[i] = notas[i].get();
        }
        return valores;
    }

    public SimpleFloatProperty[] notasProperty() {
        return notas;
    }

    // Observação
    public void setObs(String obs) {
        this.obs.set(obs);
    }

    public String getObs() {
        return this.obs.get();
    }

    public SimpleStringProperty obsProperty() {
        return obs;
    }
}
