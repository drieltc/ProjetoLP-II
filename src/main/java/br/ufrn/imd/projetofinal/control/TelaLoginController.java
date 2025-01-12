package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.dao.Repo;
import br.ufrn.imd.projetofinal.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para a tela de login.
 */
public class TelaLoginController {
    @FXML private Label resultTest;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private Repo rep;

    private static final Logger LOGGER = Logger.getLogger(TelaLoginController.class.getName());

    /**
     * Construtor da classe. Inicializa o repositório e preenche dados.
     */
    public TelaLoginController() {
        rep = new Repo();
        try {
            rep.preencher();
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Erro ao preencher dados do repositório", e);
        }
    }

    /**
     * Método acionado ao clicar no botão "Entrar".
     */
    @FXML
    protected void onEntrarButtonClick() {
        String username = usernameField.getText().trim();
        String senha = passwordField.getText().trim();

        if (rep.autenticar(username, senha)) {
            resultTest.setText("Login aprovado");

            Usuario usuario = rep.get(username);
            String arquivoFXML = determinarArquivoFXML(usuario);

            if (arquivoFXML != null) {
                abrirNovaTela(arquivoFXML);
            } else {
                resultTest.setText("Tipo de usuário não reconhecido.");
                LOGGER.warning("Tipo de usuário desconhecido para: " + username);
            }
        } else {
            resultTest.setText("Login reprovado");
        }
    }

    /**
     * Determina o arquivo FXML correspondente ao tipo de usuário.
     *
     * @param usuario o usuário autenticado.
     * @return o nome do arquivo FXML, ou {@code null} se o tipo de usuário não for reconhecido.
     */
    private String determinarArquivoFXML(Usuario usuario) {
        if (usuario instanceof Professor) {
            return "TelaProfessor";
        }
        return null;
    }

    /**
     * Abre uma nova tela com base no arquivo FXML fornecido.
     *
     * @param arquivoFXML o nome do arquivo FXML.
     */
    private void abrirNovaTela(String arquivoFXML) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/projetofinal/" + arquivoFXML + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);

            // Mostrar a nova tela
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao abrir a nova tela", e);
            resultTest.setText("Erro ao carregar a nova tela.");
        }
    }

    /**
     * Método acionado ao clicar no botão "Apagar".
     */
    @FXML
    protected void onApagarButtonClick() {
        resultTest.setText("Botão Apagar foi pressionado");
        usernameField.clear();
        passwordField.clear();
    }
}
