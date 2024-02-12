package sinerji;
import java.util.ArrayList;
import java.util.List;

class Funcionario {
    private String nome;
    private String cargo;
    private int anoContratacao;

    public Funcionario(String nome, String cargo, int anoContratacao) {
        this.nome = nome;
        this.cargo = cargo;
        this.anoContratacao = anoContratacao;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public int getAnoContratacao() {
        return anoContratacao;
    }
}

class Vendedor extends Funcionario {
    private List<Double> vendas;

    public Vendedor(String nome, int anoContratacao) {
        super(nome, "Vendedor", anoContratacao);
        vendas = new ArrayList<>();
    }

    public void addVenda(double valor) {
        vendas.add(valor);
    }

    public List<Double> getVendas() {
        return vendas;
    }

    public double getTotalVendas() {
        double total = 0;
        for (Double venda : vendas) {
            total += venda;
        }
        return total;
    }
}

class Empresa {
    private List<Funcionario> funcionarios;

    public Empresa() {
        funcionarios = new ArrayList<>();
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Vendedor> getVendedores() {
        List<Vendedor> vendedores = new ArrayList<>();
        for (Funcionario funcionario : funcionarios) {
            if (funcionario instanceof Vendedor) {
                vendedores.add((Vendedor) funcionario);
            }
        }
        return vendedores;
    }

    public void valorTotalPago(List<Funcionario> funcionarios, int mes, int ano) {
        double totalSalarioBeneficio = 0;
        double totalSalario = 0;
        double totalBeneficio = 0;

        for (Funcionario funcionario : funcionarios) {
            double salario = calcularSalario(funcionario, mes, ano);
            double beneficio = calcularBeneficio(funcionario, mes, ano);
            totalSalarioBeneficio += salario + beneficio;
            totalSalario += salario;
            totalBeneficio += beneficio;
            System.out.println("Nome: " + funcionario.getNome() + ", Salário + Benefício: R$" + (salario + beneficio));
        }

        System.out.println("Total pago (Salário + Benefício) no mês " + mes + "/" + ano + ": R$" + totalSalarioBeneficio);
        System.out.println("Total pago em salários no mês " + mes + "/" + ano + ": R$" + totalSalario);
        System.out.println("Total pago em benefícios no mês " + mes + "/" + ano + ": R$" + totalBeneficio);
        System.out.println();
    }

    public void valorTotalSalario(List<Funcionario> funcionarios, int mes, int ano) {
        double totalSalario = 0;

        for (Funcionario funcionario : funcionarios) {
            double salario = calcularSalario(funcionario, mes, ano);
            totalSalario += salario;
            System.out.println("Nome: " + funcionario.getNome() + ", Salário: R$" + salario);
        }

        System.out.println("Total pago em salários no mês " + mes + "/" + ano + ": R$" + totalSalario);
        System.out.println();
    }

    public void valorTotalBeneficio(List<Funcionario> funcionarios, int mes, int ano) {
        double totalBeneficio = 0;

        for (Funcionario funcionario : funcionarios) {
            double beneficio = calcularBeneficio(funcionario, mes, ano);
            totalBeneficio += beneficio;
        }

        System.out.println("Total pago em benefícios no mês " + mes + "/" + ano + ": R$" + totalBeneficio);
        System.out.println();
    }

    public String funcionarioComMaiorValorNoMes(List<Funcionario> funcionarios, int mes, int ano) {
        double maiorValor = 0;
        String nomeFuncionario = "";

        for (Funcionario funcionario : funcionarios) {
            double valorPago = calcularSalario(funcionario, mes, ano) + calcularBeneficio(funcionario, mes, ano);
            if (valorPago > maiorValor) {
                maiorValor = valorPago;
                nomeFuncionario = funcionario.getNome();
            }
        }

        return nomeFuncionario;
    }

    public String funcionarioComMaiorBeneficioNoMes(List<Funcionario> funcionarios, int mes, int ano) {
        double maiorBeneficio = 0;
        String nomeFuncionario = "";

        for (Funcionario funcionario : funcionarios) {
            double beneficio = calcularBeneficio(funcionario, mes, ano);
            if (beneficio > maiorBeneficio) {
                maiorBeneficio = beneficio;
                nomeFuncionario = funcionario.getNome();
            }
        }

        return nomeFuncionario;
    }

    public String vendedorComMaisVendasNoMes(List<Vendedor> vendedores, int mes, int ano) {
        double maiorVenda = 0;
        String nomeVendedor = "";

        for (Vendedor vendedor : vendedores) {
            double venda = 0;
            for (Double valor : vendedor.getVendas()) {
                venda += valor;
            }
            if (venda > maiorVenda) {
                maiorVenda = venda;
                nomeVendedor = vendedor.getNome();
            }
        }

        return nomeVendedor;
    }

    private double calcularSalario(Funcionario funcionario, int mes, int ano) {
        double salarioBase = 0;

        switch (funcionario.getCargo()) {
            case "Secretário":
                salarioBase = 7000 + (1000 * (ano - funcionario.getAnoContratacao()));
                break;
            case "Vendedor":
                salarioBase = 12000 + (1800 * (ano - funcionario.getAnoContratacao()));
                break;
            case "Gerente":
                salarioBase = 20000 + (3000 * (ano - funcionario.getAnoContratacao()));
                break;
        }

        return salarioBase;
    }

    private double calcularBeneficio(Funcionario funcionario, int mes, int ano) {
        double beneficio = 0;

        if (funcionario.getCargo().equals("Secretário")) {
            beneficio = calcularSalario(funcionario, mes, ano) * 0.2;
        } else if (funcionario.getCargo().equals("Vendedor")) {
            // Vamos assumir que o benefício do vendedor é o total das vendas no mês
            Vendedor vendedor = (Vendedor) funcionario;
            double totalVendas = 0;
            for (double venda : vendedor.getVendas()) {
                totalVendas += venda;
            }
            beneficio = totalVendas * 0.3;
        }

        return beneficio;
    }
}

public class Main {
    public static void main(String[] args) {
        Empresa empresa = new Empresa();

        empresa.addFuncionario(new Funcionario("Jorge Carvalho", "Secretário", 2018));
        empresa.addFuncionario(new Funcionario("Maria Souza", "Secretário", 2015));
        Vendedor anaSilva = new Vendedor("Ana Silva", 2021);
        anaSilva.addVenda(5200);
        anaSilva.addVenda(4000);
        anaSilva.addVenda(4200);
        anaSilva.addVenda(5850);
        anaSilva.addVenda(7000);
        empresa.addFuncionario(anaSilva);
        Vendedor joaoMendes = new Vendedor("João Mendes", 2021);
        joaoMendes.addVenda(3400);
        joaoMendes.addVenda(7700);
        joaoMendes.addVenda(5000);
        joaoMendes.addVenda(5900);
        joaoMendes.addVenda(6500);
        empresa.addFuncionario(joaoMendes);
        empresa.addFuncionario(new Funcionario("Juliana Alves", "Gerente", 2017));
        empresa.addFuncionario(new Funcionario("Bento Albino", "Gerente", 2014));

        List<Funcionario> funcionarios = empresa.getFuncionarios();
        List<Vendedor> vendedores = empresa.getVendedores();

        // Testando os métodos para diferentes meses e anos
        System.out.println("=== Mês 4 / 2022 ===");
        empresa.valorTotalPago(funcionarios, 4, 2022); // Abril de 2022
        empresa.valorTotalSalario(funcionarios, 4, 2022); // Abril de 2022
        empresa.valorTotalBeneficio(funcionarios, 4, 2022); // Abril de 2022
        System.out.println("Funcionário com o maior valor pago no mês: " + empresa.funcionarioComMaiorValorNoMes(funcionarios, 4, 2022)); // Abril de 2022
        System.out.println("Funcionário com o maior benefício pago no mês: " + empresa.funcionarioComMaiorBeneficioNoMes(funcionarios, 4, 2022)); // Abril de 2022
        System.out.println("Vendedor com mais vendas no mês: " + empresa.vendedorComMaisVendasNoMes(vendedores, 4, 2022)); // Abril de 2022
        System.out.println();

        System.out.println("=== Mês 3 / 2022 ===");
        empresa.valorTotalPago(funcionarios, 3, 2022); // Março de 2022
        empresa.valorTotalSalario(funcionarios, 3, 2022); // Março de 2022
        empresa.valorTotalBeneficio(funcionarios, 3, 2022); // Março de 2022
        System.out.println("Funcionário com o maior valor pago no mês: " + empresa.funcionarioComMaiorValorNoMes(funcionarios, 3, 2022)); // Março de 2022
        System.out.println("Funcionário com o maior benefício pago no mês: " + empresa.funcionarioComMaiorBeneficioNoMes(funcionarios, 3, 2022)); // Março de 2022
        System.out.println("Vendedor com mais vendas no mês: " + empresa.vendedorComMaisVendasNoMes(vendedores, 3, 2022)); // Março de 2022
        System.out.println();

        System.out.println("=== Mês 2 / 2022 ===");
        empresa.valorTotalPago(funcionarios, 2, 2022); // Fevereiro de 2022
        empresa.valorTotalSalario(funcionarios, 2, 2022); // Fevereiro de 2022
        empresa.valorTotalBeneficio(funcionarios, 2, 2022); // Fevereiro de 2022
        System.out.println("Funcionário com o maior valor pago no mês: " + empresa.funcionarioComMaiorValorNoMes(funcionarios, 2, 2022)); // Fevereiro de 2022
        System.out.println("Funcionário com o maior benefício pago no mês: " + empresa.funcionarioComMaiorBeneficioNoMes(funcionarios, 2, 2022)); // Fevereiro de 2022
        System.out.println("Vendedor com mais vendas no mês: " + empresa.vendedorComMaisVendasNoMes(vendedores, 2, 2022)); // Fevereiro de 2022
        System.out.println();

        System.out.println("=== Mês 1 / 2022 ===");
        empresa.valorTotalPago(funcionarios, 1, 2022); // Janeiro de 2022
        empresa.valorTotalSalario(funcionarios, 1, 2022); // Janeiro de 2022
        empresa.valorTotalBeneficio(funcionarios, 1, 2022); // Janeiro de 2022
        System.out.println("Funcionário com o maior valor pago no mês: " + empresa.funcionarioComMaiorValorNoMes(funcionarios, 1, 2022)); // Janeiro de 2022
        System.out.println("Funcionário com o maior benefício pago no mês: " + empresa.funcionarioComMaiorBeneficioNoMes(funcionarios, 1, 2022)); // Janeiro de 2022
        System.out.println("Vendedor com mais vendas no mês: " + empresa.vendedorComMaisVendasNoMes(vendedores, 1, 2022)); // Janeiro de 2022
        System.out.println();

        System.out.println("=== Mês 12 / 2021 ===");
        empresa.valorTotalPago(funcionarios, 12, 2021); // Dezembro de 2021
        empresa.valorTotalSalario(funcionarios, 12, 2021); // Dezembro de 2021
        empresa.valorTotalBeneficio(funcionarios, 12, 2021); // Dezembro de 2021
        System.out.println("Funcionário com o maior valor pago no mês: " + empresa.funcionarioComMaiorValorNoMes(funcionarios, 12, 2021)); // Dezembro de 2021
        System.out.println("Funcionário com o maior benefício pago no mês: " + empresa.funcionarioComMaiorBeneficioNoMes(funcionarios, 12, 2021)); // Dezembro de 2021
        System.out.println("Vendedor com mais vendas no mês: " + empresa.vendedorComMaisVendasNoMes(vendedores, 12, 2021)); // Dezembro de 2021
    }
}
