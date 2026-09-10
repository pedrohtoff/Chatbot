package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

        mensagens.heightProperty().addListener((obs, alturaAntiga, alturaNova) -> {
            scroll.setVvalue(1.0); // Rola para o final quando uma nova mensagem é adicionada
        });

        // Garantir que a barra de rolagem vertical apareça apenas quando necessário
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        HBox caixaConversaBot = new HBox();
        Label mensagemBot = new Label("Bot: Olá! Como posso ajudá-lo hoje?");
        mensagemBot.setWrapText(true);
        mensagemBot.setStyle(
                "-fx-background-color: #E8E8E8;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10;");

        caixaConversaBot.getChildren().add(mensagemBot);
        caixaConversaBot.setAlignment(Pos.CENTER_LEFT);

        mensagens.getChildren().addAll(caixaConversaBot);
        // Campo para digitar
        TextField campoMensagem = new TextField();
        campoMensagem.setPromptText("Digite sua mensagem...");

        // Botão de enviar
        Button enviar = new Button("Enviar");
        enviar.setOnAction(e -> {
            String texto = campoMensagem.getText();

            HBox caixaConversaUsuario = new HBox();

            Label mensagemUsuario = new Label(texto);

            mensagemUsuario.setWrapText(true);
            mensagemUsuario.setStyle(
                    "-fx-background-color: #DCF2FF;" +
                            "-fx-background-radius: 15;" +
                            "-fx-padding: 10;");

            caixaConversaUsuario.getChildren().add(mensagemUsuario);
            caixaConversaUsuario.setAlignment(Pos.CENTER_RIGHT);

            mensagens.getChildren().addAll(caixaConversaUsuario);

            campoMensagem.clear();
        });

        // Barra inferior
        HBox entrada = new HBox(10);
        entrada.setPadding(new Insets(10));
        entrada.getChildren().addAll(campoMensagem, enviar);

        // Barra superior
        HBox superior = new HBox(10);
        Label titulo = new Label("Banca$h");
        Label status = new Label("Online");
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
