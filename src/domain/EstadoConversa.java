package domain;

public class EstadoConversa {
    private Estado estado = EstadoConversa.Estado.NORMAL;
    private AcaoPendente acao;
    private OperacaoPIX operacaoPIX;
    private OperacaoCARTAO operacaoCARTAO;

    enum AcaoPendente {
        SALDO,
        FATURA,
        LIMITE,
        PIX,
        CARTAO,
        EMPRESTIMO,
    }

    enum Estado {
        NORMAL,
        AGUARDANDO_CPF,
        AGUARDANDO_ACAO_FATURA,
        AGUARDANDO_ACAO_PIX,
        AGUARDANDO_ACAO_EMPRESTIMO,
        AGUARDANDO_ACAO_CARTAO,
        AGUARDANDO_VALOR
    }

    enum OperacaoPIX {
        ENVIAR,
        RECEBER
    }

    enum OperacaoCARTAO {
        CREDITO,
        DEBITO
    }

    public String tratarEstados(String mensagem, Conta fkConta) {
        switch (estado) {
            case NORMAL:
                estado = Estado.NORMAL;
                return "Desculpe, não entendi. Pode reformular a pergunta?";
            case AGUARDANDO_CPF:
                if (validarCPF(mensagem)) {
                    switch (acao) {
                        case SALDO:
                            estado = Estado.NORMAL;
                            return String.format(
                                    "Pronto! O seu saldo é de R$ %.2f, Deseja fazer mais alguma coisa?",
                                    fkConta.getSaldo());
                        case FATURA:
                            estado = Estado.AGUARDANDO_ACAO_FATURA;
                            return String.format("Pronto! Sua fatura é de R$ %.2f, Deseja pagar ou sair?",
                                    fkConta.getCartao().getFatura());
                        case LIMITE:
                            estado = Estado.NORMAL;
                            return String
                                    .format("Pronto! O seu limite é de R$ %.2f, Deseja fazer mais alguma coisa?",
                                            fkConta.getCartao().getLimite());
                        case PIX:
                            estado = Estado.AGUARDANDO_ACAO_PIX;
                            return "Você deseja receber ou enviar um pix ?";
                        case CARTAO:
                            estado = Estado.AGUARDANDO_ACAO_CARTAO;
                            return "O número do seu cartão é: " + fkConta.getCartao().getNumero() +
                                    "\nO limite do seu cartão é: R$" + fkConta.getCartao().getLimite() +
                                    "\nA fatura do seu cartão é: R$" + fkConta.getCartao().getFatura() +
                                    "\nO seu cartão vence no dia " + fkConta.getCartao().getDiaVencimento() +
                                    "\nDeseja fazer um pagamento com o cartao?";
                        case EMPRESTIMO:
                            estado = Estado.AGUARDANDO_ACAO_EMPRESTIMO;
                            return String
                                    .format("Pronto! O valor do seu empréstimo é de R$ %.2f, Deseja resgata-lo agora?",
                                            fkConta.getEmprestimo());
                        default:
                            estado = Estado.NORMAL;
                            return "Não entendi o quando você disse. Pode reformular a pergunta?";
                    }
                } else {
                    return "CPF não encontrado. Por favor, tente novamente.";
                }
            case AGUARDANDO_VALOR:
                double valor, aux;
                switch (acao) {
                    case FATURA:
                        try {
                            valor = Double.parseDouble(mensagem);
                            if (valor <= 0) {
                                return "Informe um valor maior que zero.";
                            }

                            if (valor <= fkConta.getSaldo()) {
                                fkConta.setSaldo(fkConta.getSaldo() - valor);

                                if (valor < fkConta.getCartao().getFatura()) {
                                    fkConta.getCartao().setFatura(fkConta.getCartao().getFatura() - valor);
                                    estado = Estado.NORMAL;
                                    return "Sua fatura foi paga com sucesso! Novo saldo: R$" + fkConta.getSaldo()
                                            + " e fatura restante: R$" + fkConta.getCartao().getFatura();
                                } else if (valor == fkConta.getCartao().getFatura()) {
                                    fkConta.getCartao().setFatura(0);
                                    estado = Estado.NORMAL;
                                    return "Sua fatura foi paga com sucesso! Novo saldo: R$" + fkConta.getSaldo();
                                } else {
                                    aux = Math.abs(fkConta.getCartao().getFatura() - valor);
                                    fkConta.getCartao().setFatura(0);
                                    fkConta.setSaldo(fkConta.getSaldo() + aux);
                                    estado = Estado.NORMAL;
                                    return "O valor inserido é maior que a fatura. Pagando o valor da fatura e devolvendo o restante.";
                                }
                            } else {
                                estado = Estado.AGUARDANDO_VALOR;
                                return "Saldo insuficiente para realizar o pagamento! Saia ou tente novamente.";
                            }
                        } catch (NumberFormatException e) {
                            estado = Estado.AGUARDANDO_VALOR;
                            return "Por favor, informe um valor válido.";
                        }
                    case PIX:
                        if (operacaoPIX == OperacaoPIX.ENVIAR) {
                            try {
                                valor = Double.parseDouble(mensagem);
                                if (valor <= fkConta.getSaldo()) {
                                    fkConta.setSaldo(fkConta.getSaldo() - valor);
                                    estado = Estado.NORMAL;
                                    return "Pix enviado com sucesso! Novo saldo: R$" + fkConta.getSaldo();
                                } else {
                                    estado = Estado.AGUARDANDO_VALOR;
                                    return "Saldo insuficiente para enviar o pix! Saia ou tente novamente.";
                                }
                            } catch (NumberFormatException e) {
                                estado = Estado.AGUARDANDO_VALOR;
                                return "Por favor, informe um valor válido.";
                            }
                        } else if (operacaoPIX == OperacaoPIX.RECEBER) {
                            try {
                                valor = Double.parseDouble(mensagem);
                                fkConta.setSaldo(fkConta.getSaldo() + valor);
                                estado = Estado.NORMAL;
                                return "Pix recebido com sucesso! Novo saldo: R$" + fkConta.getSaldo();
                            } catch (NumberFormatException e) {
                                estado = Estado.AGUARDANDO_VALOR;
                                return "Por favor, informe um valor válido.";
                            }
                        }

                    case CARTAO:
                        if (operacaoCARTAO == OperacaoCARTAO.CREDITO) {
                            try {
                                valor = Double.parseDouble(mensagem);
                                if (valor <= (fkConta.getCartao().getLimite() - fkConta.getCartao().getFatura())) {
                                    fkConta.getCartao().setFatura(fkConta.getCartao().getFatura() + valor);
                                    estado = Estado.NORMAL;
                                    return "Pagamento realizado com sucesso! Novo valor da fatura: R$"
                                            + fkConta.getCartao().getFatura();
                                } else {
                                    return "Compra negada! Valor excede limite disponível.";
                                }

                            } catch (NumberFormatException e) {
                                estado = Estado.AGUARDANDO_VALOR;
                                return "Por favor, informe um valor válido.";
                            }
                        } else if (operacaoCARTAO == OperacaoCARTAO.DEBITO) {
                            try {
                                valor = Double.parseDouble(mensagem);
                                fkConta.setSaldo(fkConta.getSaldo() - valor);
                                estado = Estado.NORMAL;
                                return "Pagamento realizado com sucesso! Novo saldo: R$" + fkConta.getSaldo();
                            } catch (NumberFormatException e) {
                                estado = Estado.AGUARDANDO_VALOR;
                                return "Por favor, informe um valor válido.";
                            }
                        }
                    case EMPRESTIMO:
                        try {
                            valor = Double.parseDouble(mensagem);
                            fkConta.setSaldo(fkConta.getSaldo() + valor);
                            fkConta.setEmprestimo(0);
                            estado = Estado.NORMAL;
                            return "Emprestimo resgatado com sucesso! Novo saldo: R$"
                                    + fkConta.getSaldo();
                        } catch (NumberFormatException e) {
                            estado = Estado.AGUARDANDO_VALOR;
                            return "Por favor, informe um valor válido.";
                        }
                    default:
                        break;
                }
            case AGUARDANDO_ACAO_FATURA:
                if (mensagem.contains("pagar")) {
                    estado = Estado.AGUARDANDO_VALOR;
                    return "Informe o valor que deseja pagar.";
                }
                estado = Estado.NORMAL;
                return "Tudo bem, operação cancelada.";
            case AGUARDANDO_ACAO_EMPRESTIMO:
                if (mensagem.contains("sim")) {
                    estado = Estado.AGUARDANDO_VALOR;
                    return "Qual o valor que deseja resgatar? ";
                }
                estado = Estado.NORMAL;
                return "Tudo bem, operação cancelada.";
            case AGUARDANDO_ACAO_PIX:
                if (mensagem.contains("receber")) {
                    estado = Estado.AGUARDANDO_VALOR;
                    operacaoPIX = OperacaoPIX.RECEBER;
                    return "Informe o valor que vais receber.";
                } else if (mensagem.contains("enviar")) {
                    estado = Estado.AGUARDANDO_VALOR;
                    operacaoPIX = OperacaoPIX.ENVIAR;
                    return "Informe o valor que vais enviar.";
                }
                estado = Estado.NORMAL;
                return "Tudo bem, operação cancelada.";
            case AGUARDANDO_ACAO_CARTAO:
                if (mensagem.equals("sim")) {
                    estado = Estado.AGUARDANDO_ACAO_CARTAO;
                    return "Deseja pagar no crédito ou no débito?";
                }
                if (mensagem.contains("credito") || mensagem.contains("crédito")) {
                    estado = Estado.AGUARDANDO_VALOR;
                    operacaoCARTAO = OperacaoCARTAO.CREDITO;
                    return "Informe o valor que vais pagar.";
                } else if (mensagem.contains("debito") || mensagem.contains("débito")) {
                    estado = Estado.AGUARDANDO_VALOR;
                    operacaoCARTAO = OperacaoCARTAO.DEBITO;
                    return "Informe o valor que vais pagar.";
                }
                estado = Estado.NORMAL;
                return "Tudo bem, operação cancelada.";
            default:
                estado = Estado.NORMAL;
                return "Desculpe, ocorreu um erro inesperado.";
        }
    }

    public boolean estadoPendente() {
        if (estado != Estado.NORMAL) {
            return true;
        }
        return false;
    }

    public String iniciarAcao(AcaoPendente acaoAtual) {
        this.acao = acaoAtual;
        this.estado = Estado.AGUARDANDO_CPF;

        return "Para continuar, por favor informe os 3 primeiros dígitos do seu CPF.";
    }

    public boolean validarCPF(String cpf) {
        if (cpf.equals("123")) {
            return true;
        } else {
            return false;
        }
    }
}
