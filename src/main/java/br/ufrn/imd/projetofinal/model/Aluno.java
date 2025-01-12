package br.ufrn.imd.projetofinal.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Representa um aluno com nome, notas e observações.
 * A classe utiliza propriedades do JavaFX para permitir a vinculação de dados em interfaces gráficas.
 */
public class Aluno {
    private final SimpleStringProperty nome = new SimpleStringProperty();
    private final SimpleFloatProperty[] notas;
    private final SimpleStringProperty obs = new SimpleStringProperty();

    /**
     * Construtor que inicializa o aluno com um nome especificado.
     * As notas são inicialmente definidas como 0, e a observação padrão é "Nenhuma Observação".
     *
     * @param nome o nome do aluno.
     */
    public Aluno(String nome) {
        this.nome.set(nome);
        notas = new SimpleFloatProperty[3];
        for (int i = 0; i < notas.length; i++) {
            notas[i] = new SimpleFloatProperty(0);
        }
        this.setObs("Nenhuma Observação");
    }

    // Nome

    /**
     * Define o nome do aluno.
     *
     * @param nome o novo nome do aluno.
     */
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * Retorna o nome do aluno.
     *
     * @return o nome do aluno.
     */
    public String getNome() {
        return this.nome.get();
    }

    /**
     * Retorna a propriedade do nome para uso em componentes JavaFX.
     *
     * @return a propriedade do nome.
     */
    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    // Notas

    /**
     * Adiciona uma nova nota à primeira posição disponível no array de notas.
     * Caso todas as posições estejam ocupadas, nenhuma alteração será feita.
     *
     * @param nota a nota a ser adicionada.
     */
    public void addNota(Float nota) {
        for (SimpleFloatProperty n : notas) {
            if (n.get() == -1) {
                n.set(nota);
                return;
            }
        }
    }

    /**
     * Define o valor de uma nota em um índice específico.
     *
     * @param nota   o valor da nota a ser definido.
     * @param indice o índice da nota no array.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    public void setNota(Float nota, int indice) {
        if (indice >= 0 && indice < notas.length) {
            notas[indice].set(nota);
        } else {
            throw new IndexOutOfBoundsException("Índice de nota inválido: " + indice);
        }
    }

    /**
     * Retorna um array contendo os valores das notas do aluno.
     * Caso uma nota tenha o valor -1, será retornado 0 no lugar.
     *
     * @return um array de float contendo as notas do aluno.
     */
    public float[] getNotas() {
        float[] valores = new float[notas.length];
        for (int i = 0; i < notas.length; i++) {
            if (notas[i].get() == -1) {
                valores[i] = 0;
            } else {
                valores[i] = notas[i].get();
            }
        }
        return valores;
    }

    /**
     * Retorna o array de propriedades das notas para uso em componentes JavaFX.
     *
     * @return o array de propriedades das notas.
     */
    public SimpleFloatProperty[] notasProperty() {
        return notas;
    }

    // Observação

    /**
     * Define a observação associada ao aluno.
     *
     * @param obs a nova observação.
     */
    public void setObs(String obs) {
        this.obs.set(obs);
    }

    /**
     * Retorna a observação associada ao aluno.
     *
     * @return a observação do aluno.
     */
    public String getObs() {
        return this.obs.get();
    }

    /**
     * Retorna a propriedade da observação para uso em componentes JavaFX.
     *
     * @return a propriedade da observação.
     */
    public SimpleStringProperty obsProperty() {
        return obs;
    }
}
