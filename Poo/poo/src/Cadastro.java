import java.util.Scanner;

public class Cadastro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu nome: ");
        String nome = scanner.next();

        System.out.println("Digite sua Idade:");
        int idade = scanner.nextInt();

        if (idade >= 18) { 
            System.out.println("Sua idade esta correta");
        }
        else  {
            System.out.println(" Sua idade esta incorreta");
        }  
        scanner.close();
     }
}