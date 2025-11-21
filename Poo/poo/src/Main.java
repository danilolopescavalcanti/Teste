import java.util.ArrayList;
import java.util.List;

class Funcionario {
    private final String nome;
    private final double salarioBase;

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    public String getNome() {
        return nome;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    /**
     * Regra padrão: 10% do salário.
     * Subclasses podem sobrescrever.
     */
    public double calcularBonus() {
        return salarioBase * 0.10;
    }

    public String cargo() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return cargo() + "{" + "nome='" + nome + "', salarioBase=" + salarioBase + "}";
    }
}

class Gerente extends Funcionario {
    public Gerente(String nome, double salarioBase) {
        super(nome, salarioBase);
    }

    @Override
    public double calcularBonus() {
        // Gerente: 20% + bônus fixo
        return getSalarioBase() * 0.20 + 500.0;
    }
}

class Analista extends Funcionario {
    public Analista(String nome, double salarioBase) {
        super(nome, salarioBase);
    }

    @Override
    public double calcularBonus() {
        // Analista: 15%
        return getSalarioBase() * 0.15;
    }
}

class Estagiario extends Funcionario {
    public Estagiario(String nome, double salarioBase) {
        super(nome, salarioBase);
    }

    @Override
    public double calcularBonus() {
        // Estagiário: 5%, com teto de R$ 300
        double bonus = getSalarioBase() * 0.05;
        return Math.min(bonus, 300.0);
    }
}

public class Main {
    public static void main(String[] args) {
        // Lista com tipo de referência da superclasse
        List<Funcionario> equipe = new ArrayList<>();
        equipe.add(new Funcionario("Diego", 5000.0));   // usa regra padrão (10%)
        equipe.add(new Gerente("Ana", 10000.0));        // usa regra do Gerente
        equipe.add(new Analista("Bruno", 8000.0));      // usa regra do Analista
        equipe.add(new Estagiario("Clara", 2000.0));    // usa regra do Estagiário

        System.out.println("=== Demonstração de Ligação Dinâmica (Polimorfismo) ===");
        for (Funcionario f : equipe) {
            // Apesar de 'f' ser declarado como Funcionario,
            // o método chamado é o da CLASSE REAL do objeto em tempo de execução.
            double bonus = f.calcularBonus(); // <-- LIGAÇÃO DINÂMICA aqui
            System.out.printf("%s - %s | Salário: R$ %.2f | Bônus: R$ %.2f%n",
                    f.cargo(), f.getNome(), f.getSalarioBase(), bonus);
        }

        System.out.println("\n=== Ligação dinâmica em um método utilitário ===");
        for (Funcionario f : equipe) {
            imprimirResumoDePagamento(f); // também dispara ligação dinâmica internamente
        }
    }

    // Este método recebe a superclasse. Qualquer subclasse funciona (polimorfismo).
    private static void imprimirResumoDePagamento(Funcionario f) {
        System.out.printf("Pagando bônus de R$ %.2f para %s (%s)%n",
                f.calcularBonus(), f.getNome(), f.cargo()); // ligação dinâmica de novo
    }
}

