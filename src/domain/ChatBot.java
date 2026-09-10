package domain;

import domain.Conta;
import domain.Intencoes;

public class ChatBot {
    Conta fkConta = new Conta();

    public String responder(String mensagem) {
        Intencoes intencoes = new Intencoes(mensagem);
        if (intencoes.isSaudacao()) {
            return "Bot: Olá! Sou o assistente virtual do BanCa$h. Como posso ajudar? ";
        }
        return "Bot: Desculpe, não entendi. Pode reformular a pergunta?";
    }
}
