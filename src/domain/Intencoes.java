package domain;

public class Intencoes {
    private boolean saudacao;
    private boolean saldo;
    private boolean fatura;
    private boolean limite;
    private boolean pix;
    private boolean emprestimo;
    private boolean agencia;
    private boolean atendente;
    private boolean pagamento;
    private boolean cartao;
    private boolean encerrar;

    public Intencoes(String mensagem) {
        this.saudacao = mensagem.contains("oi") || mensagem.contains("olá");
        this.saldo = mensagem.contains("saldo") || mensagem.contains("conta");
        this.fatura = mensagem.contains("fatura");
        this.limite = mensagem.contains("limite");
        this.pix = mensagem.contains("pix");
        this.emprestimo = mensagem.contains("emprestimo");
        this.agencia = mensagem.contains("agencia");
        this.atendente = mensagem.contains("atendente") || mensagem.contains("pessoa");
        this.pagamento = mensagem.contains("pagar") || mensagem.contains("pagamento");
        this.cartao = mensagem.contains("cartão") || mensagem.contains("cartao");
        this.encerrar = mensagem.contains("tchau") || mensagem.contains("sair");
    }

    public boolean isEncerrar() {
        return encerrar;
    }

    public boolean isCartao() {
        return cartao;
    }

    public boolean isSaudacao() {
        return saudacao;
    }

    public boolean isSaldo() {
        return saldo;
    }

    /*
     * public boolean isExtrato() {
     * return extrato;
     * }
     */
    public boolean isFatura() {
        return fatura;
    }

    public boolean isLimite() {
        return limite;
    }

    public boolean isPix() {
        return pix;
    }

    public boolean isEmprestimo() {
        return emprestimo;
    }

    public boolean isAgencia() {
        return agencia;
    }

    public boolean isAtendente() {
        return atendente;
    }

    public boolean isPagamento() {
        return pagamento;
    }

}
