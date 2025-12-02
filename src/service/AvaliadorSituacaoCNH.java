package service;

import dominio.Motorista;
import dominio.SituacaoCNH;

public interface AvaliadorSituacaoCNH {
    SituacaoCNH avaliarSituacao(Motorista motorista);
}