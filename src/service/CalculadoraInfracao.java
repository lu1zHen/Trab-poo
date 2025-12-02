package service;

import dominio.Infracao;

public interface CalculadoraInfracao {
    double calcularValorMulta(Infracao infracao);
    int calcularPontos(Infracao infracao);
}