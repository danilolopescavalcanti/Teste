import java.util.Scanner;

// Classe Pessoa com atributos e método para validar a idade
class Pessoa {
    private String nome;
    private int idade;

    // Construtor da classe Pessoa
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    // Método para verificar se a idade é maior que 18
    public void verificarIdade() {
        if (idade > 18) {
            System.out.println("Olá " + nome + ", sua idade é válida.");
        } else {
            System.out.println("A idade informada é inválida. Você precisa ser maior que 18 anos.");
        }
    }
}

public class ValidacaoIdadePOO {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pedir o nome
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        // Pedir a idade
        System.out.print("Digite sua idade: ");
        int idade = scanner.nextInt();

        // Criar um objeto da classe Pessoa
        Pessoa pessoa = new Pessoa(nome, idade);

        // Chamar o método para verificar a idade
        pessoa.verificarIdade();

        scanner.close();
    }
}

