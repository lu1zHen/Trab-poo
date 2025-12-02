package service;

import dominio.Infracao;

public class CalculadoraInfracaoPadrao implements CalculadoraInfracao {
    @Override
    public double calcularValorMulta(Infracao infracao) {
        return infracao.getValorMulta();
    }

    @Override
    public int calcularPontos(Infracao infracao) {
        return infracao.getPontos();
    }
}