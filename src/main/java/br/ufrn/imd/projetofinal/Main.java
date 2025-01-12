package br.ufrn.imd.projetofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal da aplicação que inicializa e exibe a interface gráfica.
 * Extende a classe {@link Application} do JavaFX.
 */
public class Main extends Application {

    /**
     * Método principal que inicia a aplicação JavaFX.
     * Carrega o arquivo FXML correspondente à tela de login e exibe a janela.
     *
     * @param stage o palco principal da aplicação, fornecido pelo JavaFX.
     * @throws IOException caso ocorra um erro ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/projetofinal/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Tela de Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal que serve como ponto de entrada para a aplicação.
     * Chamado pelo sistema para inicializar o JavaFX.
     *
     * @param args argumentos da linha de comando (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        launch();
    }
}
