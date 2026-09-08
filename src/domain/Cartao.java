package domain;

public class Cartao {
    private String numero;
    private double limite;
    private double fatura;
    private int diaVencimento;

    public Cartao() {
        this.numero = "1234 5678 9123 4567";
        this.limite = 1000.0;
        this.fatura = 451.3;
        this.diaVencimento = 10;
    }

    public String getNumero() {
        return numero;
    }

    public double getLimite() {
        return limite;
    }

    public double getFatura() {
        return fatura;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public void setFatura(double fatura) {
        if (fatura >= 0 && fatura <= limite) {
            this.fatura = fatura;
        } else {
            System.out.println("Limite excedido! A fatura não pode ser maior que o limite do cartão.");
        }
        this.fatura = fatura;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

}
