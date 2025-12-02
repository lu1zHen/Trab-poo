package service;

import dominio.*;
import repository.MotoristaRepository;
import repository.VeiculoRepository;
import repository.InfracaoRepository;
import java.util.List;
import java.util.Optional;

public class RelatorioService {
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;
    private final InfracaoRepository infracaoRepository;
    private final AvaliadorSituacaoCNH avaliadorSituacaoCNH;

    public RelatorioService(
            MotoristaRepository motoristaRepository,
            VeiculoRepository veiculoRepository,
            InfracaoRepository infracaoRepository,
            AvaliadorSituacaoCNH avaliadorSituacaoCNH) {
        this.motoristaRepository = motoristaRepository;
        this.veiculoRepository = veiculoRepository;
        this.infracaoRepository = infracaoRepository;
        this.avaliadorSituacaoCNH = avaliadorSituacaoCNH;
    }

    public void emitirRelatorioMotorista(String numeroCnh) {
        Optional<Motorista> motoristaOpt = motoristaRepository.buscarPorCnh(numeroCnh);
        if (motoristaOpt.isPresent()) {
            Motorista motorista = motoristaOpt.get();
            List<Infracao> infracoes = infracaoRepository.listarPorMotorista(numeroCnh);
            SituacaoCNH situacao = avaliadorSituacaoCNH.avaliarSituacao(motorista);

            System.out.println("\n" + "=".repeat(50));
            System.out.println("RELATÓRIO DO MOTORISTA");
            System.out.println("=".repeat(50));
            System.out.println("Nome: " + motorista.getNome());
            System.out.println("CNH: " + motorista.getNumeroCnh());
            System.out.println("Pontos acumulados: " + motorista.getPontosAcumulados());
            System.out.println("Situação: " + situacao.getDescricao());
            System.out.println("Infrações registradas: " + infracoes.size());

            if (!infracoes.isEmpty()) {
                System.out.println("\nDETALHES DAS INFRAÇÕES:");
                for (Infracao infracao : infracoes) {
                    System.out.printf(" - %s%n", infracao);
                }
            }
            System.out.println("=".repeat(50) + "\n");
        } else {
            System.out.println("❌ Motorista não encontrado!");
        }
    }

    public void emitirRelatorioGeral() {
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        List<Infracao> todasInfracoes = infracaoRepository.listarTodos();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("RELATÓRIO GERAL DO SISTEMA");
        System.out.println("=".repeat(50));
        System.out.println("Total de motoristas cadastrados: " + motoristas.size());
        System.out.println("Total de infrações registradas: " + todasInfracoes.size());

        for (Motorista motorista : motoristas) {
            SituacaoCNH situacao = avaliadorSituacaoCNH.avaliarSituacao(motorista);
            List<Infracao> infracoesMotorista = infracaoRepository.listarPorMotorista(motorista.getNumeroCnh());

            System.out.printf("- %s: %d pontos (%s) - %d infrações%n",
                    motorista.getNome(),
                    motorista.getPontosAcumulados(),
                    situacao.getDescricao(),
                    infracoesMotorista.size());
        }
        System.out.println("=".repeat(50) + "\n");
    }

    public void listarTodosMotoristas() {
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("❌ Nenhum motorista cadastrado!");
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("MOTORISTAS CADASTRADOS");
        System.out.println("=".repeat(50));
        for (Motorista motorista : motoristas) {
            SituacaoCNH situacao = avaliadorSituacaoCNH.avaliarSituacao(motorista);
            System.out.printf("- %s | Situação: %s%n", motorista, situacao.getDescricao());
        }
        System.out.println("=".repeat(50) + "\n");
    }

    public void listarTodosVeiculos() {
        List<Veiculo> veiculos = veiculoRepository.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("❌ Nenhum veículo cadastrado!");
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("VEÍCULOS CADASTRADOS");
        System.out.println("=".repeat(50));
        for (Veiculo veiculo : veiculos) {
            System.out.printf("- %s%n", veiculo);
        }
        System.out.println("=".repeat(50) + "\n");
    }
}