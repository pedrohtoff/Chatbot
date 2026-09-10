package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
        mensagens.getChildren().add(new Text("Voce: Olá!"));
        mensagens.getChildren().add(new Text("Bot: Olá! Como posso ajudá-lo hoje?"));

        // Campo para digitar
        TextField campoMensagem = new TextField();
        campoMensagem.setPromptText("Digite sua mensagem...");

        // Botão de enviar
        Button enviar = new Button("Enviar");

        // Barra inferior
        HBox entrada = new HBox(10);
        entrada.setPadding(new Insets(10));
        entrada.getChildren().addAll(campoMensagem, enviar);

        // Barra superior
        HBox superior = new HBox(10);
        Text titulo = new Text("Banca$h");
        Text status = new Text("Online");
        Circle indicadorStatus = new Circle(5, Color.GREEN);

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        superior.getChildren().addAll(titulo, espaco, indicadorStatus, status);
        superior.setAlignment(Pos.CENTER);
        // Fazendo o textfield ocupar o maximo do espaço
        HBox.setHgrow(campoMensagem, Priority.ALWAYS);

        // Montando a tela
        root.setTop(superior);
        root.setCenter(scroll);
        root.setBottom(entrada);

        // Criando a cena
        Scene scene = new Scene(root, 500, 600);

        stage.setTitle("App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
