package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.model.Aluno;
import br.ufrn.imd.projetofinal.dao.Turma;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Controlador da interface para criar uma nova turma.
 * Gerencia os elementos da interface e a lógica para criação de uma nova turma.
 */
public class TelaCriarTurmaController {
    @FXML private TextField txtNomeTurma;
    @FXML private TextArea txtAlunos;

    private Stage stage;
    private Turma novaTurma;

    /**
     * Define o estágio atual da janela.
     * @param stage o estágio da janela.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Retorna a turma recém-criada.
     * @return a nova turma criada ou {@code null} se o processo for cancelado.
     */
    public Turma getNovaTurma() {
        return novaTurma;
    }

    /**
     * Salva a nova turma com os dados fornecidos nos campos de entrada.
     * Verifica se todos os campos estão preenchidos e cria uma nova turma,
     * incluindo a lista de alunos especificada.
     * Fecha a janela ao finalizar.
     */
    @FXML
    private void salvarTurma() {
        String nome = txtNomeTurma.getText().trim();
        String alunosTexto = txtAlunos.getText().trim();

        // Validação do nome da turma
        if (nome.isEmpty() || !isValidName(nome)) {
            mostrarAlerta("Erro", "Nome inválido", "Por favor, insira um nome de turma válido.");
            return;
        }

        // Validação dos alunos
        if (alunosTexto.isEmpty()) {
            mostrarAlerta("Erro", "Lista de alunos vazia", "Por favor, insira pelo menos um aluno.");
            return;
        }

        ArrayList<Aluno> alunos = new ArrayList<>();
        try {
            // Divide os nomes e cria objetos Aluno
            Arrays.stream(alunosTexto.split(";"))
                    .map(String::trim)
                    .filter(nomeAluno -> !nomeAluno.isEmpty() && isValidName(nomeAluno)) // Ignorar entradas vazias ou inválidas
                    .forEach(nomeAluno -> alunos.add(new Aluno(nomeAluno)));
        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao processar alunos", "Certifique-se de que os nomes dos alunos estão no formato correto.");
            return;
        }

        // Criar a turma
        novaTurma = new Turma();
        novaTurma.setNome(nome);
        novaTurma.setAlunos(alunos);
        novaTurma.setObs("Nenhuma Observação");

        stage.close();
    }

    /**
     * Cancela o processo de criação da turma.
     * Define {@code novaTurma} como {@code null} e fecha a janela.
     */
    @FXML
    private void cancelar() {
        novaTurma = null;
        stage.close();
    }

    /**
     * Exibe uma mensagem de alerta.
     * @param titulo o título da janela de alerta.
     * @param cabecalho o cabeçalho da mensagem de alerta.
     * @param conteudo o conteúdo detalhado da mensagem.
     */
    private void mostrarAlerta(String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    /**
     * Valida o nome, permitindo apenas letras, números, espaços e alguns caracteres especiais.
     * @param nome o nome a ser validado.
     * @return {@code true} se o nome for válido, {@code false} caso contrário.
     */
    private boolean isValidName(String nome) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9À-ÿ\\s.,'-]+$");
        return pattern.matcher(nome).matches();
    }
}
