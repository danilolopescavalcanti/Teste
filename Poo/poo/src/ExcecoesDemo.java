import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class ExcecoesDemo {

    // ==== Execução de exemplo ====
    public static void main(String[] args) {
        ContaBancaria conta = new ContaBancaria("Ana", 1000.0);

        System.out.println("1) Checked exception (SaldoInsuficienteException):");
        try {
            conta.sacar(1500.0); // lança exceção checked
        } catch (SaldoInsuficienteException e) {
            System.out.println("Erro ao sacar: " + e.getMessage());
        }

        System.out.println("\n2) Unchecked exception (IllegalArgumentException) + finally:");
        try {
            conta.depositar(-50.0); // lança exceção unchecked
        } catch (IllegalArgumentException e) {
            System.out.println("Depósito inválido: " + e.getMessage());
        } finally {
            System.out.println("Saldo atual de " + conta.getTitular() + ": R$ " + conta.getSaldo());
        }

        System.out.println("\n3) Multi-catch (tratando mais de um tipo):");
        String entrada = "42a"; // propositalmente inválido
        try {
            int numero = Integer.parseInt(entrada);
            System.out.println("Número lido: " + numero);
        } catch (NumberFormatException e) {
            System.out.println("Falha ao converter \"" + entrada + "\": " + e.getMessage());
        }

        System.out.println("\n4) Try-with-resources (fecha o recurso automaticamente):");
        String dados = "10\n20\ntrinta\n40";
        try (BufferedReader br = new BufferedReader(new StringReader(dados))) {
            String linha;
            int soma = 0, lin = 0;
            while ((linha = br.readLine()) != null) {
                lin++;
                try {
                    soma += Integer.parseInt(linha);
                } catch (NumberFormatException e) {
                    System.out.println("Linha " + lin + " inválida: \"" + linha + "\"");
                }
            }
            System.out.println("Soma das linhas numéricas: " + soma);
        } catch (IOException e) {
            System.out.println("Erro de I/O: " + e.getMessage());
        }

        System.out.println("\n5) Encadeamento (rethrow) com causa preservada:");
        try {
            processarCPF(null); // vai falhar e encadear a causa
        } catch (RuntimeException e) {
            System.out.println("Falha ao processar CPF: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Causa original: " + e.getCause());
            }
        }
    }

    // ==== Exemplo de rethrow/encadeamento ====
    static void processarCPF(String cpf) {
        try {
            validarCPF(cpf);
            System.out.println("CPF válido e processado com sucesso!");
        } catch (IllegalArgumentException e) { // capturamos a causa...
            // ...e relançamos com um contexto mais rico, preservando a causa original:
            throw new RuntimeException("CPF inválido fornecido para processamento", e);
        }
    }

    static void validarCPF(String cpf) {
        if (cpf == null) throw new IllegalArgumentException("CPF nulo.");
        if (!cpf.matches("\\d{11}")) throw new IllegalArgumentException("CPF deve ter 11 dígitos numéricos.");
    }

    // ==== Domínio bancário: checked e unchecked ====
    static class ContaBancaria {
        private final String titular;
        private double saldo;

        ContaBancaria(String titular, double saldoInicial) {
            if (titular == null || titular.isBlank()) {
                throw new IllegalArgumentException("Titular não pode ser vazio.");
            }
            if (saldoInicial < 0) {
                throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
            }
            this.titular = titular;
            this.saldo = saldoInicial;
        }

        public String getTitular() { return titular; }
        public double getSaldo() { return saldo; }

        public void depositar(double valor) {
            if (valor <= 0) {
                // Exceção unchecked: erro de programação/validação de argumento
                throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
            }
            saldo += valor;
        }

        public void sacar(double valor) throws SaldoInsuficienteException {
            if (valor <= 0) {
                throw new IllegalArgumentException("Valor do saque deve ser positivo.");
            }
            if (valor > saldo) {
                // Exceção checked: condição esperada do domínio que obriga tratamento
                double faltante = valor - saldo;
                throw new SaldoInsuficienteException(
                    "Saldo insuficiente. Faltam R$ " + String.format("%.2f", faltante),
                    saldo, valor
                );
            }
            saldo -= valor;
        }
    }

    // Exceção checked personalizada
    static class SaldoInsuficienteException extends Exception {
        private static final long serialVersionUID = 1L; // <- adiciona isto

        private final double saldoAtual;
        private final double tentativaDeSaque;

        public SaldoInsuficienteException(String msg, double saldoAtual, double tentativaDeSaque) {
            super(msg);
            this.saldoAtual = saldoAtual;
            this.tentativaDeSaque = tentativaDeSaque;
        }

        public double getSaldoAtual() { return saldoAtual; }
        public double getTentativaDeSaque() { return tentativaDeSaque; }
    }
}