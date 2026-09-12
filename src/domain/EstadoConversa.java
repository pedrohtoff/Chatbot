package domain;

public class EstadoConversa {
    private Estado estado = EstadoConversa.Estado.NORMAL;
    private AcaoPendente acao;

    enum AcaoPendente {
        SALDO,
        FATURA
    }

    enum Estado {
        NORMAL,
        AGUARDANDO_CPF,
        AGUARDANDO_ACAO_FATURA,
        AGUARDANDO_VALOR
    }

    public String tratarEstados(String mensagem, Conta fkConta) {
        switch (estado) {
            case NORMAL:
                estado = Estado.NORMAL;
                return "Bot: Desculpe, não entendi. Pode reformular a pergunta?";
            case AGUARDANDO_CPF:
                if (validarCPF(mensagem)) {
                    switch (acao) {
                        case SALDO:
                            estado = Estado.NORMAL;
                            return String.format(
                                    "Bot: Pronto! O seu saldo é de R$ %.2f, Deseja fazer mais algumas coisa?",
                                    fkConta.getSaldo());
                        case FATURA:
                            estado = Estado.AGUARDANDO_ACAO_FATURA;
                            return String.format("Bot: Pronto! Sua fatura é de R$ %.2f, Deseja pagar ou sair?",
                                    fkConta.getCartao().getFatura());
                        default:
                            estado = Estado.NORMAL;
                            return "Não entendi o quando você disse. Pode reformular a pergunta?";
                    }
                } else {
                    return "Bot: CPF não encontrado. Por favor, tente novamente.";
                }
            case AGUARDANDO_VALOR:
                return "Bot: Por favor, informe o valor desejado.";
            case AGUARDANDO_ACAO_FATURA:
                if (mensagem.contains("pagar")) {
                    estado = Estado.AGUARDANDO_VALOR;
                    return "Informe o valor que deseja pagar: ";
                }
                estado = Estado.NORMAL;
                return "Bot: Tudo bem, operação cancelada.";
            default:
                estado = Estado.NORMAL;
                return "Bot: Desculpe, ocorreu um erro inesperado.";
        }
    }

    public boolean estadoPendente() {
        if (estado != Estado.NORMAL) {
            return true;
        }
        return false;
    }

    public void iniciarAcao(AcaoPendente acaoAtual) {
        this.acao = acaoAtual;
        this.estado = Estado.AGUARDANDO_CPF;
    }

    public boolean validarCPF(String cpf) {
        if (cpf.equals("123")) {
            return true;
        } else {
            return false;
        }
    }
}
