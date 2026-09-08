package domain;

public class Conta {
    private String nome;
    private String cpf;
    private String numeroConta;
    private double saldo;
    private double emprestimo;

    private Cartao cartao;

    public Conta() {
        Cartao cartao = new Cartao();

        this.nome = "Pedro Henrique ";
        this.cpf = "123.456.789-10";
        this.numeroConta = "123456-X";
        this.saldo = 3433.10;
        this.emprestimo = 10000.00;
        this.cartao = cartao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(double emprestimo) {
        this.emprestimo = emprestimo;
    }
}
