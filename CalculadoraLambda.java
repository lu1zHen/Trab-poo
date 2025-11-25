// Interface funcional para operações matemáticas
interface Operacao {
    double executar(double a, double b);
}

public class CalculadoraLambda {
    public static void main(String[] args) {
        // Implementações usando expressões lambda

        // Soma
        Operacao soma = (a, b) -> a + b;

        // Subtração
        Operacao subtracao = (a, b) -> a - b;

        // Multiplicação
        Operacao multiplicacao = (a, b) -> a * b;

        // Divisão com tratamento de divisão por zero
        Operacao divisao = (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Divisão por zero!");
            }
            return a / b;
        };

        // Testando as operações
        double x = 10.0;
        double y = 5.0;

        System.out.println("🧮 === CALCULADORA LAMBDA ===\n");

        System.out.println("Valores: " + x + " e " + y);
        System.out.println("----------------------------");

        System.out.println("Soma: " + x + " + " + y + " = " + soma.executar(x, y));
        System.out.println("Subtração: " + x + " - " + y + " = " + subtracao.executar(x, y));
        System.out.println("Multiplicação: " + x + " × " + y + " = " + multiplicacao.executar(x, y));

        try {
            System.out.println("Divisão: " + x + " ÷ " + y + " = " + divisao.executar(x, y));
        } catch (ArithmeticException e) {
            System.out.println("Divisão: " + e.getMessage());
        }

        // Testando com outros valores
        System.out.println("\n=== OUTROS TESTES ===");
        System.out.println("Soma (15 + 3): " + soma.executar(15, 3));
        System.out.println("Subtração (8 - 12): " + subtracao.executar(8, 12));
        System.out.println("Multiplicação (7 × 6): " + multiplicacao.executar(7, 6));

        // Teste de divisão por zero
        try {
            System.out.println("Divisão (10 ÷ 0): " + divisao.executar(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("Divisão (10 ÷ 0): " + e.getMessage());
        }
    }
}