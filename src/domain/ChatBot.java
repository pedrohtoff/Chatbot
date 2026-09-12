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
            return estadoConversa.iniciarAcao(AcaoPendente.SALDO);
        } else if (intencoes.isFatura()) {
            return estadoConversa.iniciarAcao(AcaoPendente.FATURA);
        } else if (intencoes.isLimite()) {
            return estadoConversa.iniciarAcao(AcaoPendente.LIMITE);
        } else if (intencoes.isPix()) {
            return estadoConversa.iniciarAcao(AcaoPendente.PIX);
        } else if (intencoes.isCartao()) {
            return estadoConversa.iniciarAcao(AcaoPendente.CARTAO);
        } else if (intencoes.isEmprestimo()) {
            return estadoConversa.iniciarAcao(AcaoPendente.EMPRESTIMO);
        } else if (intencoes.isAgencia()) {
            return "Bot: Claro! A agência mais próxima de você é a Agência Central, localizada na Rua Principal, nº 123. O horário de funcionamento é de segunda a sexta-feira, das 9h às 17h, Deseja mais alguma coisa?";
        } else if (intencoes.isAtendente()) {
            // Arrumar para colocar um tempinho como se tivesse procurando
            return "Bot: Claro! Encaminhando para um atendente humano. Por favor, aguarde...";
        }
        return "Bot: Desculpe, não entendi. Pode reformular a pergunta?";
    }
}
