package service;

import dominio.Motorista;
import dominio.SituacaoCNH;

public class AvaliadorSituacaoCNHPadrao implements AvaliadorSituacaoCNH {
    private static final int PONTOS_REGULAR = 10;
    private static final int PONTOS_SUSPENSA = 20;

    @Override
    public SituacaoCNH avaliarSituacao(Motorista motorista) {
        int pontos = motorista.getPontosAcumulados();

        if (pontos < PONTOS_REGULAR) {
            return SituacaoCNH.REGULAR;
        } else if (pontos < PONTOS_SUSPENSA) {
            return SituacaoCNH.EM_RISCO;
        } else {
            return SituacaoCNH.SUSPENSA;
        }
    }
}