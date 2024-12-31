package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.dao.Repo;
import br.ufrn.imd.projetofinal.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;

public class TelaLoginController {
    @FXML private Label resultTest;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private Repo rep;

    public TelaLoginController(){
        rep = new Repo();
        try {
            rep.preencher();
        } catch (ParseException e){
            System.out.println("Erro ao preencher");
        }
    }

    @FXML protected void onEntrarButtonClick() {
        String username = usernameField.getText();
        String senha = passwordField.getText();

        if (rep.autenticar(username, senha)){
            resultTest.setText("Login aprovado");

            Usuario p = rep.get(username);
            String arquivo = "";

            if (p.getClass().toString().contains("Professor")){
                arquivo = "TelaProfessor";
            } else if (p.getClass().toString().contains("Aluno")){
                arquivo = "TelaAluno";
            } else if(p.getClass().toString().contains("AdmEscola")){
                arquivo = "TelaAdmEscola";
            } else if(p.getClass().toString().contains("AdmSistema")){
                arquivo = "TelaAdmSistema";
            } else if(p.getClass().toString().contains("AdmRoot")) {
                arquivo = "TelaAdmRoot";
            }

            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/projetofinal/" + arquivo + ".fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);

                // Mostrar a nova tela
                stage.show();
            } catch (Exception e){
                e.printStackTrace();
                resultTest.setText("Erro ao abrir a nova tela.");
            }
        } else{
            resultTest.setText("Login reprovado");
        }
    }

    @FXML protected void onApagarButtonClick(){
        resultTest.setText("Botão Apagar foi pressionado");
        usernameField.setText("");
        passwordField.setText("");
    }
}
