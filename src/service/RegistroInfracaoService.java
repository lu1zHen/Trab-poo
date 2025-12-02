package service;

import dominio.*;
import repository.InfracaoRepository;

public class RegistroInfracaoService {
    private final InfracaoRepository infracaoRepository;
    private final CalculadoraInfracao calculadoraInfracao;
    private final AvaliadorSituacaoCNH avaliadorSituacaoCNH;

    public RegistroInfracaoService(
            InfracaoRepository infracaoRepository,
            CalculadoraInfracao calculadoraInfracao,
            AvaliadorSituacaoCNH avaliadorSituacaoCNH) {
        this.infracaoRepository = infracaoRepository;
        this.calculadoraInfracao = calculadoraInfracao;
        this.avaliadorSituacaoCNH = avaliadorSituacaoCNH;
    }

    public Infracao registrarInfracao(TipoInfracao tipo, Veiculo veiculo) {
        Motorista motorista = veiculo.getMotoristaResponsavel();
        Infracao infracao = new Infracao(tipo, veiculo);
        int pontos = calculadoraInfracao.calcularPontos(infracao);

        motorista.adicionarPontos(pontos);
        infracaoRepository.salvar(infracao);

        return infracao;
    }

    public SituacaoCNH consultarSituacaoMotorista(Motorista motorista) {
        return avaliadorSituacaoCNH.avaliarSituacao(motorista);
    }

    public double consultarValorMulta(Infracao infracao) {
        return calculadoraInfracao.calcularValorMulta(infracao);
    }
}