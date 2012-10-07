package caixaMultibanco;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @authors: Miguel Oliveira; Nuno Diogo da Silva; Paulo Silva;
 */

public class Multibanco implements Serializable{ //mantém a integridade do ficheiro de objectos quando se escreve e se lê
    
    private static Scanner input = new Scanner (System.in); //variável para ler texto


    //Controlo o código introduzido pelo utilizador, verificando de acordo com as restrições necessárias;
    public static String controloVerificaCodigo(int j){
        BufferedReader inS;
        String linha;
        String[] aux = null;
        String codigo;
        try{
            inS = new BufferedReader(new FileReader("clientes.txt"));
            System.out.println ("\nInsira codigo:");
            codigo = input.next();
            linha = inS.readLine();
            while (linha != null){
                aux = linha.split(",");
                if (Integer.parseInt(codigo) == Integer.parseInt(aux[0]))
                    return linha;
                linha = inS.readLine();
            }
            if (j==0){
                throw new Exception("\n\n***** !!CARTAO BLOQUEADO!! *****\n");
            }
            System.out.println ("\nTem mais " + j + " tentativas\n");
            return controloVerificaCodigo(--j);
        }
        catch (InputMismatchException e){
            System.out.println ("\n***** !!INSERÇÃO INVÁLIDA!! *****");
            exec();
            return null;
        }
        catch (NumberFormatException e){
            System.out.println ("\n***** !!INSERÇÃO INVÁLIDA!! *****");
            exec();
            return null;
        }
        catch (FileNotFoundException e){
            System.out.println ("Ficheiro não encontrado / não existe");
            return null;
        }
        catch (IOException e){
            System.out.println ("Erro de leitura");
            return null;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
            return null;
        }

    }

    //Escreve os novos dados após uma operação concluída, no ficheiro de objectos
    public static void guardaDados(Conta c, String cliente){
        try {
            ObjectOutputStream out;
            out = new ObjectOutputStream(new FileOutputStream(cliente));
            out.writeFloat(c.saldoContabilistico());
            out.writeFloat(c.getPlafond());
            out.writeObject(c.getMov());
            out.close();
        }
        catch (FileNotFoundException e){
            System.out.println ("Ficheiro não encontrado / não existe");
        }
        catch (IOException ex) {
            System.out.println("Erro de escrita!");
        }
    }

    //método que executa o método VerificaCódigo e posteriormente os menus afectos à respectiva conta
    public static void exec(){
        String linha = controloVerificaCodigo(2);
        String[] aux = null;
        aux = linha.split(",");
        String cliente = aux[3];
        String numConta = aux[1];
        GerirConta gerir;
        if (cliente != null)
            gerir = new GerirConta(cliente, numConta);
     }

    public static void main(String[] args){
        System.out.println ("***BEM-VINDO À REDE DE CAIXAS MB DA UNIVERSIDADE DOS AÇORES***\n");
        exec();
    }
}