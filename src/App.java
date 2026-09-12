import java.util.Scanner;

import domain.Conta;
import domain.Intencoes;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        Conta fkConta = new Conta();

        System.out.println("Digite uma mensagem: ");
        String mensagem = scan.nextLine().trim().toLowerCase();
        do {
            Intencoes intencoes = new Intencoes(mensagem);

            if (intencoes.isSaudacao()) {
                System.out.println("Bot: Olá! Sou o assistente virtual do BanCa$h. Como posso ajudar? ");
                mensagem = scan.nextLine().trim().toLowerCase();
            } else if (intencoes.isSaldo()) {
                System.out.println("Bot: Claro! Mas antes preciso dos 3 primeiros digitos do seu CPF:");
                mensagem = scan.nextLine().trim().toLowerCase();
                if (mensagem.contains("123")) {
                    System.out.println("Bot: Pronto! O seu saldo é de R$" + fkConta.getSaldo());
                    System.out.println("Bot: Deseja fazer mais algumas coisa ?");
                } else {
                    System.out.println("Bot: CPF não encontrado... Tente novamente");
                }
                mensagem = scan.nextLine().trim().toLowerCase();
            } else if (intencoes.isFatura()) {
                System.out.println("Bot: Claro! Mas antes preciso dos 3 primeiros digitos do seu CPF:");
                mensagem = scan.nextLine().trim().toLowerCase();
                if (mensagem.contains("123")) {
                    System.out.println("Bot: Pronto! Aqui está: R$" + fkConta.getCartao().getFatura());
                    System.out.println("Bot: Deseja fazer o que com a fatura? Pagar ou sair?");
                    mensagem = scan.nextLine().trim().toLowerCase();
                    if (mensagem.contains("pagar")) {
                        System.out.println("Bot: A fatura está com o valor de R$" + fkConta.getCartao().getFatura());
                        System.out.println("Bot: Informe o valor que deseja pagar: ");
                        double valor = scan.nextDouble();
                        if (valor <= fkConta.getSaldo()) {
                            fkConta.setSaldo(fkConta.getSaldo() - valor);
                            if (valor < fkConta.getCartao().getFatura()) {
                                fkConta.getCartao().setFatura(fkConta.getCartao().getFatura() - valor);
                                System.out.println(
                                        "Bot: Sua fatura foi paga com sucesso! Novo saldo: R$" + fkConta.getSaldo()
                                                + " e fatura restante: R$" + fkConta.getCartao().getFatura());
                            } else if (valor == fkConta.getCartao().getFatura()) {
                                fkConta.getCartao().setFatura(0);
                                System.out.println(
                                        "Bot: Sua fatura foi paga com sucesso! Novo saldo: R$" + fkConta.getSaldo());
                            } else {
                                System.out.println(
                                        "Bot: O valor inserido é maior que a fatura. Pagando o valor da fatura e devolvendo o restante.");
                                double aux = Math.abs(fkConta.getCartao().getFatura() - valor);
                                fkConta.getCartao().setFatura(0);
                                fkConta.setSaldo(fkConta.getSaldo() + aux);
                            }
                        } else {
                            System.out.println(
                                    "Bot: Saldo insuficiente para realizar o pagamento! Saia ou tente novamente.");
                        }
                    }
                } else {
                    System.out.println("Bot: CPF não encontrado... Tente novamente");
                    mensagem = scan.nextLine().trim().toLowerCase();
                }
            } else if (intencoes.isLimite()) {
                System.out.println("Bot: Claro! Mas antes preciso dos 3 primeiros digitos do seu CPF:");
                mensagem = scan.nextLine().trim().toLowerCase();
                if (mensagem.contains("123")) {
                    System.out.println("Bot: Pronto! O seu limite é de R$" + fkConta.getCartao().getLimite());
                    System.out.println("Bot: Deseja fazer mais algumas coisa ?");
                } else {
                    System.out.println("Bot: CPF não encontrado... Tente novamente ");
                }
                mensagem = scan.nextLine().trim().toLowerCase();
            } else if (intencoes.isPix()) {
                System.out.println("Bot: Claro! Mas antes preciso dos 3 primeiros digitos do seu CPF:");
                mensagem = scan.nextLine().trim().toLowerCase();
                if (mensagem.contains("123")) {
                    System.out.println("Bot: Você deseja receber ou enviar um pix ?");
                    mensagem = scan.nextLine().trim().toLowerCase();
                    if (mensagem.contains("receber")) {
                        System.out.println(
                                "Bot: Para receber um pix, você precisa informar o valor que deseja receber: ");
                        double valor = scan.nextDouble();
                        fkConta.setSaldo(fkConta.getSaldo() + valor);
                        System.out.println("Bot: Pix recebido com sucesso! Novo saldo: R$" + fkConta.getSaldo());
                    } else if (mensagem.contains("enviar")) {
                        System.out
                                .println("Bot: Para enviar um pix, você precisa informar o valor que deseja enviar: ");
                        double valor = scan.nextDouble();
                        if (valor <= fkConta.getSaldo()) {
                            fkConta.setSaldo(fkConta.getSaldo() - valor);
                            System.out.println("Bot: Pix enviado com sucesso! Novo saldo: R$" + fkConta.getSaldo());
                        } else {
                            System.out.println("Bot: Saldo insuficiente para enviar o pix! Saia ou tente novamente.");
                        }
                    }
                    System.out.println("Bot: Deseja fazer mais algumas coisa ?");
                    mensagem = scan.nextLine().trim().toLowerCase();
                } else {
                    System.out.println("Bot: CPF não encontrado... Tente novamente ");
                    mensagem = scan.nextLine().trim().toLowerCase();
                }
            } else if (intencoes.isCartao()) {
                System.out.println("Bot: Claro! Mas antes preciso dos 3 primeiros digitos do seu CPF:");
                mensagem = scan.nextLine().trim().toLowerCase();
                if (mensagem.contains("123")) {
                    System.out.println("Bot: O número do seu cartão é: " + fkConta.getCartao().getNumero());
                    System.out.println("Bot: O limite do seu cartão é: R$" + fkConta.getCartao().getLimite());
                    System.out.println("Bot: A fatura do seu cartão é: R$" + fkConta.getCartao().getFatura());
                    System.out.println("Bot: O seu cartão vence no dia " + fkConta.getCartao().getDiaVencimento());
                    System.out.println("Bot: Deseja fazer mais algumas coisa ?");
                    mensagem = scan.nextLine().trim().toLowerCase();
                    if (mensagem.contains("pagamento")) {
                        System.out.println(
                                "Bot: Para realizar o pagamento com cartão, informe o valor que deseja pagar: ");
                        double valor = scan.nextDouble();
                        System.out.println("Bot: Quer fazer o pagamento no crédito ou débito?");
                        mensagem = scan.nextLine().trim().toLowerCase();
                        if (mensagem.contains("credito")) {
                            fkConta.getCartao().setFatura(fkConta.getCartao().getFatura() + valor);
                            System.out.println("Bot: Pagamento realizado com sucesso! Novo valor da fatura: R$"
                                    + fkConta.getCartao().getFatura());
                        } else if (mensagem.contains("debito")) {
                            fkConta.setSaldo(fkConta.getSaldo() - valor);
                            System.out.println(
                                    "Bot: Pagamento realizado com sucesso! Novo saldo: R$" + fkConta.getSaldo());
                        }
                    }
                    mensagem = scan.nextLine().trim().toLowerCase();
                }
            } else if (intencoes.isEmprestimo()) {
                System.out.println("Bot: Claro! Mas antes preciso dos 3 primeiros digitos do seu CPF:");
                mensagem = scan.nextLine().trim().toLowerCase();
                if (mensagem.equals("123")) {
                    System.out.println("Bot: O seu valor de emprestimo é de R$" + fkConta.getEmprestimo());
                    System.out.println("Bot: Deseja resgata-lo agora?");
                    mensagem = scan.nextLine().trim().toLowerCase();
                    if (mensagem.contains("sim")) {
                        System.out.println("Qual o valor que deseja resgatar? ");
                        double valorResgate = scan.nextDouble();
                        fkConta.setSaldo(fkConta.getSaldo() + valorResgate);
                        System.out
                                .println("Bot: Emprestimo resgatado com sucesso! Novo saldo: R$" + fkConta.getSaldo());
                        fkConta.setEmprestimo(0);
                    } else {
                        System.out.println("Bot: Ok, emprestimo não resgatado.");
                    }
                    System.out.println("Bot: Deseja fazer mais algumas coisa ?");
                } else {
                    System.out.println("Bot: CPF não encontrado... Tente novamente ");
                }
                mensagem = scan.nextLine().trim().toLowerCase();
            } else if (intencoes.isAgencia()) {
                System.out.println(
                        "Bot: Claro! A agência mais próxima de você é a Agência Central, localizada na Rua Principal, nº 123. O horário de funcionamento é de segunda a sexta-feira, das 9h às 17h.");
                System.out.println("Bot: Deseja fazer mais algumas coisa ?");
                mensagem = scan.nextLine().trim().toLowerCase();
            } else if (intencoes.isAtendente()) {
                System.out.println("Bot: Claro! Encaminhando para um atendente humano. Por favor, aguarde...");
                Thread.sleep(2000);
                System.out.println(
                        "Bot: Desculpe, todos os atendentes estão ocupados no momento. Por favor, tente novamente mais tarde.");
                System.out.println("Bot: Deseja fazer mais algumas coisa ?");
                mensagem = scan.nextLine().trim().toLowerCase();
            } else {
                System.out.println("Bot: Desculpe, não entendi. Pode reformular?");
                mensagem = scan.nextLine().trim().toLowerCase();
            }
        } while (!mensagem.contains("tchau"));

    }
}
