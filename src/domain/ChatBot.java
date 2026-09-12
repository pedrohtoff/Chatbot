package domain;

import domain.EstadoConversa.AcaoPendente;

public class ChatBot {
    Conta fkConta = new Conta();
    EstadoConversa estadoConversa = new EstadoConversa();

    public String responder(String mensagem) {
        if (estadoConversa.estadoPendente()) {
            return estadoConversa.tratarEstados(mensagem, fkConta);
        }
        Intencoes intencoes = new Intencoes(mensagem);
        if (intencoes.isSaudacao()) {
            return "Bot: Olá! Sou o assistente virtual do BanCa$h. Como posso ajudar? ";
        } else if (intencoes.isSaldo()) {
            estadoConversa.iniciarAcao(AcaoPendente.SALDO);
            return "Bot: Para consultar o saldo, por favor informe os 3 primeiros dígitos do seu CPF.";
        } else if (intencoes.isFatura()) {
            estadoConversa.iniciarAcao(AcaoPendente.FATURA);
            return "Bot: Para consultar a sua fatura, por favor informe os 3 primeiros dígitos do seu CPF.";
        }
        return "Bot: Desculpe, não entendi. Pode reformular a pergunta?";
    }
}
