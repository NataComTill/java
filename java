import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private String numero;
    private String titular;
    protected double saldo;
    private List<Transacao> transacoes;

    public Conta(String numero, String titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.transacoes = new ArrayList<>();
    }

    public abstract void depositar(double valor);
    public abstract void sacar(double valor) throws SaldoInsuficienteException;
    public abstract void transferir(double valor, Conta contaDestino) throws SaldoInsuficienteException;

    public double consultarSaldo() {
        return saldo;
    }

    protected void adicionarTransacao(String tipo, double valor, String destino) {
        Transacao transacao = new Transacao(tipo, valor, destino);
        this.transacoes.add(transacao);
    }

    protected void adicionarTransacao(String tipo, double valor) {
        Transacao transacao = new Transacao(tipo, valor);
        this.transacoes.add(transacao);
    }

    public List<Transacao> obterHistoricoTransacoes() {
        return this.transacoes;
    }

    // Getters and Setters
    public String getNumero() { return numero; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
}
public class Transacao {
    private String tipo;
    private double valor;
    private String destino;

    public Transacao(String tipo, double valor, String destino) {
        this.tipo = tipo;
        this.valor = valor;
        this.destino = destino;
    }

    public Transacao(String tipo, double valor) {
        this(tipo, valor, null);
    }

    // Getters and toString()
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public String getDestino() { return destino; }

    @Override
    public String toString() {
        if (destino != null) {
            return "Transacao [tipo=" + tipo + ", valor=" + valor + ", destino=" + destino + "]";
        } else {
            return "Transacao [tipo=" + tipo + ", valor=" + valor + "]";
        }
    }
}
public class ContaCorrente extends Conta {
    private double juros;

    public ContaCorrente(String numero, String titular, double saldoInicial, double juros) {
        super(numero, titular, saldoInicial);
        this.juros = juros;
    }

    public ContaCorrente(String numero, String titular, double saldoInicial) {
        this(numero, titular, saldoInicial, 0.05);
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        adicionarTransacao("Depósito", valor);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        adicionarTransacao("Saque", valor);
    }

    @Override
    public void transferir(double valor, Conta contaDestino) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        contaDestino.depositar(valor);
        adicionarTransacao("Transferência", valor, contaDestino.getNumero());
    }
}
public class ContaPoupanca extends Conta {
    private double rendimento;

    public ContaPoupanca(String numero, String titular, double saldoInicial, double rendimento) {
        super(numero, titular, saldoInicial);
        this.rendimento = rendimento;
    }

    public ContaPoupanca(String numero, String titular, double saldoInicial) {
        this(numero, titular, saldoInicial, 0.002);
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        adicionarTransacao("Depósito", valor);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        adicionarTransacao("Saque", valor);
    }

    @Override
    public void transferir(double valor, Conta contaDestino) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        contaDestino.depositar(valor);
        adicionarTransacao("Transferência", valor, contaDestino.getNumero());
    }
}
public class ContaUniversitaria extends Conta {

    public ContaUniversitaria(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        adicionarTransacao("Depósito", valor);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        adicionarTransacao("Saque", valor);
    }

    @Override
    public void transferir(double valor, Conta contaDestino) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        contaDestino.depositar(valor);
        adicionarTransacao("Transferência", valor, contaDestino.getNumero());
    }
}
import java.util.List;

public class ContaConjunta extends Conta {
    private List<String> titulares;

    public ContaConjunta(String numero, List<String> titulares, double saldoInicial) {
        super(numero, titulares.toString(), saldoInicial);
        this.titulares = titulares;
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        adicionarTransacao("Depósito", valor);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        adicionarTransacao("Saque", valor);
    }

    @Override
    public void transferir(double valor, Conta contaDestino) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }
        saldo -= valor;
        contaDestino.depositar(valor);
        adicionarTransacao("Transferência", valor, contaDestino.getNumero());
    }
}
import java.util.List;

public class Banco {
    public static Conta cadastrarConta(String tipo, String numero, String titular, double saldoInicial) {
        switch (tipo.toLowerCase()) {
            case "corrente":
                return new ContaCorrente(numero, titular, saldoInicial);
            case "poupanca":
                return new ContaPoupanca(numero, titular, saldoInicial);
            case "universitaria":
                return new ContaUniversitaria(numero, titular, saldoInicial);
            default:
                throw new IllegalArgumentException("Tipo de conta desconhecido");
        }
    }

    public static Conta cadastrarContaConjunta(String numero, List<String> titulares, double saldoInicial) {
        return new ContaConjunta(numero, titulares, saldoInicial);
    }

    public static double realizarDeposito(Conta conta, double valor) {
        conta.depositar(valor);
        return conta.consultarSaldo();
    }

    public static void realizarSaque(Conta conta, double valor) throws SaldoInsuficienteException {
        conta.sacar(valor);
    }

    public static void realizarTransferencia(Conta contaOrigem, Conta contaDestino, double valor) throws SaldoInsuficienteException {
        contaOrigem.transferir(valor, contaDestino);
    }
}
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            // Criação de contas
            Conta conta1 = Banco.cadastrarConta("corrente", "12345", "Alice", 1000);
            Conta conta2 = Banco.cadastrarConta("poupanca", "67890", "Bob", 500);

            // Realização de operações
            System.out.println("Saldo após depósito: " + Banco.realizarDeposito(conta1, 200));  // Depósito
            Banco.realizarSaque(conta
