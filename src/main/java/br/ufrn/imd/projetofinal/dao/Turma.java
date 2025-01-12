package br.ufrn.imd.projetofinal.dao;

import br.ufrn.imd.projetofinal.model.Aluno;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma turma que contém um nome, uma lista de alunos e uma observação.
 * Esta classe permite gerenciar alunos e formar duplas ou trios de maneira aleatória.
 */
public class Turma {
    private String nome;
    private ArrayList<Aluno> alunos;
    private SimpleStringProperty obs = new SimpleStringProperty();

    /**
     * Construtor padrão que inicializa uma turma com nome vazio, lista de alunos vazia
     * e nenhuma observação.
     */
    public Turma() {
        this.nome = "";
        this.obs.set("");
        this.alunos = new ArrayList<>(); // Inicializa a lista de alunos
    }

    /**
     * Construtor que inicializa uma turma com um nome especificado.
     *
     * @param nome o nome da turma.
     */
    public Turma(String nome) {
        this.nome = nome;
        obs.set("");
        this.alunos = new ArrayList<>(); // Inicializa a lista de alunos
    }

    /**
     * Define o nome da turma.
     *
     * @param nome o novo nome da turma.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o nome da turma.
     *
     * @return o nome da turma.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Adiciona um aluno à lista de alunos da turma.
     *
     * @param a o aluno a ser adicionado.
     */
    public void addAluno(Aluno a) {
        alunos.add(a);
    }

    /**
     * Remove um aluno da lista de alunos da turma.
     *
     * @param a o aluno a ser removido.
     */
    public void removeAluno(Aluno a) {
        alunos.remove(a);
    }

    /**
     * Define a lista de alunos da turma.
     *
     * @param alunos a nova lista de alunos.
     */
    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    /**
     * Retorna a lista de alunos da turma.
     *
     * @return a lista de alunos da turma.
     */
    public ArrayList<Aluno> getAlunos() {
        return this.alunos;
    }

    /**
     * Define a observação associada à turma.
     *
     * @param obs a nova observação.
     */
    public void setObs(String obs) {
        this.obs.set(obs);
    }

    /**
     * Retorna a observação associada à turma.
     *
     * @return a observação da turma.
     */
    public String getObs() {
        return this.obs.get();
    }

    /**
     * Retorna a propriedade de observação para uso em componentes JavaFX.
     *
     * @return a propriedade de observação.
     */
    public SimpleStringProperty obsProperty() {
        return obs;
    }

    /**
     * Forma duplas de alunos de maneira aleatória. Caso a quantidade de alunos seja ímpar,
     * o último grupo formado será um trio.
     *
     * @return uma lista de listas representando as duplas ou trios de alunos.
     */
    public List<List<Aluno>> formarDuplas() {
        List<Aluno> copiaAlunos = new ArrayList<>(alunos);
        Collections.shuffle(copiaAlunos); // Embaralhar a lista de alunos para gerar duplas aleatórias
        List<List<Aluno>> grupos = new ArrayList<>();

        // Formar duplas
        for (int i = 0; i < copiaAlunos.size() - 1; i += 2) {
            List<Aluno> dupla = new ArrayList<>();
            dupla.add(copiaAlunos.get(i));
            dupla.add(copiaAlunos.get(i + 1));
            grupos.add(dupla);
        }

        // Verificar se sobrou um aluno (quantidade ímpar)
        if (copiaAlunos.size() % 2 != 0) {
            // Formar um trio com o último aluno
            List<Aluno> ultimoGrupo = grupos.get(grupos.size() - 1); // Última dupla
            ultimoGrupo.add(copiaAlunos.get(copiaAlunos.size() - 1)); // Adicionar o aluno extra à dupla
        }

        return grupos;
    }
}
