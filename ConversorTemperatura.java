import java.util.Scanner;

interface Conversao {
    double converter(double valor);
}

class CelsiusParaFahrenheit implements Conversao {
    @Override
    public double converter(double valor) {
        return (valor * 9/5) + 32;
    }

    @Override
    public String toString() {
        return "Celsius para Fahrenheit";
    }
}

class FahrenheitParaCelsius implements Conversao {
    @Override
    public double converter(double valor) {
        return (valor - 32) * 5/9;
    }

    @Override
    public String toString() {
        return "Fahrenheit para Celsius";
    }
}

class CelsiusParaKelvin implements Conversao {
    @Override
    public double converter(double valor) {
        return valor + 273.15;
    }

    @Override
    public String toString() {
        return "Celsius para Kelvin";
    }
}

public class ConversorTemperatura {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🌡️ === CONVERSOR DE TEMPERATURA ===\n");

        int opcao;

        do {
            System.out.println("=== MENU DE CONVERSÕES ===");
            System.out.println("1 - Celsius para Fahrenheit");
            System.out.println("2 - Fahrenheit para Celsius");
            System.out.println("3 - Celsius para Kelvin");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= 3) {
                // Ler o valor a ser convertido
                System.out.print("\nDigite o valor para conversão: ");
                double valor = scanner.nextDouble();

                Conversao conversao = null;

                // Criar a conversão baseada na escolha
                switch (opcao) {
                    case 1:
                        conversao = new CelsiusParaFahrenheit();
                        break;
                    case 2:
                        conversao = new FahrenheitParaCelsius();
                        break;
                    case 3:
                        conversao = new CelsiusParaKelvin();
                        break;
                }

                double resultado = conversao.converter(valor);

                System.out.println("\n╔══════════════════════════════╗");
                System.out.println("║       RESULTADO DA CONVERSÃO  ║");
                System.out.println("╠══════════════════════════════╣");
                System.out.println("║ Conversão: " + String.format("%-16s", conversao) + "║");
                System.out.println("║ Valor original: " + String.format("%-10.2f", valor) + "║");
                System.out.println("║ Resultado: " + String.format("%-16.2f", resultado) + "║");
                System.out.println("╚══════════════════════════════╝\n");

            } else if (opcao == 4) {
                System.out.println("\n👋 Saindo do conversor...");
            } else {
                System.out.println("\n❌ Opção inválida! Tente novamente.\n");
            }

        } while (opcao != 4);

        scanner.close();
    }
}