package ui;

import dominio.*;
import repository.*;
import service.*;
import java.util.Scanner;
import java.util.List;

public class InterfaceUsuario {
    private final Scanner scanner;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;
    private final RegistroInfracaoService registroService;
    private final RelatorioService relatorioService;

    public InterfaceUsuario(
            MotoristaRepository motoristaRepository,
            VeiculoRepository veiculoRepository,
            RegistroInfracaoService registroService,
            RelatorioService relatorioService) {
        this.scanner = new Scanner(System.in);
        this.motoristaRepository = motoristaRepository;
        this.veiculoRepository = veiculoRepository;
        this.registroService = registroService;
        this.relatorioService = relatorioService;
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("\n🚗 SISTEMA DE CONTROLE DE TRÂNSITO 🚗");
            System.out.println("1. Cadastrar Motorista");
            System.out.println("2. Cadastrar Veículo");
            System.out.println("3. Registrar Infração");
            System.out.println("4. Consultar Situação do Motorista");
            System.out.println("5. Listar Motoristas");
            System.out.println("6. Listar Veículos");
            System.out.println("7. Relatório Geral");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarMotorista();
                    break;
                case 2:
                    cadastrarVeiculo();
                    break;
                case 3:
                    registrarInfracao();
                    break;
                case 4:
                    consultarSituacaoMotorista();
                    break;
                case 5:
                    relatorioService.listarTodosMotoristas();
                    break;
                case 6:
                    relatorioService.listarTodosVeiculos();
                    break;
                case 7:
                    relatorioService.emitirRelatorioGeral();
                    break;
                case 8:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        }
    }

    private void cadastrarMotorista() {
        System.out.println("\n--- CADASTRAR MOTORISTA ---");

        System.out.print("Nome do motorista: ");
        String nome = scanner.nextLine();

        System.out.print("Número da CNH: ");
        String numeroCnh = scanner.nextLine();

        if (motoristaRepository.buscarPorCnh(numeroCnh).isPresent()) {
            System.out.println("❌ Já existe um motorista com esta CNH!");
            return;
        }

        Motorista motorista = new Motorista(nome, numeroCnh);
        motoristaRepository.salvar(motorista);

        System.out.println("✅ Motorista cadastrado com sucesso!");
    }

    private void cadastrarVeiculo() {
        System.out.println("\n--- CADASTRAR VEÍCULO ---");
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("❌ Não há motoristas cadastrados. Cadastre um motorista primeiro!");
            return;
        }

        System.out.println("Motoristas disponíveis:");
        for (int i = 0; i < motoristas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, motoristas.get(i));
        }

        System.out.print("Selecione o número do motorista: ");
        int indiceMotorista = lerInteiro() - 1;

        if (indiceMotorista < 0 || indiceMotorista >= motoristas.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }

        Motorista motorista = motoristas.get(indiceMotorista);
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo do veículo: ");
        String modelo = scanner.nextLine();

        if (veiculoRepository.buscarPorPlaca(placa).isPresent()) {
            System.out.println("❌ Já existe um veículo com esta placa!");
            return;
        }

        Veiculo veiculo = new Veiculo(placa, modelo, motorista);
        veiculoRepository.salvar(veiculo);

        System.out.println("✅ Veículo cadastrado com sucesso!");
    }

    private void registrarInfracao() {
        System.out.println("\n--- REGISTRAR INFRAÇÃO ---");
        List<Veiculo> veiculos = veiculoRepository.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("❌ Não há veículos cadastrados. Cadastre um veículo primeiro!");
            return;
        }

        System.out.println("Veículos disponíveis:");
        for (int i = 0; i < veiculos.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, veiculos.get(i));
        }

        System.out.print("Selecione o número do veículo: ");
        int indiceVeiculo = lerInteiro() - 1;
        if (indiceVeiculo < 0 || indiceVeiculo >= veiculos.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }

        Veiculo veiculo = veiculos.get(indiceVeiculo);
        System.out.println("\nTipos de infração:");
        TipoInfracao[] tipos = TipoInfracao.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s (%d pontos - R$ %.2f)%n",
                    i + 1, tipos[i], tipos[i].getPontos(), tipos[i].getValor());
        }

        System.out.print("Selecione o tipo de infração: ");
        int indiceTipo = lerInteiro() - 1;
        if (indiceTipo < 0 || indiceTipo >= tipos.length) {
            System.out.println("❌ Índice inválido!");
            return;
        }

        TipoInfracao tipo = tipos[indiceTipo];
        Infracao infracao = registroService.registrarInfracao(tipo, veiculo);

        System.out.println("✅ Infração registrada com sucesso!");
        System.out.printf("📝 Detalhes: %s - %d pontos - R$ %.2f%n",
                tipo, tipo.getPontos(), tipo.getValor());

        Motorista motorista = veiculo.getMotoristaResponsavel();
        SituacaoCNH situacao = registroService.consultarSituacaoMotorista(motorista);
        System.out.printf("📊 Situação do motorista: %s (%d pontos)%n",
                situacao.getDescricao(), motorista.getPontosAcumulados());
    }

    private void consultarSituacaoMotorista() {
        System.out.println("\n--- CONSULTAR SITUAÇÃO DO MOTORISTA ---");
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("❌ Não há motoristas cadastrados!");
            return;
        }

        System.out.println("Motoristas disponíveis:");
        for (int i = 0; i < motoristas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, motoristas.get(i));
        }

        System.out.print("Selecione o número do motorista: ");
        int indiceMotorista = lerInteiro() - 1;
        if (indiceMotorista < 0 || indiceMotorista >= motoristas.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }

        Motorista motorista = motoristas.get(indiceMotorista);
        relatorioService.emitirRelatorioMotorista(motorista.getNumeroCnh());
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ Por favor, digite um número válido: ");
            }
        }
    }
}