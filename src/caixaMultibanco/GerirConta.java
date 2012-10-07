package caixaMultibanco;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @authors: Miguel Oliveira; Nuno Diogo da Silva; Paulo Silva;
 */

public class GerirConta implements Serializable{ //permite manter a integridade dos atributos quando guardados em ficheiros de objectos;

    //variáveis de classe
    private static Scanner input = new Scanner (System.in);

    //atributos de instância
    private Conta c;
    private String cliente;
    private String numConta;

    //Construtor
    public GerirConta (String cliente, String numConta){
        this.cliente = cliente;
        this.numConta = numConta;

        //carregamento de atributos do cliente, caso já exista (já tenha sido carregado)
        try {
            ObjectInputStream in;
                in = new ObjectInputStream(new FileInputStream(this.cliente));
                float s = in.readFloat();
                float p = in.readFloat();
                Vector<String> m = (Vector<String>)in.readObject();
                c = new Conta(s, p, m);
                menu(c);
                in.close();
        }
        catch (ClassNotFoundException e){
            System.out.println ("Classe não encontrada!");
            
        }

        //Se o ficheiro não for encontrado, ao entrar na excepcção o ficheiro é criado.
        catch (FileNotFoundException e){
                System.out.println ("\nObrigado por usufruir do MB pela 1ª vez!");//mensagem enviada após a criação de um ficheiro de dados para um novo cliente
                try {
                    ObjectOutputStream out;
                    out = new ObjectOutputStream(new FileOutputStream(this.cliente));
                    c = new Conta();
                    menu(c);
                    out.writeFloat(c.saldoContabilistico());//escreve no ficheiro o saldo aleatório atribuído
                    out.writeFloat(c.getPlafond());//escreve no ficheiro o plafond aleatório atribuído
                    out.writeObject(c.getMov());
                    out.close();
                }
                catch (FileNotFoundException ex){
                    System.out.println ("Ficheiro não encontrado / não existe");
                }
                catch (IOException ex) {
                    System.out.println("Erro de escrita!");
                }
        }
        catch (IOException ex) {
            System.out.println("Erro de leitura!");
        }
     }

    //Selectores
    public Conta getC() {
        return c;
    }

    public String getCliente() {
        return cliente;
    }

    public String getNumConta() {
        return numConta;
    }

    //modificadores
    public void mainExec(){     //menu apresentado após as operações em que não desejamos que o multibanco volte directamente à introdução do código
        Multibanco.guardaDados(c, cliente);
        System.out.println(
                  "\n1) Retirar Cartão\n"
                + "0) Outras Operações");
        int op = input.nextInt();
        switch(op){
            case 1:{
                System.out.println ("\nObrigado pela sua preferência. Volte sempre!\n");
                System.exit(1);
            }
            case 0:{
                Multibanco.exec();
            }
            default:{
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\n\n***** !!OPCÇÃO INVÁLIDA!! *****\n");
                mainExec();
            }
        }
    }

    public final void menu(Conta c){    //menu principal com as operações disponíveis
        int op;
        float dep;
        System.out.println(
                  "\nEscolha uma das seguintes opcções:\n\n"
                + "1) Levantamentos\n"
                + "2) Consulta Saldos de Conta\n"
                + "3) Consulta Movimentos de Conta\n"
                + "4) Pagamento de Serviços\n"
                + "5) Depósitos\n"
                + "6) Anular");
        op = input.nextInt();
        switch (op){
            case 1:{
                menuLevantamentos(c);
                break;
            }
            case 2:{
                apresentaSaldos(c);
                mainExec();
                break;
            }
            case 3:{
                consultaMovimentos(c);
                break;
            }
            case 4:{
                menuPagamentoServicos(c);
                break;
            }
            case 5:{    
                System.out.println("\nInsira o montante a depositar:");
                //controlo para o montade inserido a depositar
                do{
                    dep = input.nextFloat();
                    if(dep <= 0)
                        System.out.println("\nInsira um valor positivo:");
                    else
                        c.deposito(dep);
                }while (dep <= 0);
                break;
            }
            case 6:{
                System.out.println("\n\n***** !!OPERACÃO ANULADA!! *****");
                System.exit(1);
            }
            default:{
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\n\n***** !!OPCÇÃO INVÁLIDA!! *****\n");
                menu(c);
            }  
        }
        Multibanco.guardaDados(c, this.cliente);
        Multibanco.exec();
    }

    //sub-menu para os levantamentos
    public void menuLevantamentos(Conta c){ 
        int op;
        System.out.println(
                "\nEscolha um dos seguintes montantes:\n\n"
                + "1) 20 €\n"
                + "2) 50 €\n"
                + "3) 100 €\n"
                + "4) 150 €\n"
                + "5) 200 €\n"
                + "6) Outros Valores\n"
                + "7) Outras Operações");
        op = input.nextInt();
        switch (op){
            case 1:{
                float lev = 20;
                c.levantamento(lev); //chamada ao método levantamento da classe conta
                break;
            }
            case 2:{
                float lev = 50;
                c.levantamento(lev);
                break;
            }
            case 3:{
                float lev = 100;
                c.levantamento(lev);
                break;
            }
            case 4:{
                float lev = 150;
                c.levantamento(lev);
                break;
            }
            case 5:{
                float lev = 200;
                c.levantamento(lev);
                break;
            }
            case 6:{
                float lev;
                // Controlo para o valor inserido ser múltiplo de 5 e no intervalo de 10 a 200;
                do{
                    System.out.println ("\nIntroduza o montante a levantar:");
                    lev = input.nextFloat();
                    if (!(lev % 5 == 0 && lev <= 200 && lev >= 10)) //Imprime mensagem para explicar ao utilizador
                            System.out.println("\nNao introduziu um valor multiplo de 5 maior que 10 e menor que 200");
                }while(!(lev % 5 == 0 && lev <= 200 && lev >= 10));
                    c.levantamento(lev); //chamada ao método levantamento da classe conta
                    break;
            }
            case 7:{
                menu(c); //retorna ao menu principal, sem tomar qualquer acção
                break;
            }
            default:{ //mensagem e tom de erro enviados caso outra opção seja escolhida
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\n\n***** !!OPCÇÃO INVÁLIDA!! *****\n");
                menuLevantamentos(c);
            }
        }
        Multibanco.guardaDados(c, this.cliente);
        Multibanco.exec();
    }

    //método (procedimento) que apresenta saldos no menu (final void)
    public void apresentaSaldos (Conta c){ //escreve no ecrã o saldo actual
        System.out.println ("\nSaldo Contabilístico: " + c.saldoContabilistico() + " €");
        System.out.println ("Saldo Disponivel: " + c.saldoDisponivel() + " €");
    }
    
    //método que apresenta saldos nos movimentos de conta
    public String consultaSaldos (Conta c){
        return "\nSaldo Contabilístico: " + c.saldoContabilistico() + " €\n" + "Saldo Disponivel: " + c.saldoDisponivel()+ " €";
    }

    //método para aceder aos movimentos de conta;
    public void consultaMovimentos (Conta c){
        int count = c.getMov().size() - 10;
        int max = c.getMov().size();
        System.out.println("\n**SALDO ACTUAL**" + consultaSaldos(c));
        System.out.println ("\n**Nº de CONTA**\n" + this.numConta);
        if (c.getMov().isEmpty()) //se Vector de Strings vazio, imprime mensagem
            System.out.println ("\n\n\t\t***** !!NÃO HÁ MOVIMENTOS REGISTADOS!! *****\n");
        else
            System.out.println ("\n\t\t**MOVIMENTOS DE CONTA**");
        if (max < 10) //senao, escreve até aos últimos 10 movimentos
            for (int i = 0; i < max; i++)
                System.out.println (c .getMov().elementAt(i));
        else
            for (int i = count; i < max; i++)
                System.out.println (c.getMov().elementAt(i));
    }

    //sub-menu para os diferentes pagamentos de serviços
    public void menuPagamentoServicos (Conta c){
        int op;
        System.out.println(
                "\nEscolha uma das seguintes opcções:\n\n"
                + "1) Conta da Electricidade\n"
                + "2) Conta da Água\n"
                + "3) Carregamento de Telemóvel\n"
                + "4) Outras Operações\n");
        op = input.nextInt();
        switch (op){
            case 1:{
                pagElec(c);
                break;
            }
            case 2:{
                pagAgua(c);
                break;
            }
            case 3:{
                operadoras(c);
                break;
            }
            case 4:{
                menu(c); //retorna ao menu principal, sem tomar qualquer acção
                break;
            }
            default:{ //mensagem e tom de erro enviados caso outra opção seja escolhida
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\n\n***** !!OPCÇÃO INVÁLIDA!! *****\n");
                menuPagamentoServicos(c);
            }
        }
        Multibanco.guardaDados(c, this.cliente);
        Multibanco.exec();
    }

    //método para o pagamento de serviços Conta de Electricidade
    public void pagElec(Conta c){
        int entidade, referencia;
        float valor;
        //controlo para o numero de dígitos da entidade (5)
        do {
            System.out.println("\nIntroduza a Entidade:");
            entidade = input.nextInt();
            if (entidade < 10000 || entidade > 99999)
                System.out.println("\nEntidade tem que ter 5 dígitos!");
        } while (entidade < 10000 || entidade > 99999) ;
        //controlo para o numero de dígitos da referência (9)
        do {
            System.out.println("\nIntroduza a Referência:");
            referencia = input.nextInt();
            if (referencia < 100000000 || referencia > 999999999)
                System.out.println("\nReferência tem que ter 9 dígitos!");
        } while (referencia < 100000000 || referencia > 999999999);

        System.out.println("\nIntroduza o Montante:");
        //controlo para o valor a pagar inserido
        do{
            valor = input.nextFloat();
            if(valor <= 0)
                System.out.println("Introduza um valor positivo: ");
            else
                c.pagElec(valor);
        }while (valor <=0);
    }
    //método para o pagamento de serviços Conta de Água
    public void pagAgua(Conta c){
        int entidade, referencia;
        float valor;
        //controlo para o numero de dígitos da entidade (5)
        do {
            System.out.println("\nIntroduza a Entidade:");
            entidade = input.nextInt();
            if (entidade < 10000 || entidade > 99999)
                System.out.println("\nEntidade tem que ter 5 dígitos!");
        } while (entidade < 10000 || entidade > 99999) ;
        //controlo para o numero de dígitos da referência (9)
        do {
            System.out.println("\nIntroduza a Referência:");
            referencia = input.nextInt();
            if (referencia < 100000000 || referencia > 999999999)
                System.out.println ("\nReferência tem que ter 9 dígitos!");
        } while (referencia < 100000000 || referencia > 999999999);

        System.out.println("\nIntroduza o Montante:");
        //controlo para o valor a pagar inserido
        do{
            valor = input.nextFloat();
            if(valor <= 0)
                System.out.println("Introduza um valor positivo: ");
            else
                c.pagAgua(valor);
        }while (valor <=0);

    }

    //sub-menu implementado para distinguir as operadoras;
    public void operadoras (Conta c){
        int op;
        System.out.println (
                "\nEscolha uma das seguintes opcções:\n\n"
                + "1) TMN\n"
                + "2) Vodafone\n"
                + "3) Optimus\n"
                + "4) Outras operações\n");
        op = input.nextInt();
        switch (op){
            case 1:{
                menuTelemovel(c); //retorna ao menu telemóvel
                break;
            }
            case 2:{
                menuTelemovel(c); 
                break;
            }
            case 3:{
                menuTelemovel(c);
                break;
            }
            case 4:{
                menu(c); //retorna ao menu principal, sem tomar qualquer acção
                break;
            }
            default:{ //mensagem e tom de erro enviados caso outra opção seja escolhida
                Toolkit.getDefaultToolkit().beep();
                System.out.println ("\n\n***** !!OPCÇÃO INVÁLIDA!! *****\n");
                operadoras(c);
            }
        }
        Multibanco.guardaDados(c, this.cliente);
        Multibanco.exec();
    }

    //método para o pagamento de serviços Carregamento de Telemóvel, após a escolha da operadora
    public void menuTelemovel (Conta c){
        int op, referencia = 0;
        //controlo para o numero de dígitos da referência (9)
        do {
            System.out.println("\nIntroduza a Referência:");
            referencia = input.nextInt();
            if (referencia < 100000000 || referencia > 999999999)
                System.out.println ("\nReferência tem que ter 9 dígitos!");
        }while (referencia < 100000000 || referencia > 999999999);
        //opções disponíveis para o montante a carregar
        System.out.println(
                "Escolha um dos seguintes montantes:\n"
                + "1) 5 €\n"
                + "2) 10 €\n"
                + "3) 20 €\n"
                + "4) Outras Operações\n");
        op = input.nextInt();
        switch (op){
            case 1:{
                int pag = 5;
                c.pagTelm(pag); //chamada ao método pagTlem da classe Conta
                break;
            }
            case 2:{
                int pag = 10;
                c.pagTelm(pag);
                break;
            }
            case 3:{
                int pag = 20;
                c.pagTelm(pag);
                break;
            }
            case 4:{
                menu(c); //retorna ao menu principal, sem tomar qualquer acção
                break;
            }
            default:{ //mensagem e tom de erro enviados caso outra opção seja escolhida
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\n\n***** !!OPCÇÃO INVÁLIDA!! *****\n");
                menuTelemovel(c);
            }
            Multibanco.guardaDados(c, this.cliente);
            Multibanco.exec();
        }
    }
}