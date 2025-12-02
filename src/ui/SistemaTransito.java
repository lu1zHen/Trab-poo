package ui;

import repository.*;
import service.*;
import dominio.*;

public class SistemaTransito {
    public static void main(String[] args) {
        System.out.println("🚗 BEM-VINDO AO SISTEMA DE TRÂNSITO 🚗");
        System.out.println("Sistema de Controle de Trânsito Municipal");

        MotoristaRepository motoristaRepo = new MotoristaRepositoryMemoria();
        VeiculoRepository veiculoRepo = new VeiculoRepositoryMemoria();
        InfracaoRepository infracaoRepo = new InfracaoRepositoryMemoria();

        CalculadoraInfracao calculadora = new CalculadoraInfracaoPadrao();
        AvaliadorSituacaoCNH avaliador = new AvaliadorSituacaoCNHPadrao();

        RegistroInfracaoService registroService = new RegistroInfracaoService(
                infracaoRepo, calculadora, avaliador);

        RelatorioService relatorioService = new RelatorioService(
                motoristaRepo, veiculoRepo, infracaoRepo, avaliador);

        InterfaceUsuario interfaceUsuario = new InterfaceUsuario(
                motoristaRepo, veiculoRepo, registroService, relatorioService);

        interfaceUsuario.exibirMenu();
    }
}