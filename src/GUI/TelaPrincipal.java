package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Layout Principal
        BorderPane root = new BorderPane();

        // Área das mensagens
        VBox mensagens = new VBox();
        mensagens.setPadding(new Insets(10));

        // Scroll para visualizar as mensagens
        ScrollPane scroll = new ScrollPane(mensagens);
        scroll.setFitToWidth(true);

        // Campo para digitar
        TextField campoMensagem = new TextField();
        campoMensagem.setPromptText("Digite sua mensagem...");

        // Botão de enviar
        Button enviar = new Button("Enviar");

        // Barra inferior
        HBox entrada = new HBox(10);
        entrada.setPadding(new Insets(10));
        entrada.getChildren().addAll(campoMensagem, enviar);

        // Fazendo o textfield ocupar o maximo do espaço
        HBox.setHgrow(campoMensagem, javafx.scene.layout.Priority.ALWAYS);

        // Montando a tela
        root.setCenter(scroll);
        root.setBottom(entrada);

        // Criando a cena
        Scene scene = new Scene(root, 500, 600);

        stage.setTitle("Banca$h");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
