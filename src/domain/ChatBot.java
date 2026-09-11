package domain;

import domain.Conta;
import domain.Intencoes;

public class ChatBot {
    private EstadoConversa.Estado estado = EstadoConversa.Estado.NORMAL;
    private EstadoConversa.AcaoPendente acao;

    Conta fkConta = new Conta();
    EstadoConversa estadoConversa = new EstadoConversa();

    public String responder(String mensagem) {
        if (estado != EstadoConversa.Estado.NORMAL) {
            String resposta = estadoConversa.tratarEstados(estado, acao, mensagem, fkConta);
            estado = EstadoConversa.Estado.NORMAL;

            return resposta;
        }
        Intencoes intencoes = new Intencoes(mensagem);
        if (intencoes.isSaudacao()) {
            return "Bot: Olá! Sou o assistente virtual do BanCa$h. Como posso ajudar? ";
        } else if (intencoes.isSaldo()) {
            estado = EstadoConversa.Estado.AGUARDANDO_CPF;
            acao = EstadoConversa.AcaoPendente.SALDO;
            return "Bot: Para consultar o saldo, por favor informe os 3 primeiros dígitos do seu CPF.";
        } else if (intencoes.isFatura()) {
            estado = EstadoConversa.Estado.AGUARDANDO_CPF;
            acao = EstadoConversa.AcaoPendente.FATURA;
            return "Bot: Para consultar a fatura, por favor informe os 3 primeiros dígitos do seu CPF.";
        }
        return "Bot: Desculpe, não entendi. Pode reformular a pergunta?";
    }
}
