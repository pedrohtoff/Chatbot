package domain;

import domain.Conta;

public class EstadoConversa {

    enum AcaoPendente {
        SALDO,
        FATURA
    }

    enum Estado {
        NORMAL,
        AGUARDANDO_CPF,
        AGUARDANDO_VALOR
    }

    public String tratarEstados(Estado estadoAtual, AcaoPendente acao, String mensagem, Conta fkConta) {
        switch (estadoAtual) {
            case NORMAL:
                return "Bot: Desculpe, não entendi. Pode reformular a pergunta?";
            case AGUARDANDO_CPF:
                if (validarCPF(mensagem)) {
                    switch (acao) {
                        case SALDO:
                            return "Bot: Pronto! O seu saldo é de R$" + fkConta.getSaldo();
                        case FATURA:
                            return "Bot: Pronto! Sua fatura é de R$" + fkConta.getCartao().getFatura();
                        default:
                            return "Não entendi o quando você disse. Pode reformular a pergunta?";
                    }
                } else {
                    return "Bot: CPF não encontrado. Por favor, tente novamente.";
                }
            case AGUARDANDO_VALOR:
                return "Bot: Por favor, informe o valor desejado.";
            default:
                return "Bot: Desculpe, ocorreu um erro inesperado.";
        }
    }

    public boolean validarCPF(String cpf) {
        if (cpf.equals("123")) {
            return true;
        } else {
            return false;
        }
    }
}
