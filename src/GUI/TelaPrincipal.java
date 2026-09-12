package GUI;

import domain.ChatBot;
import javafx.animation.PauseTransition;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaPrincipal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ChatBot chatbot = new ChatBot();
        PauseTransition pausaFechar = new PauseTransition(Duration.seconds(2));
        PauseTransition pausaMensagens = new PauseTransition(Duration.seconds(1));
        // Layout Principal
        BorderPane root = new BorderPane();
        root.setId("root");
        // Área das mensagens
        VBox mensagens = new VBox();
        mensagens.setPadding(new Insets(10));
        mensagens.setId("area-chat");
        // Scroll para visualizar as mensagens
        ScrollPane scroll = new ScrollPane(mensagens);
        scroll.setFitToWidth(true);
        scroll.setId("scroll-chat");
        mensagens.heightProperty().addListener((obs, alturaAntiga, alturaNova) -> {
            scroll.setVvalue(1.0); // Rola para o final quando uma nova mensagem é adicionada
        });

        // Garantir que a barra de rolagem vertical apareça apenas quando necessário
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Campo para digitar
        TextField campoMensagem = new TextField();
        campoMensagem.setPromptText("Digite sua mensagem...");
        campoMensagem.setId("campo-mensagem");
        // Botão de enviar
        Button enviar = new Button("➜");
        enviar.setId("botao-enviar");
        enviar.setOnAction(e -> {
            String textoSemNormalizacao = campoMensagem.getText();

            String textoComNormalizacao = textoSemNormalizacao.trim().toLowerCase();

            HBox caixaConversaUsuario = new HBox();
            Label mensagemUsuario = new Label(textoSemNormalizacao);
            mensagemUsuario.setWrapText(true);
            mensagemUsuario.setMaxWidth(450);
            mensagemUsuario.getStyleClass().add("mensagem-usuario");

            caixaConversaUsuario.getChildren().add(mensagemUsuario);
            caixaConversaUsuario.setAlignment(Pos.CENTER_RIGHT);

            mensagens.getChildren().addAll(caixaConversaUsuario);
            campoMensagem.clear();

            Label digitando = new Label("Banca$h está digitando...");
            HBox caixaDigitando = new HBox(digitando);
            caixaDigitando.setAlignment(Pos.CENTER_LEFT);

            mensagens.getChildren().add(caixaDigitando);

            pausaMensagens.setOnFinished(event -> {

                mensagens.getChildren().remove(caixaDigitando);

                Label nomeBot = new Label("Banca$h");
                nomeBot.getStyleClass().add("nome-bot");

                String respostaBot = chatbot.responder(textoComNormalizacao);
                if (chatbot.isEncerrado()) {
                    pausaFechar.setOnFinished(events -> stage.close());
                    pausaFechar.play();
                }
                HBox caixaConversaBot = new HBox();
                Label mensagemBot = new Label(respostaBot);
                mensagemBot.setWrapText(true);
                mensagemBot.getStyleClass().add("mensagem-bot");

                VBox conteudoBot = new VBox(3);
                conteudoBot.getChildren().addAll(nomeBot, mensagemBot);

                caixaConversaBot.getChildren().add(conteudoBot);
                caixaConversaBot.setAlignment(Pos.CENTER_LEFT);
                mensagens.getChildren().addAll(caixaConversaBot);
            });
            pausaMensagens.play();
        });

        // Barra inferior
        HBox entrada = new HBox(10);
        entrada.setPadding(new Insets(10));
        entrada.getChildren().addAll(campoMensagem, enviar);
        entrada.setId("area-entrada");

        // Barra superior
        HBox superior = new HBox(10);
        Label titulo = new Label("Banca$h");
        Label status = new Label("Online");
        Circle indicadorStatus = new Circle(5);

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        superior.setId("cabecalho");
        titulo.setId("titulo");
        status.setId("status");
        indicadorStatus.setId("indicadorStatus");

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
        scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());

        stage.setTitle("App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
