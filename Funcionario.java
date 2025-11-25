public class Funcionario {
    private String nome;
    private double salario;
    private boolean ativo;

    // Construtor padrão
    public Funcionario() {
    }

    // Construtor com parâmetros
    public Funcionario(String nome, double salario, boolean ativo) {
        this.nome = nome;
        this.salario = salario;
        this.ativo = ativo;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // Métodos adicionais
    public String getStatus() {
        return ativo ? "Ativo" : "Inativo";
    }

    public void aumentarSalario(double percentual) {
        if (percentual > 0) {
            this.salario += this.salario * (percentual / 100);
        }
    }

    // Método toString
    @Override
    public String toString() {
        return "Funcionario{" +
                "nome='" + nome + '\'' +
                ", salario=R$ " + salario +
                ", ativo=" + ativo +
                '}';
    }

    // Método main para teste
    public static void main(String[] args) {
        // Criando funcionário com construtor completo
        Funcionario func1 = new Funcionario("Luiz Henrique", 3500.0, true);

        // Criando funcionário com construtor padrão e setters
        Funcionario func2 = new Funcionario();
        func2.setNome("Leonan");
        func2.setSalario(4200.0);
        func2.setAtivo(false);

        // Exibindo informações do funcionário 1
        System.out.println("Funcionário 1:");
        System.out.println("Nome: " + func1.getNome());
        System.out.println("Salário: R$ " + func1.getSalario());
        System.out.println("Ativo: " + func1.isAtivo());
        System.out.println("Status: " + func1.getStatus());

        // Exibindo informações do funcionário 2
        System.out.println("\nFuncionário 2:");
        System.out.println("Nome: " + func2.getNome());
        System.out.println("Salário: R$ " + func2.getSalario());
        System.out.println("Ativo: " + func2.isAtivo());
        System.out.println("Status: " + func2.getStatus());

        // Aplicando aumento de salário
        func1.aumentarSalario(10);
        System.out.println("\nNovo salário do Luiz Henrique após aumento: R$ " + func1.getSalario());

        // Usando método toString
        System.out.println("\n" + func1.toString());
        System.out.println(func2.toString());
    }
}