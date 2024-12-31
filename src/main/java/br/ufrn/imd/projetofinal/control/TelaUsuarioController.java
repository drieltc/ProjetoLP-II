package br.ufrn.imd.projetofinal.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class TelaUsuarioController {
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
}
