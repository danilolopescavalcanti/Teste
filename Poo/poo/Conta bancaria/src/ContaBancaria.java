public class ContaBancaria {
    private String titular;
    private double saldo;

    public ContaBancaria(String titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de depósito inválido.");
            return;
        }
        saldo += valor;
        System.out.println("Depósito realizado. Novo saldo: " + saldo);
    }

    public void sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque inválido.");
            return;
        }
        if (valor > saldo) {
            System.out.println("Saldo insuficiente para saque.");
            return;
        }
        saldo -= valor;
        System.out.println("Saque realizado. Novo saldo: " + saldo);
    }

    public void transferir(ContaBancaria destino, double valor) {
        if (destino == null) {
            System.out.println("Conta de destino inexistente.");
            return;
        }
        if (valor <= 0) {
            System.out.println("Valor de transferência inválido.");
            return;
        }
        if (valor > saldo) {
            System.out.println("Saldo insuficiente para transferência.");
            return;
        }
        this.saldo -= valor;
        destino.saldo += valor;
        System.out.println("Transferência realizada. Seu novo saldo: " + saldo);
    }
}
	