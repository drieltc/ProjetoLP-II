package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.model.Aluno;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controlador para a tela de exibição de duplas.
 */
public class TelaDuplasController {

    @FXML
    private TextArea areaDuplas; // Substituí ListView por TextArea

    private Stage stage; // Referência à janela atual

    /**
     * Define o palco (janela) associado a este controlador.
     *
     * @param stage o palco da janela.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carrega as duplas no TextArea.
     *
     * @param duplas lista de listas de alunos representando as duplas ou grupos.
     */
    public void carregarDuplas(List<List<Aluno>> duplas) {
        areaDuplas.clear(); // Limpa o conteúdo antes de adicionar novas duplas

        int grupoNumero = 1;
        StringBuilder texto = new StringBuilder();
        for (List<Aluno> dupla : duplas) {
            texto.append("Grupo ").append(grupoNumero).append(": ");
            for (Aluno aluno : dupla) {
                texto.append(aluno.getNome()).append(" | ");
            }
            texto.append("\n");
            grupoNumero++;
        }
        areaDuplas.setText(texto.toString().trim());
    }

    /**
     * Fecha a janela associada ao controlador.
     */
    @FXML
    private void fecharJanela() {
        if (stage != null) {
            stage.close();
        }
    }
}
