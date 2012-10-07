package caixaMultibanco;

import java.util.Random;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 *
 * @authors: Miguel Oliveira; Nuno Diogo da Silva; Paulo Silva;
 */

public class Conta{ 

    //variáveis de classe
    private static Random geraSaldo = new Random();
    private static Random geraPlafond = new Random();

    //atributos de instância
    private float saldo;
    private float plafond;
    private Vector<String> mov;

    //construtor
    public Conta (){
        saldo = geraSaldo.nextInt(1001) + geraSaldo.nextFloat();
        plafond = geraPlafond.nextInt(101);
        mov = new Vector<String>();
        
    }

    public Conta (float saldo, float plafond, Vector<String> mov){
        this.saldo = saldo;
        this.plafond = plafond;
        this.mov = mov;
    }

    //selectores
    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public float getPlafond() {
        return plafond;
    }

    public float saldoContabilistico() {
        return saldo;
    }

    public float saldoDisponivel(){
        return saldo+plafond;
    }

    public Vector<String> getMov() {
        return mov;
    }

    //modificadores

    //método que actualiza o saldo após a introdução do montante a depositar e que regista esta operação no ficheiro dos movimentos do cliente
    public void deposito (float valor){
        this.saldo += valor;
        String op = (getDateTime() + "/Depósito MB" + "/Crédito/" + valor + " €");
        mov.add(op);
    }

    //método que actualiza o saldo após um levantamento e que regista esta operação no ficheiro dos movimentos do cliente
    public void levantamento (float valor){
        if (this.saldo + this.plafond >= valor){
            this.saldo -= valor;
            String op = (getDateTime() + "/Levantamento MB" + "/Débito/" + valor  + " €");
            mov.add(op);
        }
        else
            System.out.println ("\n!!Ultrapassou Montante Autorizado!!");
    }

    //método que actualiza o saldo após um pagamento de electricidade e que regista esta operação no ficheiro dos movimentos do cliente
    public void pagElec (float valor){
        if (this.saldo + this.plafond >= valor){
            this.saldo -= valor;
            String op = (getDateTime() + "/Pagamentos de serviços de Electricidade" + "/Débito/" + valor + " €");
            mov.add(op);
        }
        else
            System.out.println ("\n!!Ultrapassou Montante Autorizado!!");
    }

    //método que actualiza o saldo após um pagamento de água e que regista esta operação no ficheiro dos movimentos do cliente
    public void pagAgua (float valor){
        if (this.saldo + this.plafond >= valor){
            this.saldo -= valor;
            String op = (getDateTime() + "/Pagamentos de serviços de Água" + "/Débito/" + valor + " €");
            mov.add(op);
        }
        else
            System.out.println ("\n!!Ultrapassou Montante Autorizado!!");
    }

    //método que actualiza o saldo após um carregamento de telemovel e que regista esta operação no ficheiro dos movimentos do cliente
    public void pagTelm (float valor){
        if (this.saldo + this.plafond >= valor){
            this.saldo -= valor;
            String op = (getDateTime() + "/Pagamentos de serviços de Telemóvel" + "/Débito/" + valor + " €");
            mov.add(op);
        }
        else
            System.out.println ("\n!!Ultrapassou Montante Autorizado!!");
    }
}