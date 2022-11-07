import Modelagem.*;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;
import Modelagem.Pessoa;
import Modelagem.Conta;
import java.security.Timestamp;

public class Aplicacao {

    public static void main(String[] args) {

        String tipoConta = null;
        int operacao;
        String encerrar;


        Scanner a = new Scanner(System.in);

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("        BEM VINDO AO BANCO ITAU-LETSCODE      ");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Bom dia vamos abrir sua conta: ");
        System.out.printf("Informe seu nome:  ");
        String nome = a.next().toUpperCase();
        System.out.printf("Informe seu sexo ('F' ou 'M'):  ");
        String sexo = a.next().toUpperCase();
        System.out.printf("Informe seu endereço: ");
        String endereco = a.next().toUpperCase();
        System.out.printf("Informe se a conta será de PF ou PJ: ");
        String tipoPessoa = a.next().toUpperCase();

        Pessoa pessoa = null;

        switch (tipoPessoa){
             case "PF":
                System.out.printf("Informe seu CPF: ");
                String cpf = a.next();
                pessoa = new PessoaFisica(nome, sexo, endereco, cpf);
                System.out.println("Informe o tipo de conta que quer abrir: ");
                System.out.printf("CC - Conta Corrente, CI - Conta Investimento, CP - Conta Popupança \n Tipo: ");
                tipoConta = a.next().toUpperCase();
                break;
            case "PJ":
                System.out.printf("Informe seu CNPJ:");
                String cnpj = a.next();
                pessoa = new PessoaJuridica(nome, endereco, cnpj);
                System.out.println("Informe o tipo de conta que quer abrir: ");
                System.out.println("CC - Conta Corrente, CI - Conta Investimento \n Tipo: ");
                tipoConta = a.next().toUpperCase();
                break;
        default:
                System.out.println("OPÇÃO INVALIDA");
                System.exit(0);
         }

        System.out.printf("Informe o saldo que irá iniciar sua conta: ");
        BigDecimal saldo = a.nextBigDecimal();

        Conta conta = null;
        long numconta = System.currentTimeMillis();




        if (tipoPessoa.equals("PF") && tipoConta.equals("CI") ) {
            saldo = saldo.multiply(BigDecimal.valueOf(1.015));
        }
        else if ((tipoPessoa.equals("PJ") && tipoConta.equals("CI"))){
             saldo = saldo.multiply(BigDecimal.valueOf(1.035));
        }else if ((tipoPessoa.equals("PF") && tipoConta.equals("CP"))){
             saldo = saldo.multiply(BigDecimal.valueOf(1.000));
        }

        switch (tipoConta) {
            case "CC":
                conta = new ContaCorrente(numconta, saldo, pessoa);
                break;
            case "CI":
                conta = new ContaInvestimento(numconta, saldo, pessoa);
                break;
            case "CP":
                conta = new ContaPoupanca(numconta, saldo, pessoa);
                break;
            default:
                System.out.println("OPÇÃO INVALIDA");
                System.exit(0);
        }

        do {
            System.out.println("\nOperações:");
            System.out.println("1-SACAR");
            System.out.println("2-DEPOSITAR");
            System.out.println("3-TRANSFERIR");
            System.out.println("4-INVESTIR");
            System.out.println("5-CONSULTAR SALDO");
            System.out.println("6-CONSULTAR DADOS DA CONTA");
            System.out.println("7-ENCERRAR");
            System.out.printf("Informe a operacao que deseja realizar: ");
            operacao = a.nextInt();

            switch (operacao) {
                case 1:
                    System.out.printf("Informe o valor do saque: ");
                    BigDecimal saque = a.nextBigDecimal();
                    if (tipoPessoa.equals("PJ")) {
                        BigDecimal txsaque = saque.multiply(BigDecimal.valueOf(1.050));
                        saque.subtract(txsaque);
                    }
                    conta.sacar(saque);
                    break;
                case 2:
                    System.out.printf("Informe o valor do deposito: ");
                    BigDecimal deposito = a.nextBigDecimal();
                    if (tipoPessoa.equals("PF") && tipoConta.equals("CI") ) {
                        deposito = deposito.multiply(BigDecimal.valueOf(1.015));
                    }
                    else if ((tipoPessoa.equals("PJ") && tipoConta.equals("CI"))){
                        deposito = deposito.multiply(BigDecimal.valueOf(1.035));
                    }else if ((tipoPessoa.equals("PF") && tipoConta.equals("CP"))){
                        deposito = deposito.multiply(BigDecimal.valueOf(1.000));
                    }

                    conta.depositar(deposito);

                    break;
                case 3:
                    System.out.printf("Informe o valor da transferencia: ");
                    BigDecimal transferencia = a.nextBigDecimal();
                    conta.transferir(transferencia);
                    break;
                case 4:
                    System.out.printf("Informe o valor do investimento: ");
                    BigDecimal investimento = a.nextBigDecimal();
                    conta.investir(investimento);
                    break;
                case 5:
                    conta.consultarSaldo();
                    break;
                case 6:
                    System.out.println("Dados da Conta: " + conta.toString());
                    break;
                default:
                    System.out.println("OPÇÃO INVALIDA");
                    System.exit(0);
            }
            System.out.print("\nDigite S se deseja continuar  ");
            encerrar = a.next().toUpperCase();
        }while(encerrar.equals("S"));


    }
}
