package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.dao.Turma;
import br.ufrn.imd.projetofinal.model.Aluno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.io.IOException;

public class TelaProfessorController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTurmas();

        // Configura as colunas
        NomeCol.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        n1Col.setCellValueFactory(cellData -> cellData.getValue().notasProperty()[0].asObject());
        n2Col.setCellValueFactory(cellData -> cellData.getValue().notasProperty()[1].asObject());
        n3Col.setCellValueFactory(cellData -> cellData.getValue().notasProperty()[2].asObject());
        obsCol.setCellValueFactory(cellData -> cellData.getValue().obsProperty());

    }


    @FXML
    private ListView<String> listaTurmas;

    @FXML
    private TableView<Aluno> tabela;

    @FXML private TableColumn<Aluno, String> NomeCol;
    @FXML private TableColumn<Aluno, Float> n1Col;
    @FXML private TableColumn<Aluno, Float> n2Col;
    @FXML private TableColumn<Aluno, Float> n3Col;
    @FXML private TableColumn<Aluno, String> obsCol;

    @FXML private Button adicionarAlunoButton;
    @FXML private Button removerAlunoButton;
    private ArrayList<Turma> turmas;

    @FXML
    public void carregarTurmas() {
        turmas = Gerenciador.carregarTurmas();

        listaTurmas.getItems().clear(); // Limpa a lista antes de carregar novas turmas

        for (Turma turma : turmas) {
            listaTurmas.getItems().add(turma.getNome());
        }
    }

    @FXML
    public void mostrarDetalhesTurma(MouseEvent event) {
        int indiceSelecionado = listaTurmas.getSelectionModel().getSelectedIndex();

        if (indiceSelecionado >= 0) {
            Turma turmaSelecionada = turmas.get(indiceSelecionado);

            tabela.getItems().clear(); // Limpa os dados anteriores na tabela

            tabela.getItems().addAll(turmaSelecionada.getAlunos()); // Adiciona alunos na tabela
        }
    }

    @FXML protected void LogOut(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/projetofinal/TelaLogin.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Login");
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML public void addAluno(){
        System.out.println("Adicionar aluno à turma");
    }

    @FXML public void removeAluno(){
        System.out.println("Removendo aluno");
        //int alunoSelecionado = NomeCol
    }
}

//funcionalidades
//visualizar as paradas
//adicionar aluno em uma turma
//remover aluno de uma turma
//alterar notas
//criar novas turmas
//zerar notas dos alunos de uma turma
//exportar
//observacao das turmas
